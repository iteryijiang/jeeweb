package cn.jeeweb.web.ebp.seller.controller;

import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.data.Condition;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.ebp.buyer.entity.TbuyerInfo;
import cn.jeeweb.web.ebp.seller.entity.TSellerCommissionDateRange;
import cn.jeeweb.web.ebp.seller.entity.TSellerCommissionReport;
import cn.jeeweb.web.ebp.seller.entity.TSellerLevel;
import cn.jeeweb.web.ebp.seller.service.TSellerCommissionPowerService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 销售管理
 *
 * @author : ytj
 * @desc:
 * @date : 2019/7/9 21:37
 */
@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/seller/info")
@ViewPrefix("ebp/seller")
@RequiresPathPermission("seller:info")
@Log(title = "销售管理")
public class TSellerController  extends BaseBeanController<TSellerCommissionReport> {

    @Resource(name = "sellerCommissionPowerService")
    private TSellerCommissionPowerService sellerCommissionPowerService;

    /**
     * 销售人员等级列表查询
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value ="level/view" )
    @RequiresMethodPermissions("level")
    @Log(logType = LogType.SELECT)
    public ModelAndView sellerLevelView(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("sellerLevelList");
        return mav;
    }

    /**
     * 销售人员等级列表查询
     *
     * @param queryable
     * @param propertyPreFilterable
     * @param request
     * @param response
     */
    @RequestMapping(value = "level/ajax", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresMethodPermissions("level")
    @Log(logType = LogType.SELECT)
    public void sellerLevellist(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request, HttpServletResponse response) {
        String content = null;
        try {
            EntityWrapper<TSellerLevel> entityWrapper = new EntityWrapper<TSellerLevel>(TSellerLevel.class);
            propertyPreFilterable.addQueryProperty("id");
            QueryableConvertUtils.convertQueryValueToEntityValue(queryable, TSellerLevel.class);
            SerializeFilter filter = propertyPreFilterable.constructFilter(TSellerLevel.class);
            PageResponse<TSellerLevel> pagejson = new PageResponse<TSellerLevel>(sellerCommissionPowerService.selectSellerLevelPageList(queryable, entityWrapper));
            content = JSON.toJSONString(pagejson, filter);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            StringUtils.printJson(response, content);
        }
    }

    /**
     * 销售人员佣金梯度列表查询
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value ="commission/range" )
    @RequiresMethodPermissions("commission")
    @Log(logType = LogType.SELECT)
    public ModelAndView sellerCommissionRangeView(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("sellerCommissionDateRangeList");
        return mav;
    }

    /**
     * 销售人员佣金梯度列表查询
     *
     * @param queryable
     * @param propertyPreFilterable
     * @param request
     * @param response
     */
    @RequestMapping(value = "commission/range/ajax", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresMethodPermissions("commission")
    @Log(logType = LogType.SELECT)
    public void sellerCommissionRangelist(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request, HttpServletResponse response) {
        String content = null;
        try {
            EntityWrapper<TSellerCommissionDateRange> entityWrapper = new EntityWrapper<TSellerCommissionDateRange>(TSellerCommissionDateRange.class);
            propertyPreFilterable.addQueryProperty("id");
            entityWrapper.orderBy("sltb.id", false);
            entityWrapper.orderBy("scdrtb.begin_day_num", true);
            QueryableConvertUtils.convertQueryValueToEntityValue(queryable, TbuyerInfo.class);
            SerializeFilter filter = propertyPreFilterable.constructFilter(TbuyerInfo.class);
            PageResponse<TSellerCommissionDateRange> pagejson = new PageResponse<TSellerCommissionDateRange>(sellerCommissionPowerService.selectSellerCommissionDateRangePageList(queryable, entityWrapper));
            content = JSON.toJSONString(pagejson, filter);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            StringUtils.printJson(response, content);
        }
    }

    /**
     * 列表查询
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value ="commission/view" )
    @RequiresMethodPermissions("commission")
    @Log(logType = LogType.SELECT)
    public ModelAndView list(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("sellerCommissionList");
        return mav;
    }

    /***
     * 销售佣金列表查询
     *
     * @param queryable
     * @param propertyPreFilterable
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "ajaxListSellerCommission", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresMethodPermissions("commission")
    @Log(logType = LogType.SELECT)
    public void ajaxListSellerCommissionReport(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String content = null;
        try {
            EntityWrapper<TSellerCommissionReport> entityWrapper = new EntityWrapper<TSellerCommissionReport>(entityClass);
            propertyPreFilterable.addQueryProperty("id");
            if (queryable.getCondition() != null) {
                Condition.Filter filter_groupName= queryable.getCondition().getFilterFor("groupName");
                if (filter_groupName != null) {
                    queryable.getCondition().remove(filter_groupName);
                    entityWrapper.like("tb.group_name", filter_groupName.getValue().toString());
                }
            }
            QueryableConvertUtils.convertQueryValueToEntityValue(queryable, TbuyerInfo.class);
            SerializeFilter filter = propertyPreFilterable.constructFilter(TbuyerInfo.class);
            PageResponse<TSellerCommissionReport> pagejson = new PageResponse<TSellerCommissionReport>();
            content = JSON.toJSONString(pagejson, filter);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            StringUtils.printJson(response, content);
        }

    }
}
