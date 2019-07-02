package cn.jeeweb.web.ebp.buyer.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import cn.jeeweb.web.ebp.buyer.entity.TBuyerGroup;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerLevel;

@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/buyer/buyerInfo")
@ViewPrefix("ebp/buyergroup")
@RequiresPathPermission("buyer:buyerInfo")
@Log(title = "买手分组")
public class TBuyerGroupController  extends BaseBeanController<TBuyerGroup> {

	/**
	 * 买手分组列表页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "buyerGroupList")
	@RequiresMethodPermissions("view")
	public ModelAndView goToBuyerGroupListPage(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = displayModelAndView("g_buyerGroupList");
		return mav;
	}
	

	/***
	 * AJAX查询买手分组列表
	 *
	 * @param queryable
	 * @param propertyPreFilterable
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "ajaxBuyerGrouplList", method = { RequestMethod.GET, RequestMethod.POST })
	@Log(logType = LogType.SELECT)
	@RequiresMethodPermissions("view")
	public void getBuyerGroupList(Queryable queryable, PropertyPreFilterable propertyPreFilterable,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		String content = null;
		try {
			EntityWrapper<TBuyerGroup> entityWrapper = new EntityWrapper<TBuyerGroup>(entityClass);
			propertyPreFilterable.addQueryProperty("id");
			
			// 预处理
			QueryableConvertUtils.convertQueryValueToEntityValue(queryable, TBuyerLevel.class);
			SerializeFilter filter = propertyPreFilterable.constructFilter(TBuyerLevel.class);
			PageResponse<TBuyerGroup> pagejson = new PageResponse<TBuyerGroup>();
			content = JSON.toJSONString(pagejson, filter);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			StringUtils.printJson(response, content);
		}
	}
	
	/***
	 * 查看分组详情
	 * 
	 * @param applyId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("getBuyerGroup/{id}")
	@Log(logType = LogType.SELECT)
	public ModelAndView getBuyerGroup(@PathVariable("id") String applyId, Model model,HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = displayModelAndView("g_buyerGroup_show");
		try {
			
			model.addAttribute("buyerGroupObj", null);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mav;
	}
	
	/***
	 * 编辑分组信息
	 * 新增操作
	 * 
	 * @param applyId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("editBuyerGroup/{id}")
	@Log(logType = LogType.SELECT)
	public ModelAndView editBuyerGroup(@PathVariable("id") String applyId, Model model,HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = displayModelAndView("g_buyerGroup_edit");
		try {
			
			model.addAttribute("buyerGroupObj", null);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mav;
	}
	
	/***
	 *保存买手分组信息
	 * 
	 * @param jsonObject
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("saveBuyerGroup")
	@Log(logType = LogType.UPDATE)
	public Response saveBuyerGroup(@RequestBody JSONObject jsonObject, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			
			return Response.ok("操作成功！");
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return Response.error("操作失败[" + ex.getMessage() + "]！");
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.error("操作失败[系统异常]！");
		}
	}
	
	/***
	 * 查编辑分组组长
	 * 
	 * @param applyId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("editBuyerGroupLeader/{id}")
	@Log(logType = LogType.SELECT)
	public ModelAndView editBuyerGroupLeader(@PathVariable("id") String applyId, Model model,HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = displayModelAndView("g_buyerGroup_leader");
		try {
			
			model.addAttribute("buyerGroupObj", null);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mav;
	}
	
	/***
	 *保存分组组长
	 * 
	 * @param jsonObject
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("saveBuyerLeader")
	@Log(logType = LogType.UPDATE)
	public Response saveBuyerMLeader(@RequestBody JSONObject jsonObject, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			
			return Response.ok("操作成功！");
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return Response.error("操作失败[" + ex.getMessage() + "]！");
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.error("操作失败[系统异常]！");
		}
	}
	
	/***
	 * 编辑分组成员
	 * 
	 * @param applyId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("editBuyerGroupMember/{id}")
	@Log(logType = LogType.SELECT)
	public ModelAndView editBuyerGroupMember(@PathVariable("id") String applyId, Model model,HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = displayModelAndView("g_buyerGroup_member");
		try {
			
			model.addAttribute("buyerGroupObj", null);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mav;
	}
	
	/***
	 *保存编辑分组成员
	 * 
	 * @param jsonObject
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("saveBuyerMember")
	@Log(logType = LogType.UPDATE)
	public Response saveBuyerMember(@RequestBody JSONObject jsonObject, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			
			return Response.ok("操作成功！");
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return Response.error("操作失败[" + ex.getMessage() + "]！");
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.error("操作失败[系统异常]！");
		}
	}
}
