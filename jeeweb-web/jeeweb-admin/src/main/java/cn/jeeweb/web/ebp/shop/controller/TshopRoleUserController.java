package cn.jeeweb.web.ebp.shop.controller;

import cn.jeeweb.beetl.tags.dict.DictUtils;
import cn.jeeweb.common.http.DuplicateValid;
import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.http.ValidResponse;
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
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.ebp.shop.entity.TshopBase;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.entity.TshopRoleUser;
import cn.jeeweb.web.ebp.shop.entity.TsoldInfo;
import cn.jeeweb.web.ebp.shop.service.TshopBaseService;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import cn.jeeweb.web.ebp.shop.service.TshopRoleUserService;
import cn.jeeweb.web.ebp.shop.service.TsoldInfoService;
import cn.jeeweb.web.ebp.shop.util.TaskUtils;
import cn.jeeweb.web.modules.sys.entity.User;
import cn.jeeweb.web.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/shop/TshopRoleUser")
@ViewPrefix("ebp/shop")
@RequiresPathPermission("shop:TshopRoleUser")
@Log(title = "销售授权")
public class TshopRoleUserController extends BaseBeanController<TshopRoleUser> {

    @Autowired
    private TshopRoleUserService TshopRoleUserService;
    @Autowired
    private TshopInfoService tshopInfoService;
    @Autowired
    private TshopBaseService tshopBaseService;
    @Autowired
    private TsoldInfoService tsoldInfoService;

    @GetMapping
    @RequiresMethodPermissions("view")
    public ModelAndView TaskList(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("list_TshopRoleUser");
        return mav;
    }

    @GetMapping(value = "add")
    public ModelAndView add(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("data", new TshopRoleUser());
        List<TsoldInfo> lsitSold = tsoldInfoService.selectByMap(new HashMap<>());
        model.addAttribute("lsitSold", lsitSold);

        List<TshopInfo> lsitShop = tshopInfoService.selectByMap(new HashMap<>());
        model.addAttribute("lsitShop", lsitShop);

        List<TshopBase> lsitBase = tshopBaseService.selectByMap(new HashMap<>());
        model.addAttribute("lsitBase", lsitBase);

        return displayModelAndView ("edit_TshopRoleUser");
    }

    @GetMapping(value = "listSoldFinance")
    @RequiresMethodPermissions("listSoldFinance")
    public ModelAndView listSoldFinance(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("listSoldFinance");
        return mav;
    }
    @PostMapping("add")
    @Log(logType = LogType.INSERT)
    public Response add(TshopRoleUser entity, BindingResult result,
                        HttpServletRequest request, HttpServletResponse response) {
        // 验证错误
        this.checkError(entity,result);
        Map map = new HashMap();
        map.put("userid",entity.getUserid());
        map.put("shopid",entity.getShopid());
        List list = TshopRoleUserService.selectByMap(map);
        if(list!=null&&list.size()>0){
            return Response.error("不能重复关联商户！");
        }
        TshopRoleUserService.insert(entity);
        return Response.ok("添加成功");
    }

    @PostMapping("update")
    @Log(logType = LogType.UPDATE)
    @RequiresMethodPermissions("update")
    public Response update(@RequestBody TshopRoleUser entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        try {
            TshopRoleUser tb = TshopRoleUserService.selectById(entity.getId());
            TshopRoleUserService.insertOrUpdate(tb);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Response.ok("更新成功");
    }

    @PostMapping("delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response delete(@PathVariable("id") String id) {
        TshopRoleUserService.deleteById(id);
        return Response.ok("删除成功");
    }

    @PostMapping("batch/delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response batchDelete(@RequestParam("ids") String[] ids) {
        List<String> idList = java.util.Arrays.asList(ids);
        TshopRoleUserService.deleteBatchIds(idList);
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
    public void ajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        EntityWrapper<TshopRoleUser> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        entityWrapper.setTableAlias("t");
        String userid = UserUtils.getPrincipal().getId();
        if (!StringUtils.isEmpty(userid)) {
            entityWrapper.eq("create_by", userid);
        }
        if(queryable.getCondition()!=null) {
            Condition.Filter filter = queryable.getCondition().getFilterFor("createDate");
            if (filter != null) {
                queryable.getCondition().remove(filter);
                queryable.getCondition().and(Condition.Operator.between, "createDate", TaskUtils.whereDate(filter));
            }
            Condition.Filter Filter_username = queryable.getCondition().getFilterFor("username");
            if(Filter_username!=null){
                queryable.getCondition().remove(Filter_username);
                entityWrapper.like("s.soldlogin",Filter_username.getValue().toString());
            }
            Condition.Filter Filter_shopname = queryable.getCondition().getFilterFor("shopname");
            if(Filter_shopname!=null){
                queryable.getCondition().remove(Filter_shopname);
                entityWrapper.like("si.loginname",Filter_shopname.getValue().toString());
            }
        }
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<TshopRoleUser> pagejson = new PageResponse<>(TshopRoleUserService.list(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }
    @PostMapping(value = "validate")
    public ValidResponse validate(DuplicateValid duplicateValid, HttpServletRequest request) {
        ValidResponse validResponse = new ValidResponse();
        Boolean valid = Boolean.FALSE;
        try {
            EntityWrapper<TshopRoleUser> entityWrapper = new EntityWrapper<TshopRoleUser>(entityClass);
            valid = TshopRoleUserService.doValid(duplicateValid,entityWrapper);
            if (valid) {
                validResponse.setStatus("y");
                validResponse.setInfo("验证通过!");
            } else {
                validResponse.setStatus("n");
                if (!StringUtils.isEmpty(duplicateValid.getErrorMsg())) {
                    validResponse.setInfo(duplicateValid.getErrorMsg());
                } else {
                    validResponse.setInfo("当前信息重复!");
                }
            }
        } catch (Exception e) {
            validResponse.setStatus("n");
            validResponse.setInfo("验证异常，请检查字段是否正确!");
        }
        return validResponse;
    }


    @RequestMapping(value = "listSoldFinanceajaxList", method = { RequestMethod.GET, RequestMethod.POST })
    public void listSoldFinanceajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        EntityWrapper<TshopRoleUser> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        Double multiple = 1.0;
        try{
            multiple = Double.parseDouble(DictUtils.getDictValue("一个任务单提成","tasknum",multiple+""));
        }catch (Exception e){

        }
        String userid = UserUtils.getPrincipal().getId();
        String loginname = "";
        String shopname = "";
        String[] creates = TaskUtils.whereNewDate("","");
        if(queryable.getCondition()!=null){
            Condition.Filter filter = queryable.getCondition().getFilterFor("effectdate");
            creates = TaskUtils.whereDate(filter);

            Condition.Filter filter_loginname = queryable.getCondition().getFilterFor("loginname");
            if(filter_loginname!=null){
                loginname = "%"+(String)filter_loginname.getValue()+"%";
            }

            Condition.Filter filter_shopname = queryable.getCondition().getFilterFor("shopname");
            if(filter_shopname!=null){
                shopname = "%"+(String)filter_shopname.getValue()+"%";
            }

        }
        Map paramMap = new HashMap();
        paramMap.put("userid",userid);
        paramMap.put("loginname",loginname);
        paramMap.put("shopname",shopname);
        paramMap.put("multiple",multiple);
        paramMap.put("createDate1",creates[0]);
        paramMap.put("createDate2",creates[1]);
        List<Map> list =TshopRoleUserService.listSoldFinance(paramMap);
        Long tasknum = 0l;
        Long unanswerednum = 0l;
        Long canreceivenum = 0l;
        Long finishnum = 0l;
        Double unansweredPrice = 0.0;
        Double finishPrice = 0.0;
        for (Map map:list) {
            if(map.get("tasknum")!=null){
                tasknum += Long.parseLong(map.get("tasknum").toString());
            }
            if(map.get("unanswerednum")!=null){
                unanswerednum += Long.parseLong(map.get("unanswerednum").toString());
            }
            if(map.get("canreceivenum")!=null){
                canreceivenum += Long.parseLong(map.get("canreceivenum").toString());
            }
            if(map.get("finishnum")!=null){
                finishnum += Long.parseLong(map.get("finishnum").toString());
            }
            if(map.get("unansweredPrice")!=null){
                unansweredPrice += Double.parseDouble(map.get("unansweredPrice").toString());
            }
            if(map.get("finishPrice")!=null){
                finishPrice += Double.parseDouble(map.get("finishPrice").toString());
            }
        }
        Map map = new HashMap();
        map.put("effectdate","");
        map.put("loginname","合计");
        map.put("tasknum",tasknum);
        map.put("unanswerednum",unanswerednum);
        map.put("canreceivenum",canreceivenum);
        map.put("finishnum",finishnum);
        map.put("unansweredPrice",unansweredPrice);
        map.put("finishPrice",finishPrice);
        list.add(map);
        String content = JSON.toJSONString(list);
        StringUtils.printJson(response,content);
    }
        @RequestMapping(value = "sumListSoldFinance", method = { RequestMethod.GET, RequestMethod.POST })
        public void sumListSoldFinance(@RequestBody JSONObject jsonObject, HttpServletRequest request,
                HttpServletResponse response) throws IOException {
        Double multiple = 1.0;
        try{
            multiple = Double.parseDouble(DictUtils.getDictValue("一个任务单提成","tasknum",multiple+""));
        }catch (Exception e){

        }
        String userid = UserUtils.getPrincipal().getId();
        String loginname = jsonObject.getString("loginname");
        Map paramMap = new HashMap();
        paramMap.put("userid",userid);
        paramMap.put("multiple",multiple);
        paramMap.put("loginname",(StringUtils.isNotEmpty(loginname)?"%"+loginname+"%":null));
        Map map =TshopRoleUserService.sumListSoldFinance(paramMap);
        map.put("multiple",multiple);
        String content = JSON.toJSONString(map);
        StringUtils.printJson(response,content);
    }
}