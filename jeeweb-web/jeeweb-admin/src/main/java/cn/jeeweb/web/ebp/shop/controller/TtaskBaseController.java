package cn.jeeweb.web.ebp.shop.controller;

import cn.jeeweb.beetl.tags.dict.Dict;
import cn.jeeweb.beetl.tags.dict.DictUtils;
import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.ebp.buyer.entity.TmyTask;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskService;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import cn.jeeweb.web.ebp.shop.spider.JdSpider;
import cn.jeeweb.web.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/shop/TtaskBase")
@ViewPrefix("ebp/shop")
@RequiresPathPermission("shop:TtaskBase")
@Log(title = "订单管理")
public class TtaskBaseController extends BaseBeanController<TtaskBase> {

    @Autowired
    private TtaskBaseService ttaskBaseService;
    @Autowired
    private TshopInfoService tshopInfoService;
    @Autowired
    private TmyTaskService tmyTaskService;

    @GetMapping
    @RequiresMethodPermissions("view")
    public ModelAndView list(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("ReleaseTask");
        return mav;
    }

    @GetMapping(value = "TaskDetail")
    @RequiresMethodPermissions("TaskDetail")
    public ModelAndView TaskDetail(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("TaskDetail");
        return mav;
    }

    @PostMapping("add")
    @Log(logType = LogType.INSERT)
    @RequiresMethodPermissions("add")
    public Response add(@RequestBody TtaskBase ttaskBase,HttpServletRequest request, HttpServletResponse response) {
        ttaskBase.setShopid(UserUtils.getUser().getId());
        ttaskBase.setTaskno((new Date().getTime())+""+(new Random().nextInt(9999)+1));
        ttaskBase.setCanreceivenum(ttaskBase.getTasknum());
        if(ttaskBase.gettPrice()!=null&&ttaskBase.gettNum()!=null){
            ttaskBase.setTotalprice(ttaskBase.gettPrice()*ttaskBase.gettNum());
        }
        ttaskBase.setStatus("1");
        try {
            ttaskBaseService.insert(ttaskBase);
        }catch (Exception e){
            e.printStackTrace();
        }
        //保存之后
        return Response.ok("添加成功");
    }

    @PostMapping("update")
    @Log(logType = LogType.UPDATE)
    @RequiresMethodPermissions("update")
    public Response update(@RequestBody TtaskBase entity, BindingResult result,
                        HttpServletRequest request, HttpServletResponse response) {
        try {
            TtaskBase tb = ttaskBaseService.selectById(entity.getId());
            BeanUtils.copyProperties(tb,entity,"tType,tUrl,tTitle,tPrice,tNum,totalPrice,searchPrice");
            ttaskBaseService.insertOrUpdate(tb);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Response.ok("更新成功");
    }

    @PostMapping("delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response delete(@PathVariable("id") String id) {
        ttaskBaseService.deleteById(id);
        return Response.ok("删除成功");
    }

    @GetMapping(value = "taskList")
    @RequiresMethodPermissions("taskList")
    public ModelAndView myList(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("list");
        return mav;
    }
    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "ajaxList", method = { RequestMethod.GET, RequestMethod.POST })
    @PageableDefaults(sort = "id=desc")
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("list")
    public void ajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
//        System.out.println("___________");
//        System.out.println("${oss.local.upload-file-path}");
        EntityWrapper<TtaskBase> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        String userid = UserUtils.getPrincipal().getId();
        if (!StringUtils.isEmpty(userid)&&!"admin".equals(UserUtils.getUser().getUsername())) {
            entityWrapper.eq("create_by", userid);
        }
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<TtaskBase> pagejson = new PageResponse<>(ttaskBaseService.list(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }
    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "myTaskList", method = { RequestMethod.GET, RequestMethod.POST })
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("myTaskList")
    public void myTaskList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        List listBase = new ArrayList();
        try {
            //获得商户表
            List<TshopInfo> listShop = tshopInfoService.findshopInfo();
            int count = 7;
            int sum = 0;
            Map<String, Integer> map = new HashMap();
            for (TshopInfo si : listShop) {
                Random rand = new Random();
                int a = rand.nextInt(3) + 1;
                sum += a;
                if (sum > 7) {
                    a = a - (sum - 7);
                }
                map.put(si.getUserid(), a);
                if (sum >= 7) {
                    break;
                }
            }
            //根据每个商户数量返回订单数
            Iterator<Map.Entry<String, Integer>> entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, Integer> entry = entries.next();
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                List<TtaskBase> list = ttaskBaseService.selectShopTask(entry.getKey(), entry.getValue());
                listBase.addAll(list);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        String content = JSON.toJSONString(listBase);
        StringUtils.printJson(response,content);
    }

    @RequestMapping(value = "showTitle", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresMethodPermissions("showTitle")
    public void showTitle(@RequestBody JSONObject jsonObject, HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        Map map = new HashMap();
        try {
            String turl = jsonObject.getString("turl");
            String goodsrc = JdSpider.getGoodImgByurl(turl);//获取图片
            String ttitle = JdSpider.getGoodTitleByurl(turl);//获取商品标题
            String goodis = JdSpider.getGoodId_ByURL(turl);//获取商品ID
            String result = JdSpider.getGoodInfos(goodis);//获取商品详细信息
            String good_price  = JdSpider.getGoodPrice_ByResult(result);//获取商品价格
            map.put("goodsrc",goodsrc);
            map.put("ttitle",ttitle);
            map.put("goodprice",good_price);

        }catch (Exception e){
            e.printStackTrace();
        }
        String content = JSON.toJSONString(map);
        StringUtils.printJson(response,content);
    }

    @GetMapping(value = "listPool")
    @RequiresMethodPermissions("listPool")
    public ModelAndView listPool(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("listPool");
        return mav;
    }

    @GetMapping(value = "{id}/listPoolGet")
    public ModelAndView listPoolGet(@PathVariable("id") String id, Model model,HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        TtaskBase tb = ttaskBaseService.selectById(id);
        TshopInfo tsi = tshopInfoService.selectOne(tb.getShopid());
        model.addAttribute("tb",tb);
        model.addAttribute("tsi",tsi);
        //进行中，完成数
        int taskstatus0=0,taskstatus1=0;

        List<Map> list = tmyTaskService.groupBytaskstatus(tb.getId());
        for (int i=0;i<list.size();i++){
            Map map = list.get(i);
            if("0".equals(map.get("taskstatus").toString())){
                taskstatus0 = Integer.parseInt(map.get("counts").toString());
            }else if("1".equals(map.get("taskstatus").toString())){
                taskstatus1 = Integer.parseInt(map.get("counts").toString());
            }
        }
        model.addAttribute("taskstatus0",taskstatus0);
        model.addAttribute("taskstatus1",taskstatus1);
        ModelAndView mav = displayModelAndView("TaskDetailForSeller");
        return mav;
    }

//    public static void main(String[] args){
//        int count = 7;
//        int sum = 0;
//        Map<String,Integer> map = new HashMap();
//        for (int i=0;i<10;i++) {
//            Random rand = new Random();
//            int a = rand.nextInt(3) + 1;
//            sum +=a;
//            if(sum>7){
//                a = a-(sum-7);
//            }
//            map.put("A"+i,a);
//            if(sum>=7){
//                break;
//            }
//        }
//        Iterator<Map.Entry<String,Integer>> entries = map.entrySet().iterator();
//        while (entries.hasNext()) {
//            Map.Entry<String,Integer> entry = entries.next();
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//        }
//    }


    @GetMapping(value = "{diccode}/getDictList")
    public void getDictList(@PathVariable("diccode") String diccode, HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        List<Dict> list = new ArrayList<Dict>();
        try {
            list = DictUtils.getDictList(diccode);
        }catch (Exception e){
            e.printStackTrace();
        }
        String content = JSON.toJSONString(list);
        StringUtils.printJson(response,content);
    }

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "myTaskCreate", method = { RequestMethod.GET, RequestMethod.POST })
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("myTaskCreate")
    public void myTaskCreate(HttpServletRequest request,HttpServletResponse response) throws IOException {
        List listBase = new ArrayList();
        try {
            int count = 3;
            int countSum = 6;
            try{
                String sum = DictUtils.getDictValue("接单数","tasknum",count+"");
                count = Integer.parseInt(sum);
                countSum = Integer.parseInt(DictUtils.getDictValue("总单数","tasknum",countSum+""));
            }catch (Exception e){
                e.printStackTrace();
            }

            List<TtaskBase> list = ttaskBaseService.selectShopTask("",count);

            for (int i=0;i<list.size();i++) {
                if((i+1)>countSum){
                    break;
                }
                TtaskBase tb = (TtaskBase)list.get(i);
                TmyTask my = new TmyTask();
                tb.setCanreceivenum(tb.getCanreceivenum()-1);
                ttaskBaseService.insertOrUpdate(tb);
                my.setGoodsname(tb.gettTitle());//
                my.setBuyerid(UserUtils.getUser().getId());//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
                my.setTaskid(tb.getId());//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
                my.setTaskstate("1");//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
                my.setTasktype(tb.gettType());//	任务类型：京东/淘宝varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
                my.setTaskstatus("0");
                my.setPays(BigDecimal.valueOf(tb.gettPrice()));
                tmyTaskService.insert(my);
            }
            //获得商户表
//            List<TshopInfo> listShop = tshopInfoService.findshopInfo();
//            int count = 7;
//            int sum = 0;
//            Map<String, Integer> map = new HashMap();
//            for (TshopInfo si : listShop) {
//                Random rand = new Random();
//                int a = rand.nextInt(3) + 1;
//                sum += a;
//                if (sum > 7) {
//                    a = a - (sum - 7);
//                }
//                map.put(si.getUserid(), a);
//                if (sum >= 7) {
//                    break;
//                }
//            }
            //根据每个商户数量返回订单数
//            Iterator<Map.Entry<String, Integer>> entries = map.entrySet().iterator();
//            while (entries.hasNext()) {
//                Map.Entry<String, Integer> entry = entries.next();
//                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//                List<TtaskBase> list = ttaskBaseService.selectShopTask(entry.getKey(), entry.getValue());
//                listBase.addAll(list);
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
//        String content = JSON.toJSONString(listBase);
//        StringUtils.printJson(response,content);
    }
}