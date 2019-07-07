package cn.jeeweb.web.ebp.buyer.controller;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jeeweb.web.ebp.exception.MyProcessException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
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
import cn.jeeweb.web.ebp.buyer.entity.TBuyerLevel;
import cn.jeeweb.web.ebp.buyer.service.TBuyerLevelService;

@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/buyer/buyerlevel")
@ViewPrefix("ebp/buyer")
@RequiresPathPermission("buyer:buyerlevel")
@Log(title = "买手等级")
public class TBuyerLevelController  extends BaseBeanController<TBuyerLevel> {
	
	@Resource(name ="buyerLevelService")
	private TBuyerLevelService buyerLevelService;
	/**
	 * 买手等级列表页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "view")
	@RequiresMethodPermissions("view")
	public ModelAndView goToBuyerLevelListPage(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = displayModelAndView("l_buyerLevelList");
		return mav;
	}
	

	/***
	 * AJAX查询买手等级列表
	 *
	 * @param queryable
	 * @param propertyPreFilterable
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "ajaxBuyerLevelList", method = { RequestMethod.GET, RequestMethod.POST })
	@Log(logType = LogType.SELECT)
	@RequiresMethodPermissions("view")
	public void getBuyerLevelList(Queryable queryable, PropertyPreFilterable propertyPreFilterable,HttpServletRequest request, HttpServletResponse response) throws IOException {
		String content = null;
		try {
			EntityWrapper<TBuyerLevel> entityWrapper = new EntityWrapper<TBuyerLevel>(entityClass);
			propertyPreFilterable.addQueryProperty("id");
			// 预处理
			QueryableConvertUtils.convertQueryValueToEntityValue(queryable, TBuyerLevel.class);
			SerializeFilter filter = propertyPreFilterable.constructFilter(TBuyerLevel.class);
			PageResponse<TBuyerLevel> pagejson = new PageResponse<TBuyerLevel>(buyerLevelService.selectBuyerLevelPageList(queryable,entityWrapper));
			content = JSON.toJSONString(pagejson, filter);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			StringUtils.printJson(response, content);
		}
	}
	
	
	/**
	 * 初始化编辑
	 * 
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "{id}/update")
	public ModelAndView update(@PathVariable("id") String id, Model model, HttpServletRequest request,HttpServletResponse response) {
		TBuyerLevel retObj = buyerLevelService.getBuyerLevelById(id);
		model.addAttribute("data", retObj);
		return displayModelAndView("l_initBuyerLevel");
	}
	
	/**
	 * 编辑保存
	 * 
	 * @param entity
	 * @param result
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("{id}/update")
	@Log(logType = LogType.UPDATE)
	public Response update(TBuyerLevel entity, BindingResult result, HttpServletRequest request,HttpServletResponse response) {
		try {
			buyerLevelService.updateBuyerLevel(entity);
			return Response.ok("更新成功");
		}catch (MyProcessException ex){
			return Response.error(ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error("系统异常");
		}
	}
}
