package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.beetl.tags.dict.DictUtils;
import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.ebp.buyer.entity.TmyTask;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskService;
import cn.jeeweb.web.ebp.finance.entity.TfinanceRechargeLog;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeLogService;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeService;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.mapper.TtaskBaseMapper;
import cn.jeeweb.web.ebp.shop.service.TshopBaseService;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import cn.jeeweb.web.ebp.shop.spider.TsequenceSpider;
import cn.jeeweb.web.utils.UserUtils;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.query.data.Queryable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Transactional
@Service("ttaskBaseService1")
public class TtaskBaseServiceImpl extends CommonServiceImpl<TtaskBaseMapper, TtaskBase> implements TtaskBaseService {

    @Autowired
    private TshopInfoService tshopInfoService;
    @Autowired
    private TmyTaskService tmyTaskService;
    @Autowired
    private TmyTaskDetailService tmyTaskDetailService;
    @Autowired
    private TshopBaseService tshopBaseService;
    @Autowired
    private TfinanceRechargeLogService tfinanceRechargeLogService;
    public List<TtaskBase> selectShopTask(String shopid,int count,String user,int minute){
        return baseMapper.selectShopTask(shopid,count,user,minute);
    }
    public List<TtaskBase> selectShopTaskNew(int minute){
        return baseMapper.selectShopTaskNew(minute);
    }

    public Map sumNumAndPrice(Map m){
        return baseMapper.sumNumAndPrice(m);
    }
    public Map showTaskBaseLoadFinance(Map m){
        return baseMapper.showTaskBaseLoadFinance(m);
    }

    public Integer sumTtaskBase(Map m){
        return baseMapper.sumTtaskBase(m);
    }

    public boolean addTask(TtaskBase ttaskBase, TshopInfo si){
        tshopInfoService.updateById(si);
        insert(ttaskBase);
        TfinanceRechargeLog log = new TfinanceRechargeLog(ttaskBase.getShopid(),ttaskBase.getStorename(),ttaskBase.getId(),TfinanceRechargeService.rechargetype_2,ttaskBase.getTaskdeposit(),si.getTotaldeposit());
        tfinanceRechargeLogService.insert(log);
        return true;
    }
    public boolean upTask(TtaskBase ttaskBase, TshopInfo si,String rechargetype,BigDecimal price){
        tshopInfoService.updateById(si);
        updateById(ttaskBase);
        TfinanceRechargeLog log = new TfinanceRechargeLog(ttaskBase.getShopid(),ttaskBase.getStorename(),ttaskBase.getId(),rechargetype,price,si.getTotaldeposit());
        tfinanceRechargeLogService.insert(log);
        return true;
    }
    @Override
    public Page<TtaskBase> selectPage(Page<TtaskBase> page, Wrapper<TtaskBase> wrapper) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectShopList(page, wrapper));
        return page;
    }

    public List<Map> selectFinanceList( String createDate1, String createDate2) {
        return baseMapper.selectFinanceList(createDate1,createDate2);
    }
    public List<Map> selectWithdrawalMoneyList(Map map) {
        return baseMapper.selectWithdrawalMoneyList(map);
    }
    public List<Map> listFinanceShopReport(Map map) {
        return baseMapper.listFinanceShopReport(map);
    }

    public Map<String,Map<String,Map<Integer,List<TtaskBase>>>> myTaskMap(int count,int minute){
        //活动当日全订单
        List<TtaskBase> list = baseMapper.selectShopTaskNew(minute);
        //根据店铺订单得到每个店铺对应 组单
        Map<String,Map<Integer,List<TtaskBase>>> shopDoubleMap = new HashMap<String,Map<Integer,List<TtaskBase>>>();
        //根据店铺订单得到每个店铺对应 只有一单
        Map<String,Map<Integer,List<TtaskBase>>> shopOneMap = new HashMap<String,Map<Integer,List<TtaskBase>>>();

        //根据订单得到全店铺，及其店铺下对应订单
        Map<String,List<TtaskBase>> shopMap = new HashMap<String,List<TtaskBase>>();
        for (int i=0;i<list.size();i++) {
            TtaskBase tb = (TtaskBase)list.get(i);
            tb.setCanreceivenums(tb.getCanreceivenum());
            if(!shopMap.containsKey(tb.getStorename())){
                List<TtaskBase> li = new ArrayList<TtaskBase>();
                li.add(tb);
                shopMap.put(tb.getStorename(),li);
            }else {
                List<TtaskBase> li = shopMap.get(tb.getStorename());
                li.add(tb);
                shopMap.put(tb.getStorename(),li);
            }
        }
        Iterator<Map.Entry<String, List<TtaskBase>>> newentries = shopMap.entrySet().iterator();
        while (newentries.hasNext()) {
            Map.Entry<String, List<TtaskBase>> entry = newentries.next();
            List<TtaskBase> list_tb = entry.getValue();
            //对每个店铺下订单，根据每个店铺可领取单数，组合订单。如（每店2单：一组：A-B，二组：A-B。每店3单：一组：A-B-C,二组：A-B-D）
            Map<Integer,List<TtaskBase>> a = shopMap(list_tb,1,count,new HashMap<Integer,List<TtaskBase>>(),1);

            if(list_tb.size()==1 || a.get(1).size()==1){
                shopOneMap.put(entry.getKey(),a);//如果该店铺只有一个订单，则该Map作为替补选择。
            }else {
                shopDoubleMap.put(entry.getKey(),a);//如果店铺有多个订单，则主要对该Map选择任务单。
            }
//                Iterator<Map.Entry<String, List<TtaskBase>>> a1 = a.entrySet().iterator();
//                while (a1.hasNext()) {
//                    Map.Entry<String, List<TtaskBase>> aentry = a1.next();
//                }

        }
        Map<String,Map<String,Map<Integer,List<TtaskBase>>>> m = new HashMap<String,Map<String,Map<Integer,List<TtaskBase>>>>();
        m.put("1",shopDoubleMap);
        m.put("2",shopOneMap);
        return m;
    }

    /**
     * 保存领取单
     * */
    @Transactional
    public boolean createMyTask() throws Exception{
        long starttime = System.currentTimeMillis();//开始领取任务
        synchronized(this) {
            String user = UserUtils.getPrincipal().getId();

            int count = 3;
            int countSum = 7;
            int minute = 10;
            int canreceivenum = 400;
            try {
                String sum = DictUtils.getDictValue("一个任务单店接单数", "tasknum", count + "");
                count = Integer.parseInt(sum);
                countSum = Integer.parseInt(DictUtils.getDictValue("一个任务总连接数", "tasknum", countSum + ""));
                minute = Integer.parseInt(DictUtils.getDictValue("一个任务领取间隔时长", "tasknum", minute + ""));
                canreceivenum = Integer.parseInt(DictUtils.getDictValue("解除领取间隔时间限制阈值", "tasknum", canreceivenum + ""));
                int sumCanreceivenum =  baseMapper.sumCanreceivenum(minute);
                if(canreceivenum>=sumCanreceivenum){
                    minute = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //获得可领取总店铺数
            int sumshop = (int) Math.ceil((countSum+0.0) / count);

            Map<String, Map<String, Map<Integer, List<TtaskBase>>>> map = myTaskMap(count, minute);
            long endtime = System.currentTimeMillis();
            long usetime = (endtime - starttime);//每个店铺组合单时间
            long usetime1 = 0l;//得到具体多组任务时间
            long usetime2 = 0l;//保存具体多组任务时间
            long usetime3 = 0l;//保存具体单组任务时间

            //根据店铺订单得到每个店铺对应 组单
            Map<String, Map<Integer, List<TtaskBase>>> shopDoubleMap = map.get("1");
            //根据店铺订单得到每个店铺对应 只有一单
            Map<String, Map<Integer, List<TtaskBase>>> shopOneMap = map.get("2");

            if ((shopDoubleMap != null && shopDoubleMap.size() > 0) || (shopOneMap != null && shopOneMap.size() > 0)) {
                List<TmyTaskDetail> new_list =new ArrayList<TmyTaskDetail>();//需要保存的订单
                List<TtaskBase> new_tasklist =new ArrayList<TtaskBase>();//需要修改的订单
                //保存任务单
                TmyTask tmyTask = new TmyTask();
                tmyTask.setMytaskno(TsequenceSpider.getTaskNo());
                tmyTask.setState("0");
                tmyTask.setId(StringUtils.randomUUID());
                BigDecimal tprice = new BigDecimal(0.0);

                //已保存任务单数
                int createCount = 1;
                if ((shopDoubleMap != null && shopDoubleMap.size() > 0)){
                    //根据全店铺和所需店铺数得到可用店铺
                    Map<String, Map<Integer, List<TtaskBase>>> newDoubleShopMap = new HashMap<String, Map<Integer, List<TtaskBase>>>();
                    while (true) {
                        Random rand = new Random();
                        int randInt = rand.nextInt(shopDoubleMap.size());
                        int a = 0;
                        Iterator<Map.Entry<String, Map<Integer, List<TtaskBase>>>> entries = shopDoubleMap.entrySet().iterator();
                        while (entries.hasNext()) {
                            Map.Entry<String, Map<Integer, List<TtaskBase>>> entry = entries.next();
                            if (randInt == a) {
                                if (!newDoubleShopMap.containsKey(entry.getKey())) {
                                    newDoubleShopMap.put(entry.getKey(), entry.getValue());
                                }
                                break;
                            }
                            a++;
                        }
                        if (newDoubleShopMap.size() == shopDoubleMap.size() || newDoubleShopMap.size() == sumshop) {
                            break;
                        }
                    }
                    long endtime1 = System.currentTimeMillis();
                    usetime1 = (endtime1 - starttime);//得到具体多组任务时间

                    Iterator<Map.Entry<String, Map<Integer, List<TtaskBase>>>> entries = newDoubleShopMap.entrySet().iterator();
                    while (entries.hasNext()) {
                        Map.Entry<String, Map<Integer, List<TtaskBase>>> entry = entries.next();
                        Map<Integer, List<TtaskBase>> a = entry.getValue();
                        List<TtaskBase> tbs = RandomDoubleTask(a,1);
                        for (TtaskBase tb : tbs) {
                            TmyTaskDetail my = new TmyTaskDetail();
                            Map Mytask = new HashMap();
                            Mytask.put("taskid",tb.getId());
                            tb.setCanreceivenum(tb.getCanreceivenum()-1);
                            tb.setLasttakingdate(new Date());
                            new_tasklist.add(tb);
                            if(tb.getTasknum()<=tmyTaskDetailService.sumTaskBase(Mytask)){
                                continue;
                            }
                            my.setGoodsname(tb.gettTitle());//
                            my.setBuyerid(UserUtils.getUser().getId());//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
                            my.setTaskid(tb.getId());//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
                            my.setTaskstate("1");//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
                            my.setTasktype(tb.gettType());//	任务类型：京东/淘宝varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
                            my.setTaskstatus("0");
                            my.setPays(tb.getActualprice());
                            my.setReceivingdate(new Date());
                            my.setMytaskid(tmyTask.getId());
                            my.setIsdouble(count+"");
                            my.setStorename(tb.getStorename());
                            my.setTaskshopurl(tb.gettUrl()+"_"+tb.getStorename());
                            new_list.add(my);
                            tprice = tprice.add(tb.getActualprice());
                            createCount++;
                            if(createCount>countSum){
                                break;
                            }
                        }
                        if(createCount>countSum){
                            break;
                        }
                    }
                }
                long endtime2 = System.currentTimeMillis();
                usetime2 = (endtime2 - starttime);//保存具体多组任务时间
                if(createCount<=countSum) {
                    Iterator<Map.Entry<String, Map<Integer, List<TtaskBase>>>> entriesOne = shopOneMap.entrySet().iterator();
                    while (entriesOne.hasNext()) {
                        Map.Entry<String, Map<Integer, List<TtaskBase>>> entry = entriesOne.next();
                        Map<Integer, List<TtaskBase>> a = entry.getValue();
                        Random rand = new Random();
                        int randInt = rand.nextInt(a.size()) + 1;
                        List<TtaskBase> tbs = a.get(randInt);
                        for (TtaskBase tb : tbs) {
                            TmyTaskDetail my = new TmyTaskDetail();
                            Map Mytask = new HashMap();
                            Mytask.put("taskid",tb.getId());
                            tb.setCanreceivenum(tb.getCanreceivenum()-1);
                            tb.setLasttakingdate(new Date());
                            new_tasklist.add(tb);
                            if(tb.getTasknum()<=tmyTaskDetailService.sumTaskBase(Mytask)){
                                continue;
                            }
                            my.setGoodsname(tb.gettTitle());//
                            my.setBuyerid(UserUtils.getUser().getId());//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
                            my.setTaskid(tb.getId());//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
                            my.setTaskstate("1");//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
                            my.setTasktype(tb.gettType());//	任务类型：京东/淘宝varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
                            my.setTaskstatus("0");
                            my.setPays(tb.getActualprice());
                            my.setReceivingdate(new Date());
                            my.setMytaskid(tmyTask.getId());
                            my.setIsdouble("1");
                            my.setStorename(tb.getStorename());
                            my.setTaskshopurl(tb.gettUrl()+"_"+tb.getStorename());
                            new_list.add(my);
                            tprice = tprice.add(tb.getActualprice());
                            createCount++;
                            if(createCount>countSum){
                                break;
                            }
                        }
                        if(createCount>countSum){
                            break;
                        }
                    }
                }
                long endtime3 = System.currentTimeMillis();
                usetime3 = (endtime3 - starttime);//保存具体单组任务时间

                tmyTask.setTotalprice(tprice.setScale(2, BigDecimal.ROUND_HALF_UP));
                tmyTaskDetailService.insertBatch(new_list,countSum);
                System.out.print("任务ID为："+tmyTask.getId());
                if(new_tasklist!=null&&new_tasklist.size()>0){
                    tmyTaskService.insert(tmyTask);
                    updateBatchById(new_tasklist);
                }else {
                    return false;
                }
            }else {
                return false;
            }
            System.out.println("一个任务领取单各个阶段耗时：(每个店铺组合单时间"+usetime+"毫秒，得到具体多组任务时间"+usetime1+"毫秒，保存具体多组任务时间"+usetime2+"毫秒，保存具体单组任务时间"+usetime3+"毫秒)");
            return true;
        }
    }

    /**
     * 递归，如果店铺有多组任务，但随机获取了多组中的单组，则需在再次随机，获取多组
     * */
    public List<TtaskBase> RandomDoubleTask(Map<Integer,List<TtaskBase>> a,int i){
        Random rand = new Random();
        int randInt = rand.nextInt(a.size()) + 1;
        List<TtaskBase> tbs = a.get(randInt);
        if(tbs.size()==1&&a.size()!=1&&i<=a.size()){
//            System.out.println(i+"获取了某店落单的对象：");
//            Iterator<Map.Entry<Integer, List<TtaskBase>>> a1 = a.entrySet().iterator();
//            while (a1.hasNext()) {
//                Map.Entry<Integer, List<TtaskBase>> aentry = a1.next();
//                System.out.println("=="+aentry.getKey());
//                for(int j=0;i<aentry.getValue().size();j++){
//                    TtaskBase bb = aentry.getValue().get(j);
//                    System.out.println(">>>>>>>>>>>>>>>>>>>"+bb.gettTitle()+"_______________"+bb.getCanreceivenum());
//                }
//            }
//            i++;
            RandomDoubleTask(a,i);
        }
        return tbs;
    }

    /**
     * 递归，对某店铺下，任务单根据剩余可领单数，进行组单。
     * */
    public Map<Integer,List<TtaskBase>> shopMap(List<TtaskBase> list_tb,int count,int countSum,Map<Integer,List<TtaskBase>> m,int i){
        TtaskBase tb0 = list_tb.get(0);
        List<TtaskBase> retlist = new ArrayList<TtaskBase>();
        tb0.setCanreceivenums(tb0.getCanreceivenums()-1);
        retlist.add(list_tb.get(0));
        if(i>=list_tb.size()){
            i=1;
        }
        for (int j = 1; j < countSum; j++) {
            if (j < list_tb.size()) {
                if(i>=list_tb.size()){
                    continue;
                }
                TtaskBase tb = list_tb.get(i++);
                tb.setCanreceivenums(tb.getCanreceivenums()-1);
                retlist.add(tb);
                if(tb.getCanreceivenums()==0){
                    list_tb.remove(tb);
                }
            }
            if(tb0.getCanreceivenums()==0){
                list_tb.remove(tb0);
//                break;
                m.put(count,retlist);
                return m;
            }
        }
        m.put(count,retlist);
        count++;

        if(list_tb!=null&&list_tb.size()>0){
            shopMap(list_tb,count,countSum,m,i);
        }
        return m;
    }
    public void myTaskCreate(){
        synchronized(this) {
            try {
                String user = UserUtils.getPrincipal().getId();
                int count = 3;
                int countSum = 7;
                int minute = 10;
                try{
                    String sum = DictUtils.getDictValue("一个任务单店接单数","tasknum",count+"");
                    count = Integer.parseInt(sum);
                    countSum = Integer.parseInt(DictUtils.getDictValue("一个任务总连接数","tasknum",countSum+""));
                    minute = Integer.parseInt(DictUtils.getDictValue("一个任务领取间隔时长","tasknum",minute+""));
                }catch (Exception e){
                    e.printStackTrace();
                }
                //活动当日全订单
                List<TtaskBase> list = selectShopTask("",count+2,user,minute);
                //获得可领取总店铺数
                int sumshop = countSum/count+1;
                if(list!=null&&!list.isEmpty()){
                    //保存任务单
                    TmyTask tmyTask = new TmyTask();
                    tmyTask.setMytaskno(TsequenceSpider.getTaskNo());
                    tmyTask.setState("0");
                    tmyTaskService.insert(tmyTask);
                    BigDecimal tprice = new BigDecimal(0.0);

                    //根据订单得到全店铺
                    Map<String,Integer> shopMap = new HashMap<String,Integer>();
                    for (int i=0;i<list.size();i++) {
                        TtaskBase tb = (TtaskBase)list.get(i);
                        if(!shopMap.containsKey(tb.getStorename())){
                            shopMap.put(tb.getStorename(),1);
                        }else {
                            int aa = 1+shopMap.get(tb.getStorename());
                            shopMap.put(tb.getStorename(),aa);
                        }
                    }
                    //根据全店铺和所需店铺数得到可用店铺
                    Map<String,Integer> newShopMap = new HashMap<String,Integer>();
                    while(true){
                        Random rand = new Random();
                        int randInt = rand.nextInt(shopMap.size()) + 1;
                        int a = 1;
                        Iterator<Map.Entry<String, Integer>> entries = shopMap.entrySet().iterator();
                        while (entries.hasNext()) {
                            Map.Entry<String, Integer> entry = entries.next();
                            if(randInt==a){
                                if(!newShopMap.containsKey(entry.getKey())) {
                                    newShopMap.put(entry.getKey(), entry.getValue());
                                }
                                break;
                            }
                            a++;
                        }
                        if(newShopMap.size()==shopMap.size()||newShopMap.size()==sumshop){
                            break;
                        }
                    }
                    List<TmyTaskDetail> new_list =new ArrayList<TmyTaskDetail>();//需要保存的订单
                    List<TtaskBase> new_tasklist =new ArrayList<TtaskBase>();//需要修改的订单
                    //循环店铺，接取任务
                    Iterator<Map.Entry<String, Integer>> newentries = newShopMap.entrySet().iterator();
                    int zsum = 1;
                    while (newentries.hasNext()) {
                        Map.Entry<String, Integer> entry = newentries.next();
                        int dsum = 1;
                        String countR = "";
                        int countRI = 1;
                        if(entry.getValue()==1){
                            countR = ",1,";
                        }
                        //根据每个店铺接取数和店铺发布订单数获得每个店铺随机得到第几个订单。
                        while(true&&entry.getValue()>1){
                            Random rand = new Random();
                            int randInt = rand.nextInt(entry.getValue()) + 1;
                            if(countR.indexOf(","+randInt+",")==-1){
                                countR = countR+","+randInt+",";
                                countRI++;
                            }
                            if(countRI>count){
                                break;
                            }
                        }

                        int storeInt = 1;
                        for (int i=0;i<list.size();i++) {
                            TtaskBase tb = (TtaskBase)list.get(i);
                            if(entry.getKey().equals(tb.getStorename())&&countR.indexOf(","+storeInt+",")>=0){
                                TmyTaskDetail my = new TmyTaskDetail();
                                tb.setCanreceivenum(tb.getCanreceivenum()-1);
                                tb.setLasttakingdate(new Date());
                                new_tasklist.add(tb);
//                                ttaskBaseService.insertOrUpdate(tb);
                                my.setGoodsname(tb.gettTitle());//
                                my.setBuyerid(UserUtils.getUser().getId());//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
                                my.setTaskid(tb.getId());//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
                                my.setTaskstate("1");//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
                                my.setTasktype(tb.gettType());//	任务类型：京东/淘宝varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
                                my.setTaskstatus("0");
                                my.setPays(tb.getActualprice());
                                my.setReceivingdate(new Date());
                                my.setMytaskid(tmyTask.getId());
                                my.setTaskshopurl(tb.gettUrl()+"_"+tb.getStorename());
                                new_list.add(my);
//                                tmyTaskDetailService.insert(my);
                                tprice = tprice.add(tb.getActualprice());
                                dsum++;
                                zsum++;
                            }
                            if(entry.getKey().equals(tb.getStorename())){
                                storeInt++;
                            }
                            if(dsum>count||zsum>countSum){
                                break;
                            }
                        }
                        if(zsum>countSum){
                            break;
                        }
                    }
                    tmyTask.setTotalprice(tprice.setScale(2, BigDecimal.ROUND_HALF_UP));

                    updateBatchById(new_tasklist);
                    tmyTaskDetailService.insertBatch(new_list,countSum);
                    tmyTaskService.insertOrUpdate(tmyTask);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
