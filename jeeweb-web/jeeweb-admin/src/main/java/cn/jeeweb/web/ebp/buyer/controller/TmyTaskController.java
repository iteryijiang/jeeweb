package cn.jeeweb.web.ebp.buyer.controller;

import cn.jeeweb.beetl.tags.dict.DictUtils;
import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mvc.entity.tree.TreeSortUtil;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.ObjectUtils;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.ebp.buyer.entity.TmyTask;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskService;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import cn.jeeweb.web.modules.sys.entity.Menu;
import cn.jeeweb.web.modules.sys.entity.OperationLog;
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
        ModelAndView mav = displayModelAndView("list");
        return mav;
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
    @GetMapping("{id}/{taskState}/upTaskState")
    @Log(logType = LogType.UPDATE)
    @RequiresMethodPermissions("upTaskState")
    public void upTaskState(@PathVariable("id") String id,@PathVariable("taskState") String taskState, HttpServletRequest request,
                            HttpServletResponse response) {
        TmyTaskDetail td = tmyTaskDetailService.selectById(id);
        if(UserUtils.getPrincipal().getId().equals(td.getCreateBy().getId())){
            TmyTask tt = tmyTaskService.selectById(td.getMytaskid());
            td.setTaskstate(taskState);
            if("2".equals(taskState)){
                td.setOrderdate(new Date());
                //确定下单，任务单下单金额增加
                if(tt.getOrderprice()==null){
                    tt.setOrderprice(td.getPays());
                }else {
                    tt.setOrderprice(tt.getOrderprice().add(td.getPays()));
                }
                tmyTaskService.insertOrUpdate(tt);
            }else if("3".equals(taskState)) {
                td.setDeliverydate(new Date());
                //确定下单，任务单发货金额增加
                if(tt.getOrderprice()==null){
                    tt.setDeliveryprice(td.getPays());
                }else {
                    tt.setDeliveryprice(tt.getOrderprice().add(td.getPays()));
                }
                tmyTaskService.insertOrUpdate(tt);
            }else if("4".equals(taskState)) {
                td.setConfirmdate(new Date());
                td.setTaskstatus("1");//修改订单为已完成状态

                //计算我的任务单是否完成
                List<TmyTaskDetail> ttList = tmyTaskDetailService.selectMytaskList(td.getMytaskid());
                boolean ttbool = true;
                for (TmyTaskDetail ttd:ttList) {
                    if(!td.getId().equals(ttd.getId())&&!"1".equals(ttd.getTaskstatus())){
                        ttbool = false;
                    }
                }
                if(ttbool){
                    tt.setState("1");
                    tmyTaskService.insertOrUpdate(tt);
                }

                //计算商家任务单是否完成
                List<TmyTaskDetail> tsList = tmyTaskDetailService.selBaseIdMyTaskDetailList(td.getTaskid());
                boolean tsbool = true;
                for (TmyTaskDetail ttd:tsList) {
                    if(!td.getId().equals(ttd.getId())&&!"1".equals(ttd.getTaskstatus())){
                        tsbool = false;
                    }
                }
                if(tsbool){
                    TtaskBase tb = ttaskBaseService.selectById(td.getTaskid());
                    tb.setStatus("1");
                    ttaskBaseService.insertOrUpdate(tb);
                }
            }
            tmyTaskDetailService.insertOrUpdate(td);
        }
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

}