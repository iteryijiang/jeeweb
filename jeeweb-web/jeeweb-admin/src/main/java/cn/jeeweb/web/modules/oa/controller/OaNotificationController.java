package cn.jeeweb.web.modules.oa.controller;

import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.ebp.enums.BasicRoleEnum;
import cn.jeeweb.web.ebp.enums.NotificationTypeRangeEnum;
import cn.jeeweb.web.ebp.enums.YesNoEnum;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import cn.jeeweb.web.modules.oa.entity.OaNotification;
import cn.jeeweb.web.modules.oa.service.IOaNotificationService;
import cn.jeeweb.web.modules.sys.entity.User;
import cn.jeeweb.web.utils.UserUtils;



/**   
 * @Title: 通知公告
 * @Description: 通知公告
 * @author jeeweb
 * @date 2017-06-10 17:15:17
 * @version V1.0   
 *
 */
@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/oa/oanotification")
@ViewPrefix("modules/oa/oanotification")
@RequiresPathPermission("oa:oanotification")
@Log(title = "通知公告")
public class OaNotificationController extends BaseBeanController<OaNotification>{
	
	@Autowired
	private IOaNotificationService oaNotificationService;
	@Autowired
    private TshopInfoService tshopInfoService;

	/**
	 * 进入列表页
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping
	@RequiresMethodPermissions("view")
	public ModelAndView list(Model model, HttpServletRequest request, HttpServletResponse response) {
		return displayModelAndView("list");
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
	public void ajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request, HttpServletResponse response) throws IOException {
		EntityWrapper<OaNotification> entityWrapper = new EntityWrapper<>(entityClass);
		propertyPreFilterable.addQueryProperty("id");
		//设置检索所属的商户/买手/角色ID
		setNotifyQueryRoleId(entityWrapper);
		// 预处理
		QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
		entityWrapper.orderBy("create_date", false);
		SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
		PageResponse<OaNotification> pagejson = new PageResponse<>(oaNotificationService.list(queryable,entityWrapper));
		String content = JSON.toJSONString(pagejson, filter);
		StringUtils.printJson(response,content);
	}

	/**
	 * 显示更多通知
	 *
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "showMoreList")
	public ModelAndView showMoreList(Model model, HttpServletRequest request, HttpServletResponse response) {
		return displayModelAndView("showMoreNotifyList");
	}

	/**
	 * 根据页码和每页记录数，以及查询条件动态加载数据
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "getLatestNotifyList", method = { RequestMethod.GET, RequestMethod.POST })
	@PageableDefaults(sort = "id=desc")
	@Log(logType = LogType.SELECT)
	public JSONObject getLatestNotifyList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request, HttpServletResponse response) throws IOException {
		EntityWrapper<OaNotification> entityWrapper = new EntityWrapper<>(entityClass);
		propertyPreFilterable.addQueryProperty("id");
		//设置检索所属的商户/买手/角色ID
		setNotifyQueryRoleId(entityWrapper);
		entityWrapper.orderBy("create_date", false);
		PageResponse<OaNotification> pagejson = new PageResponse<>(oaNotificationService.list(queryable,entityWrapper));
		List<OaNotification> retList= pagejson.getResults();
		JSONObject retObj=new JSONObject();
		retObj.put("retCode",pagejson.getRows()>0?0:-1);
		retObj.put("retData",retList);
		return retObj;
	}
	
	/**
	 * 设置查询申请所属的用户ID
	 * 
	 * @param queryWrapper
	 */
	private void setNotifyQueryRoleId(EntityWrapper<OaNotification> queryWrapper) {
		// 当前登录人
		Set<String> roleSet = UserUtils.getRoleStringList();
		User loginUser = UserUtils.getUser();
		if (!roleSet.isEmpty()) {
			for (String roleId : roleSet) {
				// 管理员
				if (BasicRoleEnum.ADMIN.roleCode.equals(roleId)) {
					return;
				}
				// 商户运营,设置商户的ID
				if (BasicRoleEnum.SHOP.roleCode.equals(roleId)) {
					TshopInfo shopObj=tshopInfoService.selectOne(loginUser.getId());
					queryWrapper.eq("notificationType", shopObj.getFromInnerOuter());
					queryWrapper.eq("status",1);
					return;
				}
				// 销售当做外部商户处理
				if (BasicRoleEnum.SELLING.roleCode.equals(roleId)) {
					queryWrapper.eq("notificationType", NotificationTypeRangeEnum.SHOP_OUTER.code);
					queryWrapper.eq("status",1);
					return;
				}
				// 买手,设置买手的ID
				if (BasicRoleEnum.BUYER.roleCode.equals(roleId)) {
					queryWrapper.eq("notificationType", NotificationTypeRangeEnum.BUYER.code);
					queryWrapper.eq("status",1);
					return;
				}
			}
		}
	}

	/**
	 * 进入新增页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "add")
	public ModelAndView add(Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("data", new OaNotification());
		return displayModelAndView ("edit");
	}

	/**
	 * 新增公告信息
	 * 
	 * @param entity
	 * @param result
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("add")
	@Log(logType = LogType.INSERT)
	@RequiresMethodPermissions("add")
	public Response add(OaNotification entity, BindingResult result,HttpServletRequest request, HttpServletResponse response) {
		// 验证错误
		this.checkError(entity,result);
		oaNotificationService.insert(entity);
		return Response.ok("添加成功");
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
		OaNotification entity = oaNotificationService.selectById(id);
		model.addAttribute("data", entity);
		return displayModelAndView ("edit");
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
	@RequiresMethodPermissions("update")
	public Response update(OaNotification entity, BindingResult result,
						   HttpServletRequest request, HttpServletResponse response) {
		// 验证错误
		this.checkError(entity,result);
		oaNotificationService.updateById(entity);
		return Response.ok("更新成功");
	}

	/**
	 * 单个删除数据
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("{id}/delete")
	@Log(logType = LogType.DELETE)
	@RequiresMethodPermissions("delete")
	public Response delete(@PathVariable("id") String id) {
		oaNotificationService.deleteById(id);
		return Response.ok("删除成功");
	}

	/***
	 * 批量删除数据
	 * 
	 * @param ids
	 * @return
	 */
	@PostMapping("batch/delete")
	@Log(logType = LogType.DELETE)
	@RequiresMethodPermissions("delete")
	public Response batchDelete(@RequestParam("ids") String[] ids) {
		List<String> idList = java.util.Arrays.asList(ids);
		oaNotificationService.deleteBatchIds(idList);
		return Response.ok("删除成功");
	}

	/**
	 * 详情页面
	 *
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "notifyDetail/{id}")
	public ModelAndView list(@PathVariable String id,Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = displayModelAndView("notifyShow");
		try {
			OaNotification obj = oaNotificationService.selectById(id);
			model.addAttribute("notifyObj", obj);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mav;
	}
}
