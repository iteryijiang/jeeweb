package cn.jeeweb.web.ebp.buyer.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerCommissionRecord;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerLevel;

@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/buyer/buyerCommission")
@ViewPrefix("ebp/buyer")
@RequiresPathPermission("buyer:buyerCommission")
@Log(title = "买手佣金")
public class TBuyerCommissionController  extends BaseBeanController<TBuyerCommissionRecord> {

	/**
	 * 买手佣金列表页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "view")
	@RequiresMethodPermissions("view")
	public ModelAndView goToBuyerCommissionListPage(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = displayModelAndView("c_buyerCommissionGroupList");
		return mav;
	}
	

	/***
	 * AJAX查询买手佣金列表
	 *
	 * @param queryable
	 * @param propertyPreFilterable
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "ajaxBuyerCommissionlList", method = { RequestMethod.GET, RequestMethod.POST })
	@Log(logType = LogType.SELECT)
	@RequiresMethodPermissions("view")
	public void getBuyerCommissionList(Queryable queryable, PropertyPreFilterable propertyPreFilterable,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		String content = null;
		try {
			EntityWrapper<TBuyerCommissionRecord> entityWrapper = new EntityWrapper<TBuyerCommissionRecord>(entityClass);
			propertyPreFilterable.addQueryProperty("id");
			
			// 预处理
			QueryableConvertUtils.convertQueryValueToEntityValue(queryable, TBuyerLevel.class);
			SerializeFilter filter = propertyPreFilterable.constructFilter(TBuyerLevel.class);
			PageResponse<TBuyerCommissionRecord> pagejson = new PageResponse<TBuyerCommissionRecord>();
			content = JSON.toJSONString(pagejson, filter);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			StringUtils.printJson(response, content);
		}
	}
	
	/***
	 * 查看佣金详情
	 * 一条佣金对应的任务链接明细详情
	 * 
	 * @param applyId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("getBuyerCommissionDetail/{id}")
	@Log(logType = LogType.SELECT)
	public ModelAndView getBuyerCommissionDetail(@PathVariable("id") String applyId, Model model,HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = displayModelAndView("c_buyerCommissionDetail");
		try {
			
			model.addAttribute("buyerCommissionObj", null);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mav;
	}
}
