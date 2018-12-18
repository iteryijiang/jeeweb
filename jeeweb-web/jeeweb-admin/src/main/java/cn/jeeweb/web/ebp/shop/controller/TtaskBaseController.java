package cn.jeeweb.web.ebp.shop.controller;

import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
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
@RequestMapping("${jeeweb.admin.url.prefix}/shop/TtaskBase")
@ViewPrefix("ebp/shop")
@RequiresPathPermission("shop:TtaskBase")
@Log(title = "订单管理")
public class TtaskBaseController extends BaseBeanController<TtaskBase> {

    @Autowired
    private TtaskBaseService ttaskBaseService;

    @GetMapping
    @RequiresMethodPermissions("view")
    public ModelAndView list(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("ReleaseTask");
        System.out.println(mav.getViewName());
        return mav;
    }

    @GetMapping(value = "TaskDetail")
    @RequiresMethodPermissions("TaskDetail")
    public ModelAndView TaskDetail(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("TaskDetail");
        System.out.println(mav.getViewName());
        return mav;
    }

    @PostMapping("add")
    @Log(logType = LogType.INSERT)
    @RequiresMethodPermissions("add")
    public Response add(TtaskBase entity, BindingResult result,
                        HttpServletRequest request, HttpServletResponse response) {

        ttaskBaseService.insert(entity);
        //保存之后
        return Response.ok("添加成功");
    }

}