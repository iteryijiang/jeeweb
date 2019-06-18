package cn.jeeweb.web.ebp.buyer.controller;

import cn.jeeweb.beetl.tags.dict.DictUtils;
import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.query.data.Condition;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.ebp.buyer.entity.TmyTask;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskService;
import cn.jeeweb.web.ebp.enums.YesNoEnum;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import cn.jeeweb.web.ebp.shop.util.TaskUtils;
import cn.jeeweb.web.modules.sys.entity.User;
import cn.jeeweb.web.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/buyer/TmyTask")
@ViewPrefix("ebp/buyer")
@RequiresPathPermission("buyer:TmyTask")
@Log(title = "抢单管理")
public class TmyTaskController extends BaseBeanController<TmyTask> {

    @Autowired
    private TmyTaskService tmyTaskService;
    @Autowired
    private TtaskBaseService ttaskBaseService;
    @Autowired
    private TmyTaskDetailService tmyTaskDetailService;


    @GetMapping
    @RequiresMethodPermissions("view")
    public ModelAndView TaskList(Model model, HttpServletRequest request, HttpServletResponse response) {
//        String userid = UserUtils.getPrincipal().getId();
//        Date date = new Date();
//        Calendar calendar = new GregorianCalendar();
//        calendar.add(calendar.DATE,1);
//        Date date2 = calendar.getTime();
//        Map map = sumNumAndPrice(userid,DateUtils.formatDate(date,"yyyy-MM-dd"),DateUtils.formatDate(date2,"yyyy-MM-dd"));
//        model.addAttribute("map",map);
        ModelAndView mav = displayModelAndView("list");
        boolean bool = false;
        User user = UserUtils.getUser();
        if(user!=null&&0==user.getFreezeStatus()&&1==user.getReceiveTaskStatus()){
            bool =true;
        }
        model.addAttribute("showbut",bool);
        return mav;
    }
    @GetMapping(value = "listFinanceBuyerReportHTML")
    @RequiresMethodPermissions("listFinanceBuyerReportHTML")
    public ModelAndView listFinanceShopReportHTML(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("list_finance_buyer_report");
        return mav;
    }

    public Map sumNumAndPrice(Map m){
        Map map = tmyTaskDetailService.sumNumAndPrice(m);
        return map;
    }

    @PostMapping("add")
    @Log(logType = LogType.INSERT)
    @RequiresMethodPermissions("add")
    public Response add(@RequestBody TmyTask tmyTask,HttpServletRequest request, HttpServletResponse response) {
//        TtaskBase entity = new TtaskBase();
//        System.out.println(request.getParameter("taskBase"));

        //tmyTask.setStatus("1");
        try {
            tmyTaskService.insert(tmyTask);
        }catch (Exception e){
            e.printStackTrace();
        }
        //保存之后
        return Response.ok("添加成功");
    }

    @PostMapping("update")
    @Log(logType = LogType.UPDATE)
    @RequiresMethodPermissions("update")
    public Response update(@RequestBody TmyTask entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        try {
            TmyTask tb = tmyTaskService.selectById(entity.getId());
            tmyTaskService.insertOrUpdate(tb);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Response.ok("更新成功");
    }

    @PostMapping("delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response delete(@PathVariable("id") String id) {
        tmyTaskService.deleteById(id);
        return Response.ok("删除成功");
    }




    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "ajaxList", method = { RequestMethod.GET, RequestMethod.POST })
    @PageableDefaults(sort = "create_date=desc")
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("view")
    public void ajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        EntityWrapper<TmyTask> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        String userid = UserUtils.getPrincipal().getId();
        if (!StringUtils.isEmpty(userid)) {
            entityWrapper.eq("create_by", userid);
        }
        if(queryable.getCondition()!=null) {
            Condition.Filter filter = queryable.getCondition().getFilterFor("createDate");
            if (filter != null) {
                queryable.getCondition().remove(filter);
                queryable.getCondition().and(Condition.Operator.between, "createDate", TaskUtils.whereDate(filter));
            }
        }
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<TmyTask> pagejson = new PageResponse<>(tmyTaskService.list(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }

    @GetMapping("{id}/detail")
    @Log(logType = LogType.SELECT)
    public ModelAndView detail(Model model,@PathVariable("id") String id) {
        TmyTask tmyTask = tmyTaskService.selectById(id);
        if(tmyTask == null){
            TmyTaskDetail tmyTaskDetail = tmyTaskDetailService.selectById(id);
            if(tmyTaskDetail!=null){
                tmyTask = tmyTaskService.selectById(tmyTaskDetail.getMytaskid());
            }
        }
        model.addAttribute("tmyTask", tmyTask);
        List<TmyTaskDetail> list = tmyTaskDetailService.selectMytaskList(tmyTask.getId());
        List namelist = new ArrayList();
        List<TmyTaskDetail> newlist = new ArrayList<TmyTaskDetail>();
        StringBuffer sb = new StringBuffer();
        for (TmyTaskDetail t :list){
            if(sb.indexOf(","+t.getShopname()+",")==-1){
                namelist.add(t.getShopname());
                sb.append(","+t.getShopname()+",");
            }
            t.setTasktype(DictUtils.getDictLabel(t.getTasktype(),"tasktype",t.getTasktype()));
            t.setTaskstateName(DictUtils.getDictLabel(t.getTaskstate(),"taskstate",t.getTaskstate()));
            newlist.add(t);
        }

        model.addAttribute("list",JSON.toJSONString(newlist));
        model.addAttribute("namelist",JSON.toJSONString(namelist));
        return displayModelAndView("MyTask");
    }

    @RequestMapping(value = "selBaseIdMyTaskList", method = { RequestMethod.GET, RequestMethod.POST })
    @PageableDefaults(sort = "id=desc")
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("selBaseIdMyTaskList")
    public void selBaseIdMyTaskList(@RequestBody JSONObject jsonObject, HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {

        String taskId = jsonObject.getString("taskId");
        List<TmyTaskDetail> list = tmyTaskDetailService.selBaseIdMyTaskDetailList(taskId);
        List<TmyTaskDetail> newlist = new ArrayList<TmyTaskDetail>();
        for (TmyTaskDetail t :list){
            t.setTasktype(DictUtils.getDictLabel(t.getTasktype(),"tasktype",t.getTasktype()));
            t.setTaskstateName(DictUtils.getDictLabel(t.getTaskstate(),"taskstate",t.getTaskstate()));
            newlist.add(t);
        }
        String content = JSON.toJSONString(newlist);
        StringUtils.printJson(response,content);
    }


    @GetMapping("{id}/updateTaskState")
    @Log(logType = LogType.SELECT)
    public ModelAndView updateTaskState(Model model,@PathVariable("id") String id) {
        TmyTaskDetail tmyTask = tmyTaskDetailService.selectById(id);
        String taskstate = tmyTask.getTaskstate();
        model.addAttribute("tmyTask", tmyTask);
        TtaskBase taskbase = ttaskBaseService.selectById(tmyTask.getTaskid());
        model.addAttribute("taskbase",taskbase);
        String targetPage = "";
        if("1".equals(taskstate)){
            //状态为已接单，未下单 ；跳转到 上传搜索关键词页面
            targetPage = "avatar";
        }else if("2".equals(taskstate)){
            //状态为已接单，未下单 ；跳转到 上传搜索关键词页面
        }
        return displayModelAndView(targetPage);
    }

    /**
     * 任务单完成，修改发布任务和我的任务状态
     * 发布任务状态：TtaskBase中status:0进行中，1完成
     * 我的任务状态：TmyTask中state:0进行中，1完成
     * 任务单状态：TmyTaskDetail中taskstatust:0进行中，1完成
     * */
    @RequestMapping(value = "upTaskState", method = { RequestMethod.GET, RequestMethod.POST })
    @Log(logType = LogType.UPDATE)
    @RequiresMethodPermissions("upTaskState")
    public Response upTaskState(@RequestBody JSONObject jsonObject, HttpServletRequest request,
                            HttpServletResponse response) {
        try {
            String id = jsonObject.getString("id");
            String state = jsonObject.getString("state");
            String buyerjdnick = jsonObject.getString("buyerjdnick");
            String jdorderno = jsonObject.getString("jdorderno");
//            String bankamount = jsonObject.getString("bankamount");
            TmyTaskDetail td = tmyTaskDetailService.selectById(id);

            Map map = new HashMap();
            map.put("storename",StringUtils.isEmpty(td.getStorename())?"NA":td.getStorename());
            map.put("buyerid",StringUtils.isEmpty(td.getBuyerid())?"NA":td.getBuyerid());
            map.put("mytaskid",StringUtils.isEmpty(td.getMytaskid())?"NA":td.getMytaskid());
            List<TmyTaskDetail> list = tmyTaskDetailService.selectByMap(map);
            if(list!=null&&!list.isEmpty()){
                for (TmyTaskDetail ttd:list) {
                    if(YesNoEnum.YES.code != ttd.getErrorStatus()){
                        ttd.setBuyerjdnick(buyerjdnick);
                        ttd.setJdorderno(jdorderno);
                        tmyTaskDetailService.upTaskState(state,ttd,id);
//                    System.out.print(DateUtils.getDateTime()+"状态修改完成："+ttd.getId()+",状态为："+ttd.getTaskstate());
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            return Response.ok("修改失败！");
        }
        return Response.ok("修改成功！");
    }
    @RequestMapping(value = "ajaxTreeList")
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("list")
    public void ajaxTreeList(Queryable queryable,
                             @RequestParam(value = "nodeid", required = false, defaultValue = "") String nodeid,
                             HttpServletRequest request, HttpServletResponse response, PropertyPreFilterable propertyPreFilterable)
            throws IOException {
        EntityWrapper<TmyTask> entityWrapper = new EntityWrapper<TmyTask>();
        entityWrapper.setTableAlias("t");
        List<TmyTask> treeNodeList = null;


//        if (!async) { // 非异步 查自己和子子孙孙
//            treeNodeList = menuService.selectTreeList(queryable, entityWrapper);
//            TreeSortUtil.create().sort(treeNodeList).async(treeNodeList);
//        } else { // 异步模式只查自己
//            // queryable.addCondition("parentId", nodeid);
//            if (ObjectUtils.isNullOrEmpty(nodeid)) {
//                // 判断的应该是多个OR条件
//                entityWrapper.isNull("parentId");
//            } else {
//                entityWrapper.eq("parentId", nodeid);
//            }
//            treeNodeList = menuService.selectTreeList(queryable, entityWrapper);
//            TreeSortUtil.create().sync(treeNodeList);
//        }
//        propertyPreFilterable.addQueryProperty("id", "expanded", "hasChildren", "leaf", "loaded", "level", "parentId");
//        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<TmyTask> pagejson = new PageResponse<TmyTask>(treeNodeList);
        String content = JSON.toJSONString(pagejson);
        StringUtils.printJson(response, content);
    }
    @RequestMapping(value = "showMyTaskLoad", method = { RequestMethod.GET, RequestMethod.POST })
    public void showMyTaskLoad(@RequestBody JSONObject jsonObject, HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        Map map = new HashMap();
        try {
            String userid = UserUtils.getPrincipal().getId();
            String create1 = jsonObject.getString("create1");
            String create2 = jsonObject.getString("create2");
            String state = jsonObject.getString("state");
            String[] creates = TaskUtils.whereNewDate(create1,create2);
            Map m = new HashMap();
            m.put("userid",userid);
            m.put("create1",creates[0]);
            m.put("create2",creates[1]);
            m.put("state",state);
            map = sumNumAndPrice(m);
        }catch (Exception e){
            e.printStackTrace();
        }
        String content = JSON.toJSONString(map);
        StringUtils.printJson(response,content);
    }

    @RequestMapping(value = "listFinanceBuyerReport", method = { RequestMethod.GET, RequestMethod.POST })
    @Log(logType = LogType.SELECT)
    public void listFinanceBuyerReport(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                                      HttpServletResponse response) throws IOException {
        EntityWrapper<TmyTask> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");

        Date date = new Date();
        String buyerName = "";
        if(queryable.getCondition()!=null){
            Condition.Filter buyerNameFilter = queryable.getCondition().getFilterFor("buyerName");
            if(buyerNameFilter!=null){
                buyerName = "%"+(String)buyerNameFilter.getValue()+"%";
            }
        }
        String[] creates = TaskUtils.whereNewDate("","");
        if(queryable.getCondition()!=null){
            Condition.Filter filter = queryable.getCondition().getFilterFor("countCreateDate");
            creates = TaskUtils.whereDate(filter);
        }
        Map par_map = new HashMap();
        par_map.put("createDate1",creates[0]);
        par_map.put("createDate2",creates[1]);
        par_map.put("buyerName",buyerName);
        List<Map> list = tmyTaskDetailService.listFinanceBuyerReport(par_map);
        Double sumPrice = 0.0;
        Double sumOrderPrice = 0.0;
        Double sumDeliveryPrice = 0.0;
        Double sumFinishPrice = 0.0;
        Long sumNum = 0l;
        Long sumFinishNum = 0l;
        for (Map map:list) {
            sumPrice += Double.parseDouble((map.get("sumPrice")==null?"0":map.get("sumPrice")).toString());
            sumOrderPrice += Double.parseDouble((map.get("sumOrderPrice")==null?"0":map.get("sumOrderPrice")).toString());
            sumDeliveryPrice += Double.parseDouble((map.get("sumDeliveryPrice")==null?"0":map.get("sumDeliveryPrice")).toString());
            sumFinishPrice += Double.parseDouble((map.get("sumFinishPrice")==null?"0":map.get("sumFinishPrice")).toString());
            sumNum += Long.parseLong((map.get("sumNum")==null?"0":map.get("sumNum")).toString());
            sumFinishNum += Long.parseLong((map.get("sumFinishNum")==null?"0":map.get("sumFinishNum")).toString());
        }
        Map map = new HashMap();
        map.put("countCreateDate","");
        map.put("buyerName","合计");
        map.put("sumPrice",sumPrice);
        map.put("sumOrderPrice",sumOrderPrice);
        map.put("sumDeliveryPrice",sumDeliveryPrice);
        map.put("sumFinishPrice",sumFinishPrice);
        map.put("sumNum",sumNum);
        map.put("sumFinishNum",sumFinishNum);
        list.add(map);
        String content = JSON.toJSONString(list);
        StringUtils.printJson(response,content);
    }
}