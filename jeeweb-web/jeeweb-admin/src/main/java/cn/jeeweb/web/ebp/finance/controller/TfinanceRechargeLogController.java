package cn.jeeweb.web.ebp.finance.controller;

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
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
        if (!StringUtils.isEmpty(userid)&&!"admin".equals(UserUtils.getUser().getUsername())) {
            entityWrapper.eq("t.create_by", userid);
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
        }else {
            if(queryable.getCondition()==null||queryable.getCondition().getFilterFor("createDate")==null) {
                String[] creates = TaskUtils.whereNewDate("", "");
                entityWrapper.between("t.create_date", creates[0], creates[1]);
            }
        }
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<TfinanceRechargeLog> pagejson = new PageResponse<>(tfinanceRechargeLogService.list(queryable,entityWrapper));
        List<TfinanceRechargeLog> new_lsit = new ArrayList<TfinanceRechargeLog>();
        for (TfinanceRechargeLog a:pagejson.getResults()) {
            String s = "+";
            if(TfinanceRechargeService.rechargetype_2.equals(a.getTradetype())||TfinanceRechargeService.rechargetype_3.equals(a.getTradetype())){
                s="-";
            }
            a.setProducedepositName(s+a.getProducedeposit());
        }
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }


}