package cn.jeeweb.web.ebp.shop.controller;

import cn.jeeweb.common.http.DuplicateValid;
import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.http.ValidResponse;
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
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.entity.TuserKey;
import cn.jeeweb.web.ebp.shop.service.TshopBaseService;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import cn.jeeweb.web.ebp.shop.service.TuserKeyService;
import cn.jeeweb.web.ebp.shop.service.TsoldInfoService;
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
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/shop/TuserKey")
@ViewPrefix("ebp/shop")
@RequiresPathPermission("shop:TuserKey")
@Log(title = "商户控制")
public class TuserKeyController extends BaseBeanController<TuserKey> {

    @Autowired
    private TuserKeyService TuserKeyService;
    @Autowired
    private TshopInfoService tshopInfoService;
    @Autowired
    private TshopBaseService tshopBaseService;
    @Autowired
    private TsoldInfoService tsoldInfoService;

    @GetMapping
    @RequiresMethodPermissions("view")
    public ModelAndView TaskList(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("list_TuserKey");
        return mav;
    }

    @GetMapping(value = "add")
    public ModelAndView add(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("data", new TuserKey());

        List<TshopInfo> lsitShop = tshopInfoService.selectByMap(new HashMap<>());
        model.addAttribute("lsitShop", lsitShop);

        return displayModelAndView ("edit_TuserKey");
    }

    @GetMapping(value = "listSoldFinance")
    @RequiresMethodPermissions("listSoldFinance")
    public ModelAndView listSoldFinance(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("listSoldFinance");
        return mav;
    }
    @PostMapping("add")
    @Log(logType = LogType.INSERT)
    public Response add(TuserKey entity, BindingResult result,
                        HttpServletRequest request, HttpServletResponse response) {
        // 验证错误
        this.checkError(entity,result);
        TuserKeyService.insert(entity);
        return Response.ok("添加成功");
    }

    @GetMapping(value = "{id}/update")
    public ModelAndView update(@PathVariable("id") String id, Model model, HttpServletRequest request,
                               HttpServletResponse response) {
        TuserKey tuserKey = TuserKeyService.selectById(id);
        List<TshopInfo> lsitShop = tshopInfoService.selectByMap(new HashMap<>());
        model.addAttribute("lsitShop", lsitShop);

        model.addAttribute("data", tuserKey);
        return displayModelAndView ("edit_TuserKey");
    }

    @PostMapping("{id}/update")
    @Log(logType = LogType.UPDATE)
    public Response update(TuserKey entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        try {
            TuserKey tb = TuserKeyService.selectById(entity.getId());
            tb.setUserkey(entity.getUserkey());
            tb.setUservalue(entity.getUservalue());
            TuserKeyService.insertOrUpdate(tb);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Response.ok("更新成功");
    }



    @PostMapping("delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response delete(@PathVariable("id") String id) {
        TuserKeyService.deleteById(id);
        return Response.ok("删除成功");
    }

    @PostMapping("batch/delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response batchDelete(@RequestParam("ids") String[] ids) {
        List<String> idList = java.util.Arrays.asList(ids);
        TuserKeyService.deleteBatchIds(idList);
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
    public void ajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        EntityWrapper<TuserKey> entityWrapper = new EntityWrapper<>(entityClass);
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
            Condition.Filter Filter_shopname = queryable.getCondition().getFilterFor("shopname");
            if(Filter_shopname!=null){
                queryable.getCondition().remove(Filter_shopname);
                entityWrapper.like("si.loginname",Filter_shopname.getValue().toString());
            }
        }
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<TuserKey> pagejson = new PageResponse<>(TuserKeyService.list(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }
    @PostMapping(value = "validate")
    public ValidResponse validate(DuplicateValid duplicateValid, HttpServletRequest request) {
        ValidResponse validResponse = new ValidResponse();
        Boolean valid = Boolean.FALSE;
        try {
            EntityWrapper<TuserKey> entityWrapper = new EntityWrapper<TuserKey>(entityClass);
            valid = TuserKeyService.doValid(duplicateValid,entityWrapper);
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
}