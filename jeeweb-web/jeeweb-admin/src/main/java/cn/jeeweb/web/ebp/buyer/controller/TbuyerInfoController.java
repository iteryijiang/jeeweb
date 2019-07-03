package cn.jeeweb.web.ebp.buyer.controller;

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
import cn.jeeweb.web.ebp.buyer.entity.TbuyerInfo;
import cn.jeeweb.web.ebp.buyer.service.TbuyerInfoService;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/buyer/TbuyerInfo")
@ViewPrefix("ebp/buyer")
@RequiresPathPermission("buyer:TbuyerInfo")
@Log(title = "买手管理")
public class TbuyerInfoController extends BaseBeanController<TbuyerInfo> {

    @Autowired
    private TtaskBaseService ttaskBaseService;
    @Resource(name = "tbuyerInfoService")
    private TbuyerInfoService tbuyerInfoService;

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
            BeanUtils.copyProperties(tb,entity,"tType,tUrl,tTitle,tPrice,tNum,totalPrice,searchPrice");
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







/***********买手帐号信息************/
    /**
     * 进入买手帐号页面
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "gotoBuyerList")
    @RequiresMethodPermissions("view")
    public ModelAndView gotoBuyerList(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("b_buyerInfoList");
        return mav;
    }

   /**
    * 查询买手列表
    * 
    * @param id
    * @param queryable
    * @param propertyPreFilterable
    * @param request
    * @param response
    * @throws IOException
    */
    @RequestMapping(value = "ajaxListBuyer", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresMethodPermissions("view")
    public void ajaxListBuyer(Queryable queryable, PropertyPreFilterable propertyPreFilterable,HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String content = null;
		try {
			EntityWrapper<TbuyerInfo> entityWrapper = new EntityWrapper<TbuyerInfo>(entityClass);
			propertyPreFilterable.addQueryProperty("id");
			// 预处理
			QueryableConvertUtils.convertQueryValueToEntityValue(queryable, TbuyerInfo.class);
			SerializeFilter filter = propertyPreFilterable.constructFilter(TbuyerInfo.class);
			PageResponse<TbuyerInfo> pagejson = new PageResponse<TbuyerInfo>(tbuyerInfoService.selectBuyerInfoPageList(queryable, entityWrapper));
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
	public ModelAndView update(@PathVariable("id") String id, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		TbuyerInfo retObj = tbuyerInfoService.getTbuyerInfoById(id);
		model.addAttribute("data", retObj);
		return displayModelAndView("b_buyerInfo_edit");
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
	public Response update(TbuyerInfo entity, BindingResult result, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			tbuyerInfoService.updateTbuyerInfoById(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok("更新成功");
	}
	
}