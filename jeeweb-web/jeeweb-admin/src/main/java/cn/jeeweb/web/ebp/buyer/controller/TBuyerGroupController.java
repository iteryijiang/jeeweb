package cn.jeeweb.web.ebp.buyer.controller;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.web.ebp.buyer.service.TBuyerGroupService;
import cn.jeeweb.web.ebp.exception.MyProcessException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("${jeeweb.admin.url.prefix}/buyer/buyergroup")
@ViewPrefix("ebp/buyer")
@RequiresPathPermission("buyer:buyergroup")
@Log(title = "买手分组")
public class TBuyerGroupController extends BaseBeanController<TBuyerGroup> {

	@Resource(name = "buyerGroupService")
	private TBuyerGroupService buyerGroupService;

	/**
	 * 买手分组列表页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "view")
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
			QueryableConvertUtils.convertQueryValueToEntityValue(queryable, TBuyerGroup.class);
			SerializeFilter filter = propertyPreFilterable.constructFilter(TBuyerGroup.class);
			Page<TBuyerGroup> pageObj=buyerGroupService.selectBuyerGroupPageList(queryable, entityWrapper);
			PageResponse<TBuyerGroup> pagejson = new PageResponse<TBuyerGroup>(pageObj);
			content = JSON.toJSONString(pagejson, filter);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			StringUtils.printJson(response, content);
		}
	}

	
	 /**
     * 关键字查询商铺信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getBuyerGroupSuggest")
    @ResponseBody
    public List<TBuyerGroup> getBuyerGroupSuggest(HttpServletRequest request, HttpServletResponse response){
        try{
            String keyWord = request.getParameter("keyword");
            List<TBuyerGroup> resultList = buyerGroupService.findBuyerGroupByKeyWord(keyWord);
            return resultList;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
	
	
	/***
	 * 查看分组详情
	 * 
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("getBuyerGroup/{id}")
	@Log(logType = LogType.SELECT)
	public ModelAndView getBuyerGroup(@PathVariable("id") String id, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = displayModelAndView("g_buyerGroup_show");
		try {
			TBuyerGroup buyerGroupObj = buyerGroupService.getBuyerGroupById(id);
			model.addAttribute("buyerGroupObj", buyerGroupObj);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mav;
	}

	/**
	 * 新增初始化
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "add")
	public ModelAndView add(Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("data", new TBuyerGroup());
		return displayModelAndView("g_buyerGroup_edit");
	}

	/***
	 * 新增保存
	 * 
	 * @param entity
	 * @param result
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("add")
	@Log(logType = LogType.INSERT)
	public Response add(TBuyerGroup entity, BindingResult result, HttpServletRequest request,HttpServletResponse response) {
		// 验证错误
		buyerGroupService.addBuyerGroup(entity);
		return Response.ok("添加成功");
	}

	/***
	 * 删除
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("{id}/delete")
	@Log(logType = LogType.UPDATE)
	public Response delete(@PathVariable("id") String id,BindingResult result, HttpServletRequest request, HttpServletResponse response) {
		try {
			buyerGroupService.deleteBuyerGroup(id);
			return Response.ok("添加成功");
		}catch (MyProcessException ex){
			return Response.error(ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error("系统异常");
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
	@Log(logType = LogType.SELECT)
	public ModelAndView update(@PathVariable("id") String id, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		TBuyerGroup retObj = buyerGroupService.selectById(id);
		model.addAttribute("data", retObj);
		return displayModelAndView("g_buyerGroup_edit");
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
	public Response update(TBuyerGroup entity, BindingResult result, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			buyerGroupService.updateBuyerGroup(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok("更新成功");
	}

	/***
	 * 查编辑分组组长
	 * 
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("editBuyerGroupLeader/{id}")
	@Log(logType = LogType.SELECT)
	public ModelAndView editBuyerGroupLeader(@PathVariable("id") String id, Model model,HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = displayModelAndView("g_buyerGroup_leader");
		try {
			TBuyerGroup retObj = buyerGroupService.selectById(id);
			model.addAttribute("buyerGroupObj", retObj);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mav;
	}

	/**
	 * 保存分组组长
	 *
	 * @param buyerId
	 * @param groupId
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("saveBuyerLeader/{buyerId}/{groupId}")
	@Log(logType = LogType.UPDATE)
	@ResponseBody
	public Response saveBuyerLeader(@PathVariable("buyerId") String buyerId,@PathVariable("groupId") String groupId, HttpServletRequest request,HttpServletResponse response) {
		try {
			TBuyerGroup obj=new TBuyerGroup();
			obj.setGroupLeader(buyerId);
			obj.setId(groupId);
			buyerGroupService.updateBuyerGroupForUpdateLeader(obj);
			return Response.ok("操作成功！");
		} catch (MyProcessException ex) {
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
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("editBuyerGroupMember/{id}")
	@Log(logType = LogType.SELECT)
	public ModelAndView editBuyerGroupMember(@PathVariable("id") String id, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = displayModelAndView("g_buyerGroup_member");
		try {
			TBuyerGroup retObj = buyerGroupService.selectById(id);
			model.addAttribute("buyerGroupObj", retObj);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mav;
	}

}
