package cn.jeeweb.web.ebp.shop.controller;

import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/shop/TshopInfo")
@ViewPrefix("ebp/shop")
@RequiresPathPermission("shop:TshopInfo")
@Log(title = "订单详情")
public class TshopInfoController extends BaseBeanController<TshopInfo> {

    @GetMapping
    @RequiresMethodPermissions("view")
    public ModelAndView list(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("ReleaseTask");
        System.out.println(mav.getViewName());
        return mav;
    }

}