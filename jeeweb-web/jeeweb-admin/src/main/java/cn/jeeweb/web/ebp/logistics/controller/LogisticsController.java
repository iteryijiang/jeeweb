package cn.jeeweb.web.ebp.logistics.controller;

import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.query.data.*;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.ebp.enums.BasicRoleEnum;
import cn.jeeweb.web.ebp.enums.BuyerTaskStatusEnum;
import cn.jeeweb.web.ebp.exception.MyProcessException;
import cn.jeeweb.web.ebp.logistics.entity.TLogisticsOrder;
import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShow;
import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShowQuery;
import cn.jeeweb.web.ebp.logistics.service.TLogisticsOrderService;
import cn.jeeweb.web.ebp.logistics.service.TShopOrderShowService;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import cn.jeeweb.web.modules.sys.entity.User;
import cn.jeeweb.web.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/logisticsOrder/logistics")
@ViewPrefix("ebp/logisticsOrder")
@RequiresPathPermission("logisticsOrder:logistics")
@Log(title = "物流信息处理")
public class LogisticsController extends BaseBeanController<TLogisticsOrder> {

    @Autowired
    private TShopOrderShowService shopOrderShowService;
    @Autowired
    private TLogisticsOrderService logisticsOrderService;
    @Autowired
    private TshopInfoService tshopInfoService;

    /**
     * 列表展示所有的商户订单信息
     * 默认展示最近3天数据
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "shopOrderList")
    @Log(logType = LogType.SELECT)
    public ModelAndView goToAllShopOrderListPage(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("list_logistics_shopOrderList");
        TshopInfo shopInfo = tshopInfoService.selectOne(UserUtils.getUser().getId());
        if(shopInfo != null){
            mav.addObject("availabledeposit",shopInfo.getAvailabledeposit());
        }else{
            mav.addObject("availabledeposit",0);
        }
        Date today=DateUtils.getCurrentDate();
        mav.addObject("beginDate",DateUtils.getDateBegin(DateUtils.addDays(today,-2)));
        mav.addObject("endDate",DateUtils.getDateBegin(today));
        return mav;
    }

    /**
     * 商户订单信息查询
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "ajaxShopOrderList", method = {RequestMethod.POST})
    @Log(logType = LogType.SELECT)
    public String ajaxShopOrderList(@RequestBody String paramStr, HttpServletRequest request, HttpServletResponse response) {
        JSONObject retObj = new JSONObject();
        retObj.put("retCode", 0);
        retObj.put("retMsg", "success");
        try {
            JSONObject queryJson=JSONObject.parseObject(paramStr);
            String beginDate=queryJson.getString("beginDate");
            String endDate=queryJson.getString("endDate");
            String jdOrderNo=queryJson.getString("jdOrderNo");
            String buyerNo=queryJson.getString("buyerNo");
            String currentPage=queryJson.getString("currentPage");
            String pageSize=queryJson.getString("pageSize");
            String queryType=queryJson.getString("queryType");
            TShopOrderShowQuery queryParam = new TShopOrderShowQuery();
            setShopOrderQueryId(queryParam);
            queryParam.setBuyerNo(buyerNo);
            Date today=DateUtils.getCurrentDate();
            if(StringUtils.isEmpty(beginDate)){
                beginDate=DateUtils.getDateBegin(DateUtils.addDays(today,-30));
            }
            queryParam.setBeginDate(beginDate);
            if(StringUtils.isEmpty(endDate)){
                endDate=DateUtils.getDateBegin(today);
            }
            queryParam.setEndDate(endDate);
            queryParam.setJdOrderNo(jdOrderNo);
            queryParam.setTaskStatus(Integer.valueOf(queryType));
            Queryable queryable=new QueryRequest();
            Pageable pageable=new PageRequest(Integer.valueOf(currentPage),Integer.valueOf(pageSize));
            queryable.setPageable(pageable);
            Page<TShopOrderShow> pageObj=shopOrderShowService.selectTShopOrderShowPageList(queryable, queryParam);

            if (pageObj == null) {
                retObj.put("retCode", -1);
                retObj.put("retMsg", "未获取到数据，调整检索条件试一试吧!!!");
            } else {
                PageResponse<TShopOrderShow> pagejson = new PageResponse<>(pageObj);
                retObj.put("retData", pagejson);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            retObj.put("retCode", -1);
            retObj.put("retMsg", "查询数据系统异常");
        } finally {
            return retObj.toJSONString();
        }
    }


    /**
     * 设置查询申请所属的用户ID
     *
     * @param queryParam
     */
    private void setShopOrderQueryId(TShopOrderShowQuery queryParam) {
        // 当前登录人
        Set<String> roleSet = UserUtils.getRoleStringList();
        User loginUser = UserUtils.getUser();
       // queryParam.setShopUserId(StringUtils.randomUUID());
        if (!roleSet.isEmpty()) {
            for (String roleId : roleSet) {
                // 商户运营,设置商户的ID
                if (BasicRoleEnum.SHOP.roleCode.equals(roleId)) {
                    queryParam.setShopUserId(loginUser.getId());
                    return;
                }
            }
        }
    }

    /**
     * 商户提出出库申请
     *
     * @param paramStr
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "addOutStoreOrder", method = {RequestMethod.POST})
    @Log(logType = LogType.INSERT)
    public Response addOutStoreOrder(@RequestBody String paramStr, HttpServletRequest request, HttpServletResponse response) {
        try {
            JSONObject paramJson=JSONObject.parseObject(paramStr);
            String jdOrderNoParam=paramJson.getString("jdOrderNo");
            if(StringUtils.isEmpty(jdOrderNoParam)){
                return Response.error("操作失败[未获取到订单编号信息]");
            }
            String[] jdOrderNoArray=jdOrderNoParam.split(",");
            List<TLogisticsOrder> objList=new ArrayList<>();
            String batchId=StringUtils.randomUUID();
            for(String jdOrderNo:jdOrderNoArray){
                if(StringUtils.isNotEmpty(jdOrderNo)){
                    TLogisticsOrder objInsert=new TLogisticsOrder();
                    objInsert.setJdOrderNo(jdOrderNo);
                    objInsert.setOutStoreBatchId(batchId);
                    objList.add(objInsert);
                }
            }
            logisticsOrderService.insertTLogisticsOrder(objList);
        } catch (MyProcessException ex) {
            ex.printStackTrace();
            return Response.error("操作失败[" + ex.getMessage() + "]");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.error("操作失败[系统异常]");
        }
        return Response.ok();
    }

    /**
     * 列表展示所有未出库的订单信息
     * 默认展示最近3天数据
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "platformOrderList")
    @Log(logType = LogType.SELECT)
    public ModelAndView goToAllPlatformOrderListPage(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("list_logistics_platformOrderList");
        return mav;
    }

    /**
     * 平台展示需要出库订单信息查询
     *
     * @param queryable
     * @param propertyPreFilterable
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "ajaxPlatformOrderList", method = {RequestMethod.GET, RequestMethod.POST})
    @Log(logType = LogType.SELECT)
    public void ajaxPlatformOrderList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                                      HttpServletResponse response) {
        try {
            EntityWrapper<TLogisticsOrder> entityWrapper = new EntityWrapper<>(TLogisticsOrder.class);
            propertyPreFilterable.addQueryProperty("id");
            // 预处理
            QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
            SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
            PageResponse<TLogisticsOrder> pagejson = new PageResponse<TLogisticsOrder>(logisticsOrderService.selectTLogisticsOrderPageList(queryable,entityWrapper));
            String content = JSON.toJSONString(pagejson, filter);
            StringUtils.printJson(response, content);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 同步订单出库物流信息
     *
     * @param paramStr
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "updateLogsticsOrderStatus", method = {RequestMethod.POST})
    @Log(logType = LogType.UPDATE)
    public Response updateLogsticsOrderStatus(@RequestBody String paramStr, HttpServletRequest request, HttpServletResponse response) {
        try {
            JSONObject paramJson=JSONObject.parseObject(paramStr);
            String outStoreId=paramJson.getString("outStoreId");
            logisticsOrderService.updateTLogisticsOrderStatus(outStoreId, BuyerTaskStatusEnum.WAITING_ACCEPT.code);
        } catch (MyProcessException ex) {
            ex.printStackTrace();
            return Response.error("操作失败[" + ex.getMessage() + "]");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.error("操作失败[系统异常]");
        }
        return Response.ok();
    }
}
