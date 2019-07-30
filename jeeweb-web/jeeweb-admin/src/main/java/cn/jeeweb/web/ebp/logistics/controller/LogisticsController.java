package cn.jeeweb.web.ebp.logistics.controller;

import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.query.data.Condition;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.ebp.logistics.entity.TLogisticsOrder;
import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShow;
import cn.jeeweb.web.ebp.logistics.entity.TShopOrderShowQuery;
import cn.jeeweb.web.ebp.logistics.service.TLogisticsOrderService;
import cn.jeeweb.web.ebp.logistics.service.TShopOrderShowService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        return mav;
    }

    /**
     * 商户订单信息查询
     *
     * @param queryable
     * @param propertyPreFilterable
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "ajaxShopOrderList", method = {RequestMethod.GET, RequestMethod.POST})
    @PageableDefaults(sort = "create_date=desc")
    @Log(logType = LogType.SELECT)
    public String ajaxShopOrderList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request, HttpServletResponse response) {
        JSONObject retObj=new JSONObject();
        retObj.put("retCode",0);
        retObj.put("retMsg","success");
        try {
            EntityWrapper<TShopOrderShow> entityWrapper = new EntityWrapper<>(TShopOrderShow.class);
            propertyPreFilterable.addQueryProperty("id");
            // 预处理
            QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
            TShopOrderShowQuery queryParam=new TShopOrderShowQuery();
            PageResponse<TShopOrderShow> pagejson = new PageResponse<>(shopOrderShowService.selectTShopOrderShowPageList(queryable, queryParam));
            if(pagejson == null ||pagejson.isEmpty() ||pagejson.getTotal()<1){
                retObj.put("retCode",-1);
                retObj.put("retMsg","未获取到数据，调整检索条件试一试吧!!!");
            }else{
                retObj.put("retData",pagejson);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            retObj.put("retCode",-1);
            retObj.put("retMsg","查询数据系统异常");
        }finally {
            return retObj.toJSONString();
        }
    }

    /**
     * 商户提出出库申请
     *
     * @param queryable
     * @param propertyPreFilterable
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "addOutStoreOrder", method = {RequestMethod.GET, RequestMethod.POST})
    @Log(logType = LogType.INSERT)
    public void addOutStoreOrder(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request, HttpServletResponse response) throws IOException {
        EntityWrapper<TShopOrderShow> entityWrapper = new EntityWrapper<>(TShopOrderShow.class);
        propertyPreFilterable.addQueryProperty("id");

        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<TShopOrderShow> pagejson = null;
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response, content);
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
    @PageableDefaults(sort = "create_date=desc")
    @Log(logType = LogType.SELECT)
    public void ajaxPlatformOrderList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                                      HttpServletResponse response) {
        try {
            EntityWrapper<TShopOrderShow> entityWrapper = new EntityWrapper<>(TShopOrderShow.class);
            propertyPreFilterable.addQueryProperty("id");

            // 预处理
            QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
            SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
            PageResponse<TShopOrderShow> pagejson = null;
            String content = JSON.toJSONString(pagejson, filter);
            StringUtils.printJson(response, content);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 同步订单出库物流信息
     *
     * @param queryable
     * @param propertyPreFilterable
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "updateLogsticsOrderStatus", method = {RequestMethod.GET, RequestMethod.POST})
    @Log(logType = LogType.UPDATE)
    public void updateLogsticsOrderStatus(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request, HttpServletResponse response) throws IOException {
        EntityWrapper<TShopOrderShow> entityWrapper = new EntityWrapper<>(TShopOrderShow.class);
        propertyPreFilterable.addQueryProperty("id");

        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<TShopOrderShow> pagejson = null;
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response, content);
    }
}
