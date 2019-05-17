package cn.jeeweb.web.ebp.notice.controller;

import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.ebp.notice.entity.NoticeInfo;
import cn.jeeweb.web.ebp.notice.service.NoticeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version v1.0.1
 * @Author ytj
 * @Date 2019/5/14
 * @Description
 */
@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/notice/noticeInfo")
@ViewPrefix("ebp/buyer")
@RequiresPathPermission("notice:noticeInfo")
@Log(title = "消息通知")
public class NoticeCtroller extends BaseBeanController<NoticeInfo> {
    @Autowired
    private NoticeService noticeService;

    @GetMapping
    @RequiresMethodPermissions("view")
    public ModelAndView list(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("noticeList");
        return mav;
    }

    /***
     * 获取单条消息通知
     *
     * @param id
     * @param request
     * @param response
     * @return
     */
    @GetMapping("getNotice/{id}")
    @RequiresMethodPermissions("detail")
    public ModelAndView TaskDetail(@PathVariable("id") long id,Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("noticeDetail");
        NoticeInfo noticeObj=noticeService.getNoticeById(id);
        model.addAttribute("noticeObj",noticeObj);
        return mav;
    }
}
