package cn.jeeweb.web.ebp.buyer.controller;

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
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
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
import java.util.ArrayList;
import java.util.List;


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
    @PageableDefaults(sort = "id=desc")
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
        //tmyTask.setTasktype(DictUtils.getDictValue(""));
        model.addAttribute("tmyTask", tmyTask);
        TtaskBase taskbase = ttaskBaseService.selectById(tmyTask.getTaskid());
        model.addAttribute("taskbase",taskbase);
        return displayModelAndView("MyTaskDetail");
    }

    @RequestMapping(value = "selBaseIdMyTaskList", method = { RequestMethod.GET, RequestMethod.POST })
    @PageableDefaults(sort = "id=desc")
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("selBaseIdMyTaskList")
    public void selBaseIdMyTaskList(@RequestBody JSONObject jsonObject, HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {

        String taskId = jsonObject.getString("taskId");
        List<TmyTask> list = tmyTaskService.selBaseIdMyTaskList(taskId);
        List<TmyTask> newlist = new ArrayList<TmyTask>();
        for (TmyTask t :list){
            t.setTasktype(DictUtils.getDictLabel(t.getTasktype(),"tasktype",t.getTasktype()));
            newlist.add(t);
        }
        String content = JSON.toJSONString(newlist);
        StringUtils.printJson(response,content);
    }


    @GetMapping("{id}/updateTaskState")
    @Log(logType = LogType.SELECT)
    public ModelAndView updateTaskState(Model model,@PathVariable("id") String id) {
        TmyTask tmyTask = tmyTaskService.selectById(id);
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




}