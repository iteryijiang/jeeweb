package cn.jeeweb.web.ebp.chargeback.controller;

import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.query.data.Condition;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.ebp.chargeback.entity.CanChargeBackTask;
import cn.jeeweb.web.ebp.chargeback.entity.TChargeBackRecord;
import cn.jeeweb.web.ebp.chargeback.service.TChargeBackRecordService;
import cn.jeeweb.web.ebp.enums.BasicRoleEnum;
import cn.jeeweb.web.ebp.shop.util.TaskUtils;
import cn.jeeweb.web.modules.sys.entity.User;
import cn.jeeweb.web.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

/**
 *
 * 退单管理
 */
@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/chargeback/order")
@ViewPrefix("ebp/chargeback")
@RequiresPathPermission("chargeback:order")
@Log(title = "退单管理")
public class ChargebackController extends BaseBeanController<TChargeBackRecord> {

    @Resource(name="tchargeBackRecordService")
    private TChargeBackRecordService tchargeBackRecordService;

    /**
     * 进入允许退单操作管理页面
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "view")
    @RequiresMethodPermissions("view")
    public ModelAndView getCanChargeBackOrderList(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("canChargeBackTaskList");
        return mav;
    }

    /**
     * 获取允许退单的任务列表
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "getCanChargeBackTaskList", method = { RequestMethod.GET, RequestMethod.POST })
    @PageableDefaults(sort = "receivingdate=desc")
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("view")
    public void getCanChargeBackTaskList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        EntityWrapper<CanChargeBackTask> entityWrapper = new EntityWrapper<CanChargeBackTask>(CanChargeBackTask.class);
        propertyPreFilterable.addQueryProperty("id");
        entityWrapper.between("buytd.taskstate", "0", "4");
        if (queryable.getCondition() == null) {
	        Date currentDate= DateUtils.getCurrentDate();
	        String defaultEndTime=DateUtils.getDateEnd(currentDate);
	        String defaultBeginTime=DateUtils.getDateBegin(DateUtils.dateAddDay(currentDate,-3));
	        entityWrapper.between("buytd.receivingdate", defaultBeginTime, defaultEndTime);
        }else {
 			Condition.Filter filterTaskStatus= queryable.getCondition().getFilterFor("buyTaskStatus");
 			if (filterTaskStatus != null) {
 				queryable.getCondition().remove(filterTaskStatus);
 				entityWrapper.eq("buytd.taskstate", filterTaskStatus.getValue().toString());
 			}
 			Condition.Filter filterBuyerTaskNo = queryable.getCondition().getFilterFor("buyerTaskNo");
 			if (filterBuyerTaskNo != null) {
 				queryable.getCondition().remove(filterBuyerTaskNo);
 				entityWrapper.like("buyt.mytaskNo", filterBuyerTaskNo.getValue().toString());
 			}
 			Condition.Filter filterEcommerceOrderNo = queryable.getCondition().getFilterFor("ecommerceOrderNo");
 			if (filterEcommerceOrderNo != null) {
 				queryable.getCondition().remove(filterEcommerceOrderNo);
 				entityWrapper.like("buytd.jdorderno", filterEcommerceOrderNo.getValue().toString());
 			}
 			Condition.Filter filterShopName = queryable.getCondition().getFilterFor("shopName");
 			if (filterShopName != null) {
 				queryable.getCondition().remove(filterShopName);
 				entityWrapper.like("shoptb.shopName", filterShopName.getValue().toString());
 			}
 			Condition.Filter filterBuyerNo = queryable.getCondition().getFilterFor("buyerNo");
 			if (filterBuyerNo != null) {
 				queryable.getCondition().remove(filterBuyerNo);
 				entityWrapper.like("buy.loginName", filterBuyerNo.getValue().toString());
 			}
 			Condition.Filter filterReceivingdate = queryable.getCondition().getFilterFor("receivingdate");
 			if (filterReceivingdate != null) {
 				queryable.getCondition().remove(filterReceivingdate);
 				String[] timeArray=TaskUtils.whereDate(filterReceivingdate);
 				queryable.getCondition().and(Condition.Operator.between, "buytd.receivingdate ",timeArray);
 			}
 		}
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, CanChargeBackTask.class);
        SerializeFilter filter = propertyPreFilterable.constructFilter(CanChargeBackTask.class);
        PageResponse<CanChargeBackTask> pagejson = new PageResponse<CanChargeBackTask>(tchargeBackRecordService.selectCanChargeBackTaskPageList(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response, content);
    }

    /**
     * 进入退单管理页面
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "goToChargeBack/{myTaskDetailId}")
    @RequiresMethodPermissions("view")
    public ModelAndView goToChargeBack(@PathVariable String myTaskDetailId,Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("execChargeBack");
        CanChargeBackTask obj=tchargeBackRecordService.selectCanChargeBackTaskByTaskId(myTaskDetailId);
        model.addAttribute("taskObj", obj);
        return mav;
    }


    /**
     * 进行退单操作
     *
     * @param jsonObject
     * @param request
     * @param response
     * @return
     */
    @PostMapping("chargeBack")
    @Log(logType = LogType.UPDATE)
    @RequiresMethodPermissions("view")
    public Response addChargeBackObj(@RequestBody JSONObject jsonObject, HttpServletRequest request,HttpServletResponse response) {
        try {
            if (StringUtils.isEmpty(jsonObject.getString("buyTaskId"))|| StringUtils.isEmpty(jsonObject.getString("remarks"))) {
                return Response.error("操作失败[未获取到请求参数信息]！");
            }
            TChargeBackRecord obj=new TChargeBackRecord();
            obj.setBuyerTaskId(jsonObject.getString("buyTaskId"));
            obj.setChargeBackReason(jsonObject.getString("remarks"));
            obj.setCreateBy(UserUtils.getUser());
            tchargeBackRecordService.addTChargeBackRecord(obj);
            return Response.ok("操作成功！");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return Response.error("操作失败[" + ex.getMessage() + "]！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.error("操作失败[系统异常]！");
        }
    }

    /**
     * 进入已经完成退单操作的列表页面
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value ="chargeBackOrderList")
    @RequiresMethodPermissions("view")
    public ModelAndView chargeBackOrderList(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("chargeBackOrderList");
        return mav;
    }


    /**
     * 获取已经退单的列表数据
     *
     * @param queryable
     * @param propertyPreFilterable
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "getChargeBackOrderList", method = { RequestMethod.GET, RequestMethod.POST })
    @PageableDefaults(sort = "id=desc")
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("view")
    public void getChargeBackOrderList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        EntityWrapper<TChargeBackRecord> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<TChargeBackRecord> pagejson = new PageResponse<TChargeBackRecord>(tchargeBackRecordService.selectChargeBackRecordPageList(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response, content);
    }

}
