package cn.jeeweb.web.ebp.finance.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.view.PoiBaseView;
import cn.jeeweb.beetl.tags.dict.Dict;
import cn.jeeweb.beetl.tags.dict.DictUtils;
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
import cn.jeeweb.common.utils.BeanUtils;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetailExport;
import cn.jeeweb.web.ebp.finance.entity.TfinanceBuyerReport;
import cn.jeeweb.web.ebp.finance.entity.TfinanceRecharge;
import cn.jeeweb.web.ebp.finance.entity.TfinanceRechargeLog;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeLogService;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeService;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import cn.jeeweb.web.ebp.shop.util.TaskUtils;
import cn.jeeweb.web.utils.UserUtils;
import com.alibaba.fastjson.JSON;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/finance/TfinanceRechargeLog")
@ViewPrefix("ebp/finance")
@RequiresPathPermission("finance:TfinanceRechargeLog")
@Log(title = "交易日志管理")
public class TfinanceRechargeLogController extends BaseBeanController<TfinanceRechargeLog> {

    @Autowired
    private TfinanceRechargeLogService tfinanceRechargeLogService;

    @Autowired
    private TshopInfoService tshopInfoService;



    @GetMapping
    @RequiresMethodPermissions("view")
    public ModelAndView TaskList(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("list");
        return mav;
    }
    @GetMapping(value = "listShopFinance")
    @RequiresMethodPermissions("listShopFinance")
    public ModelAndView listShopFinance(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<Dict> dicList = DictUtils.getDictList("basestate");
        String content = JSON.toJSONString(dicList);
        List<TshopInfo> lsit = tshopInfoService.selectByMap(new HashMap<>());
        model.addAttribute("dicList", content);
        boolean bool = false;
        if (!"admin".equals(UserUtils.getUser().getUsername())&&!UserUtils.getRoleStringList().contains("finance")) {
            bool = true;
        }
        model.addAttribute("showHidden",bool);
        ModelAndView mav = displayModelAndView("listShopFinance");
        return mav;
    }

    @GetMapping(value = "add")
    public ModelAndView add(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("data", new TfinanceRecharge());
        return displayModelAndView ("edit");
    }

    @PostMapping("add")
    @Log(logType = LogType.INSERT)
    public Response add(TfinanceRechargeLog entity, BindingResult result,
                        HttpServletRequest request, HttpServletResponse response) {
        // 验证错误
        try {
            tfinanceRechargeLogService.insert(entity);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Response.ok("添加成功");
    }

    @PostMapping("update")
    @Log(logType = LogType.UPDATE)
    @RequiresMethodPermissions("update")
    public Response update(@RequestBody TfinanceRechargeLog entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        try {
            TfinanceRechargeLog tb = tfinanceRechargeLogService.selectById(entity.getId());
            tfinanceRechargeLogService.insertOrUpdate(tb);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Response.ok("更新成功");
    }

    @PostMapping("delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response delete(@PathVariable("id") String id) {

        tfinanceRechargeLogService.deleteById(id);
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
        EntityWrapper<TfinanceRechargeLog> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        String userid = UserUtils.getPrincipal().getId();
        if (!StringUtils.isEmpty(userid)&&!"admin".equals(UserUtils.getUser().getUsername())&&!UserUtils.getRoleStringList().contains("finance")) {
            entityWrapper.eq("t.shopid", userid);
        }
        entityWrapper.setTableAlias("t");
        if(queryable.getCondition()!=null) {
            Condition.Filter filter = queryable.getCondition().getFilterFor("createDate");
            if (filter != null) {
                queryable.getCondition().remove(filter);
                queryable.getCondition().and(Condition.Operator.between, "createDate", TaskUtils.whereDate(filter));
            }
            Condition.Filter Filter_loginname = queryable.getCondition().getFilterFor("loginname");
            if(Filter_loginname!=null){
                queryable.getCondition().remove(Filter_loginname);
                entityWrapper.like("s.loginname",Filter_loginname.getValue().toString());
            }

            Condition.Filter Filter_name = queryable.getCondition().getFilterFor("shopname");
            if(Filter_name!=null){
                queryable.getCondition().remove(Filter_name);
                entityWrapper.like("b.shopname",Filter_name.getValue().toString());
            }
            Condition.Filter fromInnerOuter = queryable.getCondition().getFilterFor("fromInnerOuter");
            if(fromInnerOuter!=null){
                queryable.getCondition().remove(fromInnerOuter);
                entityWrapper.eq("s.from_Inner_Outer",fromInnerOuter.getValue().toString());
            }
            Condition.Filter taskno = queryable.getCondition().getFilterFor("taskno");
            if(taskno!=null){
                queryable.getCondition().remove(taskno);
                entityWrapper.eq("tb.taskno",taskno.getValue().toString());
            }
        }else {
            if(queryable.getCondition()==null||queryable.getCondition().getFilterFor("createDate")==null) {
                Date date1 = DateUtils.dateAddDay(new Date(),-7);
                String[] creates = TaskUtils.whereNewDate(DateUtils.formatDate(date1), DateUtils.formatDate(new Date()));
                entityWrapper.between("t.create_date", creates[0], creates[1]);
            }
        }
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<TfinanceRechargeLog> pagejson = new PageResponse<>(tfinanceRechargeLogService.list(queryable,entityWrapper));
        List<TfinanceRechargeLog> new_lsit = new ArrayList<TfinanceRechargeLog>();
        for (TfinanceRechargeLog a:pagejson.getResults()) {
            if(TfinanceRechargeService.rechargetype_2.equals(a.getTradetype())||TfinanceRechargeService.rechargetype_3.equals(a.getTradetype())){
                a.setProducedepositPayName("-"+a.getProducedeposit());
            }else {
                a.setProducedepositIncomeName("+"+a.getProducedeposit());
            }
        }
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }




    @RequestMapping("export")
    @PageableDefaults(sort = "create_date=desc")
    public void export(ModelMap map, Queryable queryable,
                       PropertyPreFilterable propertyPreFilterable, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        EntityWrapper<TfinanceRechargeLog> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        String userid = UserUtils.getPrincipal().getId();
        if (!StringUtils.isEmpty(userid)&&!"admin".equals(UserUtils.getUser().getUsername())&&!UserUtils.getRoleStringList().contains("finance")) {
            entityWrapper.eq("t.shopid", userid);
        }
        entityWrapper.setTableAlias("t");
        if(queryable.getCondition()!=null) {
            Condition.Filter filter = queryable.getCondition().getFilterFor("createDate");
            if (filter != null) {
                queryable.getCondition().remove(filter);
                queryable.getCondition().and(Condition.Operator.between, "createDate", TaskUtils.whereDate(filter));
            }
            Condition.Filter Filter_loginname = queryable.getCondition().getFilterFor("loginname");
            if(Filter_loginname!=null){
                queryable.getCondition().remove(Filter_loginname);
                entityWrapper.like("s.loginname",Filter_loginname.getValue().toString());
            }

            Condition.Filter Filter_name = queryable.getCondition().getFilterFor("shopname");
            if(Filter_name!=null){
                queryable.getCondition().remove(Filter_name);
                entityWrapper.like("b.shopname",Filter_name.getValue().toString());
            }
            Condition.Filter fromInnerOuter = queryable.getCondition().getFilterFor("fromInnerOuter");
            if(fromInnerOuter!=null){
                queryable.getCondition().remove(fromInnerOuter);
                entityWrapper.eq("s.from_Inner_Outer",fromInnerOuter.getValue().toString());
            }
        }else {
            if(queryable.getCondition()==null||queryable.getCondition().getFilterFor("createDate")==null) {
                Date date1 = DateUtils.dateAddDay(new Date(),-7);
                String[] creates = TaskUtils.whereNewDate(DateUtils.formatDate(date1), DateUtils.formatDate(new Date()));
                entityWrapper.between("t.create_date", creates[0], creates[1]);
            }
        }
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        List<TfinanceRechargeLog> userList = tfinanceRechargeLogService.listAll(queryable,entityWrapper);
        for (TfinanceRechargeLog l:userList) {
            l.setFromInnerOuter(DictUtils.getDictLabel(l.getFromInnerOuter(), "shopSource", l.getFromInnerOuter()));
            if(TfinanceRechargeService.rechargetype_2.equals(l.getTradetype())||TfinanceRechargeService.rechargetype_3.equals(l.getTradetype())){
                l.setProducedepositPayName("-"+l.getProducedeposit());
            }else {
                l.setProducedepositIncomeName("+"+l.getProducedeposit());
            }
            l.setTradetype(DictUtils.getDictLabel(l.getTradetype(), "tradetype", l.getTradetype()));
        }
        String title = "商户余额信息";
        ExportParams params = new ExportParams(title, title, ExcelType.XSSF);
        map.put(NormalExcelConstants.DATA_LIST, userList);
        map.put(NormalExcelConstants.CLASS, TfinanceRechargeLog.class);
        map.put(NormalExcelConstants.PARAMS, params);
        map.put("fileName", title + "-" + DateUtils.getDateTime());
        PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
    }

}