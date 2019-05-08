package cn.jeeweb.web.ebp.buyer.controller;

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
import cn.jeeweb.common.query.data.PageImpl;
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
import cn.jeeweb.web.ebp.buyer.entity.TmyTask;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetailExport;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetailExportGroup;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import cn.jeeweb.web.ebp.finance.entity.TfinanceBuyerReport;
import cn.jeeweb.web.ebp.finance.service.TfinanceBuyerReportService;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import cn.jeeweb.web.ebp.shop.util.TaskUtils;
import cn.jeeweb.web.modules.sys.entity.LoginLog;
import cn.jeeweb.web.modules.sys.entity.User;
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
@RequestMapping("${jeeweb.admin.url.prefix}/buyer/TmyTaskDetail")
@ViewPrefix("ebp/buyer")
@RequiresPathPermission("buyer:TmyTaskDetail")
@Log(title = "抢单详情")
public class TmyTaskDetailController extends BaseBeanController<TmyTaskDetail> {

    @Autowired
    private TmyTaskDetailService tmyTaskDetailService;
    @Autowired
    private TfinanceBuyerReportService ttService;

    @GetMapping
    @RequiresMethodPermissions("view")
    public ModelAndView list(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("list");
        return mav;
    }
    @GetMapping(value = "{id}/buyerDetail")
    @RequiresMethodPermissions("buyerDetail")
    public ModelAndView buyerDetail(@PathVariable("id") String id,Model model, HttpServletRequest request, HttpServletResponse response) {
        boolean bool = false;
        if(id.indexOf("_")>=0){
            if (!"admin".equals(UserUtils.getUser().getUsername())&&!UserUtils.getRoleStringList().contains("finance")) {
                bool = true;
            }
        }
        model.addAttribute("showHidden",bool);
        model.addAttribute("id",id);
        ModelAndView mav = displayModelAndView("list_buyer_detail");
        return mav;
    }

    @GetMapping(value = "buyerDetailGroup")
    @RequiresMethodPermissions("buyerDetailGroup")
    public ModelAndView buyerDetailGroup(Model model, HttpServletRequest request, HttpServletResponse response) {
        boolean bool = false;
        if (!"admin".equals(UserUtils.getUser().getUsername())&&!UserUtils.getRoleStringList().contains("finance")) {
            bool = true;
        }
        model.addAttribute("showHidden",bool);
        ModelAndView mav = displayModelAndView("list_buyer_detailgroup");
        return mav;
    }

    @GetMapping(value = "shopbasehtml")
    @RequiresMethodPermissions("shopbasehtml")
    public ModelAndView shopbasehtml(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("list_shop_base");
        return mav;
    }

    @PostMapping("add")
    @Log(logType = LogType.INSERT)
    @RequiresMethodPermissions("add")
    public Response add(@RequestBody TmyTaskDetail tmyTaskDetail,HttpServletRequest request, HttpServletResponse response) {
        try {
            tmyTaskDetailService.insert(tmyTaskDetail);
        }catch (Exception e){
            e.printStackTrace();
        }
        //保存之后
        return Response.ok("添加成功");
    }

    @PostMapping("update")
    @Log(logType = LogType.UPDATE)
    @RequiresMethodPermissions("update")
    public Response update(@RequestBody TmyTaskDetail entity, BindingResult result,
                        HttpServletRequest request, HttpServletResponse response) {
        try {
            TmyTaskDetail td = tmyTaskDetailService.selectById(entity.getId());
            tmyTaskDetailService.insertOrUpdate(td);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Response.ok("更新成功");
    }

    @PostMapping("delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response delete(@PathVariable("id") String id) {
        tmyTaskDetailService.deleteById(id);
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
    @PageableDefaults(sort = "id=desc")
    @Log(logType = LogType.SELECT)
    @RequiresMethodPermissions("view")
    public void ajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        EntityWrapper<TmyTaskDetail> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        String userid = UserUtils.getPrincipal().getId();
        if (!StringUtils.isEmpty(userid)) {
            entityWrapper.eq("create_by", userid);
        }
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<TmyTaskDetail> pagejson = new PageResponse<TmyTaskDetail>(tmyTaskDetailService.list(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }

    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "{id}/ajaxListDetail", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresMethodPermissions("ajaxListDetail")
    public void ajaxListDetail(@PathVariable("id") String id,Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        TfinanceBuyerReport fbr = ttService.selectById(id);
        EntityWrapper<TmyTaskDetail> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        String[] creates = new String[2];
        if(fbr==null){
            String[] ids = id.split("_");
            creates = TaskUtils.whereNewDate(ids[1],ids[1]);
            entityWrapper.eq("sb.id", ids[0]);
        }else {
            creates = TaskUtils.whereNewDate(DateUtils.formatDate(fbr.getCountcreatedate(),"yyyy-MM-dd"),DateUtils.formatDate(fbr.getCountcreatedate(),"yyyy-MM-dd"));
            entityWrapper.eq("t.buyerid", fbr.getBuyerid());
        }

        entityWrapper.between("t.create_date",creates[0],creates[1]);
        entityWrapper.setTableAlias("t");
        // 预处理
        if(queryable.getCondition()!=null) {
            Condition.Filter filter_shopLoginname = queryable.getCondition().getFilterFor("shopLoginname");
            if (filter_shopLoginname != null) {
                queryable.getCondition().remove(filter_shopLoginname);
                entityWrapper.like("s.loginname", filter_shopLoginname.getValue().toString());
            }

            Condition.Filter filter_shopidName = queryable.getCondition().getFilterFor("shopidName");
            if (filter_shopidName != null) {
                queryable.getCondition().remove(filter_shopidName);
                entityWrapper.like("s.shopname", filter_shopidName.getValue().toString());
            }
            Condition.Filter filter_shopname = queryable.getCondition().getFilterFor("shopname");
            if (filter_shopname != null) {
                queryable.getCondition().remove(filter_shopname);
                entityWrapper.like("sb.shopname", filter_shopname.getValue().toString());
            }
            Condition.Filter filter_article = queryable.getCondition().getFilterFor("article");
            if (filter_article != null) {
                queryable.getCondition().remove(filter_article);
                entityWrapper.like("tb.article", filter_article.getValue().toString());
            }
        }

        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<TmyTaskDetail> pagejson = new PageResponse<TmyTaskDetail>(tmyTaskDetailService.listDetail(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }

    /**
     * 根据页码和每页记录数，以及查询条件动态加载数据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "ajaxListDetailGroup", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresMethodPermissions("ajaxListDetailGroup")
    public void ajaxListDetailGroup(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
        EntityWrapper<TmyTaskDetail> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        entityWrapper.setTableAlias("t");
        String[] creates = TaskUtils.whereNewDate("","");
        if(queryable.getCondition()!=null){
            Condition.Filter filter = queryable.getCondition().getFilterFor("receivingdates");
            if (filter != null) {
                queryable.getCondition().remove(filter);
            }
            creates = TaskUtils.whereDate(filter);
        }

        entityWrapper.between("t.receivingdate",creates[0],creates[1]);
        // 预处理
        if(queryable.getCondition()!=null) {
            Condition.Filter filter_shopLoginname = queryable.getCondition().getFilterFor("shopLoginname");
            if (filter_shopLoginname != null) {
                queryable.getCondition().remove(filter_shopLoginname);
                entityWrapper.like("s.loginname", filter_shopLoginname.getValue().toString());
            }
            Condition.Filter filter_shopidName = queryable.getCondition().getFilterFor("shopidName");
            if (filter_shopidName != null) {
                queryable.getCondition().remove(filter_shopidName);
                entityWrapper.like("s.shopname", filter_shopidName.getValue().toString());
            }

            Condition.Filter filter_buyeridName = queryable.getCondition().getFilterFor("buyeridName");
            if (filter_buyeridName != null) {
                queryable.getCondition().remove(filter_buyeridName);
                entityWrapper.like("bi.buyerName", filter_buyeridName.getValue().toString());
            }
            Condition.Filter filter_buyeridLogin = queryable.getCondition().getFilterFor("buyeridLogin");
            if (filter_buyeridLogin != null) {
                queryable.getCondition().remove(filter_buyeridLogin);
                entityWrapper.like("bi.loginname", filter_buyeridLogin.getValue().toString());
            }

            Condition.Filter filter_shopname = queryable.getCondition().getFilterFor("shopname");
            if (filter_shopname != null) {
                queryable.getCondition().remove(filter_shopname);
                entityWrapper.like("sb.shopname", filter_shopname.getValue().toString());
            }
            Condition.Filter filter_article = queryable.getCondition().getFilterFor("article");
            if (filter_article != null) {
                queryable.getCondition().remove(filter_article);
                entityWrapper.like("tb.article", filter_article.getValue().toString());
            }
        }
        entityWrapper.groupBy("buyeridName").groupBy("buyeridLogin").groupBy("receivingdates")
        .groupBy("orderdates").groupBy("shopLoginname").groupBy("shopidName").groupBy("shopname")
        .groupBy("buyerjdnick").groupBy("jdorderno").groupBy("taskstate");
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<TmyTaskDetail> pagejson = new PageResponse<TmyTaskDetail>(tmyTaskDetailService.listDetailGroup(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }


    @RequestMapping(value = "listShopBaseDetail", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresMethodPermissions("listShopBaseDetail")
    public void listShopBaseDetail(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
        EntityWrapper<TmyTaskDetail> entityWrapper = new EntityWrapper<>(entityClass);
        String[] s = {"id","tUrl"};
        propertyPreFilterable.addQueryProperty(s);
        entityWrapper.setTableAlias("t");
        // 预处理
        if(queryable.getCondition()!=null) {
            Condition.Filter filter_receivingdate = queryable.getCondition().getFilterFor("receivingdate");
            if (filter_receivingdate != null) {
                queryable.getCondition().remove(filter_receivingdate);
                queryable.getCondition().and(Condition.Operator.between, "receivingdate", TaskUtils.whereDate(filter_receivingdate));
            }

            Condition.Filter filter_shopLoginname = queryable.getCondition().getFilterFor("shopLoginname");
            if (filter_shopLoginname != null) {
                queryable.getCondition().remove(filter_shopLoginname);
                entityWrapper.like("s.loginname", filter_shopLoginname.getValue().toString());
            }

            Condition.Filter filter_shopidName = queryable.getCondition().getFilterFor("shopidName");
            if (filter_shopidName != null) {
                queryable.getCondition().remove(filter_shopidName);
                entityWrapper.like("s.shopname", filter_shopidName.getValue().toString());
            }
            Condition.Filter filter_shopname = queryable.getCondition().getFilterFor("shopname");
            if (filter_shopname != null) {
                queryable.getCondition().remove(filter_shopname);
                entityWrapper.like("sb.shopname", filter_shopname.getValue().toString());
            }
            Condition.Filter filter_article = queryable.getCondition().getFilterFor("article");
            if (filter_article != null) {
                queryable.getCondition().remove(filter_article);
                entityWrapper.like("tb.article", filter_article.getValue().toString());
            }
            QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
            SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
            PageResponse<TmyTaskDetail> pagejson = new PageResponse<TmyTaskDetail>(tmyTaskDetailService.listShopBaseDetail(queryable,entityWrapper));
            String content = JSON.toJSONString(pagejson, filter);
            StringUtils.printJson(response,content);
        }else {
            SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
//            new PageImpl<TmyTaskDetail>(new ArrayList<>(), queryable.getPageable(), 0);
            PageResponse<TmyTaskDetail> pagejson = new PageResponse<TmyTaskDetail>();
            String content = JSON.toJSONString(pagejson, filter);
            StringUtils.printJson(response,content);
        }
//        if(queryable.getCondition()==null||queryable.getCondition().getFilterFor("receivingdate")==null) {
//            String[] creates = TaskUtils.whereNewDate("", "");
//            entityWrapper.between("receivingdate", creates[0], creates[1]);
//        }

    }


    @GetMapping("{id}/{buyerjdnick}/upbuyerjdnick")
    @Log(logType = LogType.UPDATE)
    public void upbuyerjdnick(@PathVariable("id") String id,@PathVariable("buyerjdnick") String buyerjdnick, HttpServletRequest request,
                            HttpServletResponse response) {
        TmyTaskDetail td = tmyTaskDetailService.selectById(id);
//        Map map = new HashMap();
//        map.put("storename",StringUtils.isEmpty(td.getStorename())?"NA":td.getStorename());
//        map.put("buyerid",StringUtils.isEmpty(td.getBuyerid())?"NA":td.getBuyerid());
//        map.put("mytaskid",StringUtils.isEmpty(td.getMytaskid())?"NA":td.getMytaskid());
//        List<TmyTaskDetail> list = tmyTaskDetailService.selectByMap(map);
//        if(list!=null&&!list.isEmpty()){
//            for (TmyTaskDetail ttd:list) {
//                ttd.setBuyerjdnick(buyerjdnick);
//            }
//            tmyTaskDetailService.updateBatchById(list);
//        }
        td.setBuyerjdnick(buyerjdnick);
        tmyTaskDetailService.updateById(td);

    }
    @GetMapping("{id}/{jdorderno}/upjdorderno")
    @Log(logType = LogType.UPDATE)
    public void upjdorderno(@PathVariable("id") String id,@PathVariable("jdorderno") String jdorderno, HttpServletRequest request,
                              HttpServletResponse response) {
        TmyTaskDetail td = tmyTaskDetailService.selectById(id);
//        Map map = new HashMap();
//        map.put("storename",StringUtils.isEmpty(td.getStorename())?"NA":td.getStorename());
//        map.put("buyerid",StringUtils.isEmpty(td.getBuyerid())?"NA":td.getBuyerid());
//        map.put("mytaskid",StringUtils.isEmpty(td.getMytaskid())?"NA":td.getMytaskid());
//        List<TmyTaskDetail> list = tmyTaskDetailService.selectByMap(map);
//        if(list!=null&&!list.isEmpty()){
//            for (TmyTaskDetail ttd:list) {
//                ttd.setJdorderno(jdorderno);
//            }
//            tmyTaskDetailService.updateBatchById(list);
//        }
        td.setJdorderno(jdorderno);
        tmyTaskDetailService.updateById(td);

    }

    @RequestMapping(value = "upTaskjdorderno", method = { RequestMethod.GET, RequestMethod.POST })
    @Log(logType = LogType.UPDATE)
    public Response upTaskjdorderno(@RequestBody JSONObject jsonObject, HttpServletRequest request,
                                HttpServletResponse response) {
        try {
            String id = jsonObject.getString("id");
            String jdorderno = jsonObject.getString("jdorderno");
            TmyTaskDetail td = tmyTaskDetailService.selectById(id);

            Map map = new HashMap();
            map.put("storename",StringUtils.isEmpty(td.getStorename())?"NA":td.getStorename());
            map.put("buyerid",StringUtils.isEmpty(td.getBuyerid())?"NA":td.getBuyerid());
            map.put("mytaskid",StringUtils.isEmpty(td.getMytaskid())?"NA":td.getMytaskid());
            List<TmyTaskDetail> list = tmyTaskDetailService.selectByMap(map);
            if(list!=null&&!list.isEmpty()){
                for (TmyTaskDetail ttd:list) {
                    ttd.setJdorderno(jdorderno);
                }
            tmyTaskDetailService.updateBatchById(list);
            }

        }catch (Exception e){
            e.printStackTrace();
            return Response.ok("修改失败！");
        }
        return Response.ok("修改成功！");
    }

    @RequestMapping(value = "upBankamount", method = { RequestMethod.GET, RequestMethod.POST })
    @Log(logType = LogType.UPDATE)
    public Response upBankamount(@RequestBody JSONObject jsonObject, HttpServletRequest request,
                             HttpServletResponse response) {
        try {
            String id = jsonObject.getString("id");
            String bankamount = jsonObject.getString("bankamount");
            TmyTaskDetail td = tmyTaskDetailService.selectById(id);
            td.setBankamount(new BigDecimal(bankamount));
            tmyTaskDetailService.insertOrUpdate(td);
        }catch (Exception e){
            e.printStackTrace();
            return Response.ok("修改失败！");
        }
        return Response.ok("修改成功！");

    }

    @GetMapping("{id}/{buyerjdnick}/upTaskDetail")
    @Log(logType = LogType.UPDATE)
    public void upTaskDetail(@PathVariable("id") String id,@PathVariable("buyerjdnick") String buyerjdnick, HttpServletRequest request,
                            HttpServletResponse response) {
        ///{jdorderno}@PathVariable("jdorderno") String jdorderno,
        Map map = new HashMap();
        map.put("mytaskid",id);
        List<TmyTaskDetail> list = tmyTaskDetailService.selectByMap(map);
        for (TmyTaskDetail ttd:list) {
            ttd.setBuyerjdnick(buyerjdnick);
//            ttd.setJdorderno(jdorderno);

        }
        tmyTaskDetailService.updateBatchById(list);
    }

    @RequestMapping("{id}/export")
    public void export(@PathVariable("id") String id,ModelMap map, Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                        HttpServletResponse response) throws IOException {
        TfinanceBuyerReport fbr = ttService.selectById(id);
        EntityWrapper<TmyTaskDetail> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        String[] creates = new String[2];
        if(fbr==null){
            String[] ids = id.split("_");
            creates = TaskUtils.whereNewDate(ids[1],ids[1]);
            entityWrapper.eq("sb.id", ids[0]);
        }else {
            creates = TaskUtils.whereNewDate(DateUtils.formatDate(fbr.getCountcreatedate(),"yyyy-MM-dd"),DateUtils.formatDate(fbr.getCountcreatedate(),"yyyy-MM-dd"));
            entityWrapper.eq("t.buyerid", fbr.getBuyerid());
        }

        entityWrapper.between("t.create_date",creates[0],creates[1]);
        // 预处理
        if(queryable.getCondition()!=null) {
            Condition.Filter filter_shopLoginname = queryable.getCondition().getFilterFor("shopLoginname");
            if (filter_shopLoginname != null) {
                queryable.getCondition().remove(filter_shopLoginname);
                entityWrapper.like("s.loginname", filter_shopLoginname.getValue().toString());
            }

            Condition.Filter filter_shopidName = queryable.getCondition().getFilterFor("shopidName");
            if (filter_shopidName != null) {
                queryable.getCondition().remove(filter_shopidName);
                entityWrapper.like("s.shopname", filter_shopidName.getValue().toString());
            }
            Condition.Filter filter_shopname = queryable.getCondition().getFilterFor("shopname");
            if (filter_shopname != null) {
                queryable.getCondition().remove(filter_shopname);
                entityWrapper.like("sb.shopname", filter_shopname.getValue().toString());
            }
            Condition.Filter filter_article = queryable.getCondition().getFilterFor("article");
            if (filter_article != null) {
                queryable.getCondition().remove(filter_article);
                entityWrapper.like("tb.article", filter_article.getValue().toString());
            }
        }



        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, TmyTaskDetail.class);
        List<TmyTaskDetail> userList = tmyTaskDetailService.listNoPageDetail(queryable,entityWrapper);
        String title = "买手财务报表详情";
        ExportParams params = new ExportParams(title, title, ExcelType.XSSF);
        if(fbr==null&&!"admin".equals(UserUtils.getUser().getUsername())&&!UserUtils.getRoleStringList().contains("finance")) {
            List<TmyTaskDetailExport> new_list = new ArrayList<TmyTaskDetailExport>();
            for (TmyTaskDetail ttd:userList) {
                TmyTaskDetailExport ttde = new TmyTaskDetailExport();
                try {
                    BeanUtils.copyProperties(ttd, ttde);
                }catch (Exception e){
                    e.printStackTrace();
                }

//                ttde.setTaskstate(ttd.getTaskstate());//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
//                ttde.setPays(ttd.getPays());//实付金额
//                ttde.setBuyerjdnick(ttd.getBuyerjdnick());//京东账号	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
//                ttde.setJdorderno(ttd.getJdorderno()); //京东订单号
//                ttde.setReceivingdate(ttd.getReceivingdate());//接受任务时间
//                ttde.setOrderdate(ttd.getOrderdate());//买手下单时间
//                ttde.setShopidName(ttd.getShopidName());
//                ttde.setShopLoginname(ttd.getShopLoginname());
//                ttde.setShopname(ttd.getShopname());
//                ttde.setArticle(ttd.getArticle());
                new_list.add(ttde);
            }
            map.put(NormalExcelConstants.DATA_LIST, new_list);
            map.put(NormalExcelConstants.CLASS, TmyTaskDetailExport.class);
        }else {
            map.put(NormalExcelConstants.DATA_LIST, userList);
            map.put(NormalExcelConstants.CLASS, TmyTaskDetail.class);
        }
        map.put(NormalExcelConstants.PARAMS, params);
        map.put("fileName",title+ "-" + DateUtils.getDateTime());
        PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
    }

    @RequestMapping("exportGroup")
    public void exportGroup(ModelMap map, Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        EntityWrapper<TmyTaskDetail> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        entityWrapper.setTableAlias("t");
        String[] creates = TaskUtils.whereNewDate("","");
        if(queryable.getCondition()!=null){
            Condition.Filter filter = queryable.getCondition().getFilterFor("receivingdates");
            if (filter != null) {
                queryable.getCondition().remove(filter);
            }
            creates = TaskUtils.whereDate(filter);
        }

        entityWrapper.between("t.receivingdate",creates[0],creates[1]);
        // 预处理
        if(queryable.getCondition()!=null) {
            Condition.Filter filter_shopLoginname = queryable.getCondition().getFilterFor("shopLoginname");
            if (filter_shopLoginname != null) {
                queryable.getCondition().remove(filter_shopLoginname);
                entityWrapper.like("s.loginname", filter_shopLoginname.getValue().toString());
            }

            Condition.Filter filter_shopidName = queryable.getCondition().getFilterFor("shopidName");
            if (filter_shopidName != null) {
                queryable.getCondition().remove(filter_shopidName);
                entityWrapper.like("s.shopname", filter_shopidName.getValue().toString());
            }
            Condition.Filter filter_shopname = queryable.getCondition().getFilterFor("shopname");
            if (filter_shopname != null) {
                queryable.getCondition().remove(filter_shopname);
                entityWrapper.like("sb.shopname", filter_shopname.getValue().toString());
            }
            Condition.Filter filter_article = queryable.getCondition().getFilterFor("article");
            if (filter_article != null) {
                queryable.getCondition().remove(filter_article);
                entityWrapper.like("tb.article", filter_article.getValue().toString());
            }
        }
        entityWrapper.groupBy("buyeridName").groupBy("buyeridLogin").groupBy("receivingdates")
                .groupBy("orderdates").groupBy("shopLoginname").groupBy("shopidName").groupBy("shopname")
                .groupBy("buyerjdnick").groupBy("jdorderno").groupBy("taskstate");

        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        List<TmyTaskDetail> userList = tmyTaskDetailService.listNoPageDetailGroup(queryable,entityWrapper);
        String title = "买手财务报表汇总";
        ExportParams params = new ExportParams(title, title, ExcelType.XSSF);
        List<TmyTaskDetailExportGroup> new_list = new ArrayList<TmyTaskDetailExportGroup>();
        for (TmyTaskDetail ttd:userList) {
            TmyTaskDetailExportGroup ttde = new TmyTaskDetailExportGroup();
            try {
                BeanUtils.copyProperties(ttd, ttde);
            }catch (Exception e){
                e.printStackTrace();
            }
            new_list.add(ttde);
        }
        map.put(NormalExcelConstants.DATA_LIST, new_list);
        map.put(NormalExcelConstants.CLASS, TmyTaskDetailExportGroup.class);
        map.put(NormalExcelConstants.PARAMS, params);
        map.put("fileName",title+ "-" + DateUtils.getDateTime());
        PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
    }
}