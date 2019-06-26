package cn.jeeweb.web.ebp.buyer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.web.aspectj.annotation.Log;

@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/buyer/buyerInfo")
@ViewPrefix("ebp/buyerlevel")
@RequiresPathPermission("buyer:buyerInfo")
@Log(title = "买手等级")
public class TBuyerLevelController {

}
