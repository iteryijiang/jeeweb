package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.beetl.tags.dict.DictUtils;
import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.buyer.entity.TmyTask;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskService;
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
    private TtaskBaseService ttaskBaseService;
    @Autowired
    private TshopInfoService tshopInfoService;
    @Autowired
    private TmyTaskService tmyTaskService;
    @Autowired
    private TmyTaskDetailService tmyTaskDetailService;
    @Autowired
    private TshopBaseService tshopBaseService;
    public List<TtaskBase> selectShopTask(String shopid,int count,String user,int minute){
        return baseMapper.selectShopTask(shopid,count,user,minute);
    }

    public Map sumNumAndPrice(Map m){
        return baseMapper.sumNumAndPrice(m);
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
    public List<Map> selectWithdrawalMoneyList( String createDate1, String createDate2,int multiple) {
        return baseMapper.selectWithdrawalMoneyList(createDate1,createDate2,multiple);
    }
    public List<Map> listFinanceShopReport(Map map) {
        return baseMapper.listFinanceShopReport(map);
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
                List<TtaskBase> list = ttaskBaseService.selectShopTask("",count+2,user,minute);
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

                    ttaskBaseService.updateBatchById(new_tasklist);
                    tmyTaskDetailService.insertBatch(new_list,countSum);
                    tmyTaskService.insertOrUpdate(tmyTask);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
