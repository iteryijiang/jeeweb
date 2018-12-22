package cn.jeeweb.web.ebp.buyer.controller;

import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/shop/TmyTaskDetail")
@ViewPrefix("ebp/buyer")
@RequiresPathPermission("shop:TmyTaskDetail")
@Log(title = "抢单详情")
public class TmyTaskDetailController extends BaseBeanController<TmyTaskDetail> {

    @Autowired
    private TtaskBaseService ttaskBaseService;

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
//        TtaskBase entity = new TtaskBase();
//        System.out.println(request.getParameter("taskBase"));

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

}