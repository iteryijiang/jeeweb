package cn.jeeweb.web.ebp.finance.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.view.PoiBaseView;
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
import cn.jeeweb.web.ebp.finance.entity.TfinanceBuyerReport;
import cn.jeeweb.web.ebp.finance.service.TfinanceBuyerReportService;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import cn.jeeweb.web.ebp.shop.spider.TsequenceSpider;
import cn.jeeweb.web.ebp.shop.util.TaskUtils;
import cn.jeeweb.web.modules.sys.entity.LoginLog;
import cn.jeeweb.web.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/finance/tfinanceBuyerReport")
@ViewPrefix("ebp/finance")
@RequiresPathPermission("finance:TfinanceBuyerReport")
@Log(title = "抢单管理")
public class TfinanceBuyerReportController extends BaseBeanController<TfinanceBuyerReport> {

    @Autowired
    private TfinanceBuyerReportService tfinanceBuyerReportService;

    @Autowired
    private TshopInfoService tshopInfoService;


    @GetMapping
    @RequiresMethodPermissions("view")
    public ModelAndView TaskList(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("list_finance_buyer_report");
        return mav;
    }

    @GetMapping(value = "add")
    public ModelAndView add(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("data", new TfinanceBuyerReport());

        List<TshopInfo> lsit = tshopInfoService.selectByMap(new HashMap<>());
        model.addAttribute("booksQueryList", lsit);

        return displayModelAndView ("edit");
    }

    @PostMapping("add")
    @Log(logType = LogType.INSERT)
    public Response add(TfinanceBuyerReport entity, BindingResult result,
                        HttpServletRequest request, HttpServletResponse response) {
        // 验证错误
        try {
            this.checkError(entity, result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Response.ok("添加成功");
    }

    @PostMapping("update")
    @Log(logType = LogType.UPDATE)
    @RequiresMethodPermissions("update")
    public Response update(@RequestBody TfinanceBuyerReport entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        try {
            TfinanceBuyerReport tb = tfinanceBuyerReportService.selectById(entity.getId());
            tfinanceBuyerReportService.insertOrUpdate(tb);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Response.ok("更新成功");
    }

    @PostMapping("{id}/delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response delete(@PathVariable("id") String id) {
        TfinanceBuyerReport entity = tfinanceBuyerReportService.selectById(id);
        return Response.ok("删除成功");
    }

    @PostMapping(value = "/updatestatus")
    @Log(logType = LogType.UPDATE)
    @RequiresMethodPermissions("updatestatus")
    public Response updatestatus(TfinanceBuyerReport entity, HttpServletRequest request,
                               HttpServletResponse response) {
        TfinanceBuyerReport tbr = tfinanceBuyerReportService.selectById(entity.getId());
        tbr.setStatus("1");
        tfinanceBuyerReportService.updateById(tbr);
        return Response.ok("对账成功");
    }

    @PostMapping("batch/delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response batchDelete(@RequestParam("ids") String[] ids) {
        List<String> idList = Arrays.asList(ids);
        return Response.ok("删除成功");
    }


    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "ajaxList", method = { RequestMethod.GET, RequestMethod.POST })
    @PageableDefaults(sort = "create_date=desc")
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("view")
    public void ajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        EntityWrapper<TfinanceBuyerReport> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        if(queryable.getCondition()!=null) {
            Condition.Filter filter = queryable.getCondition().getFilterFor("countcreatedate");
            if (filter != null) {
                queryable.getCondition().remove(filter);
                queryable.getCondition().and(Condition.Operator.between, "countcreatedate", TaskUtils.whereDate(filter));
            }
            Condition.Filter filter_buyeridName = queryable.getCondition().getFilterFor("buyeridName");
            if (filter_buyeridName != null) {
                queryable.getCondition().remove(filter_buyeridName);
                entityWrapper.like("b.buyername", filter_buyeridName.getValue().toString());
            }
            Condition.Filter filter_loginName = queryable.getCondition().getFilterFor("loginName");
            if (filter_loginName != null) {
                queryable.getCondition().remove(filter_loginName);
                entityWrapper.like("b.loginname", filter_loginName.getValue().toString());
            }
        }
        if(queryable.getCondition()==null||queryable.getCondition().getFilterFor("countcreatedate")==null){
            Date date = DateUtils.dateAddDay(null,-1);
            String[] creates = TaskUtils.whereNewDate(DateUtils.formatDate(date,"yyyy-MM-dd"),DateUtils.formatDate(date,"yyyy-MM-dd"));
            entityWrapper.between("countcreatedate",creates[0],creates[1]);
        }
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<TfinanceBuyerReport> pagejson = new PageResponse<>(tfinanceBuyerReportService.list(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }

    @RequestMapping(value = "showBuyerReportLoad", method = { RequestMethod.GET, RequestMethod.POST })
    public void showBuyerReportLoad(@RequestBody JSONObject jsonObject, HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        Map map = new HashMap();
        try {
            String userid = "";
            if (!"admin".equals(UserUtils.getUser().getUsername())&&!UserUtils.getRoleStringList().contains("finance")) {
                userid = UserUtils.getPrincipal().getId();
            }
            String create1 = jsonObject.getString("create1");
            String create2 = jsonObject.getString("create2");
            String buyeridName = jsonObject.getString("buyeridName");
            String loginName = jsonObject.getString("loginName");
            String status = jsonObject.getString("status");
            if(StringUtils.isEmpty(create1)||StringUtils.isEmpty(create2)){
                Date date = DateUtils.dateAddDay(null,-1);
                create1 = DateUtils.formatDate(date,"yyyy-MM-dd");
                create2 = DateUtils.formatDate(date,"yyyy-MM-dd");
            }

            String[] creates = TaskUtils.whereNewDate(create1,create2);

            Map m = new HashMap();
            m.put("userid",userid);
            m.put("create1",creates[0]);
            m.put("create2",creates[1]);
            m.put("buyeridName",(StringUtils.isNotEmpty(buyeridName)?"%"+buyeridName+"%":null));
            m.put("loginName",(StringUtils.isNotEmpty(loginName)?"%"+loginName+"%":null));
            m.put("status",status);
            map = tfinanceBuyerReportService.showBuyerReportLoad(m);
        }catch (Exception e){
            e.printStackTrace();
        }
        String content = JSON.toJSONString(map);
        StringUtils.printJson(response,content);
    }


    @RequestMapping("export")
    @RequiresMethodPermissions("export")
    private void export(ModelMap map, Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                        HttpServletResponse response) throws IOException {

        EntityWrapper<TfinanceBuyerReport> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, TfinanceBuyerReport.class);
        List<TfinanceBuyerReport> userList = tfinanceBuyerReportService.listWithNoPage(queryable,entityWrapper);
        String title = "财务报表";
        ExportParams params = new ExportParams(title, title, ExcelType.XSSF);
        map.put(NormalExcelConstants.DATA_LIST, userList);
        map.put(NormalExcelConstants.CLASS, TfinanceBuyerReport.class);
        map.put(NormalExcelConstants.PARAMS, params);
        map.put("fileName",title+ "-" + DateUtils.getDateTime());
        PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
    }
}