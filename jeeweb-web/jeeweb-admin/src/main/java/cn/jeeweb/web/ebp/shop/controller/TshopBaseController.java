package cn.jeeweb.web.ebp.shop.controller;

import cn.jeeweb.common.http.DuplicateValid;
import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.http.ValidResponse;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.query.utils.QueryableConvertUtils;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.ebp.shop.entity.TshopBase;
import cn.jeeweb.web.ebp.shop.service.TshopBaseService;
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
import java.util.List;


@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/shop/TshopBase")
@ViewPrefix("ebp/shop")
@RequiresPathPermission("shop:TshopBase")
@Log(title = "订单详情")
public class TshopBaseController extends BaseBeanController<TshopBase> {


    @Autowired
    private TshopBaseService tshopBaseService;


    @GetMapping
    @RequiresMethodPermissions("view")
    public ModelAndView list(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("list_shop");
        System.out.println(mav.getViewName());
        return mav;
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
        EntityWrapper<TshopBase> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        String userid = UserUtils.getPrincipal().getId();
        if (!StringUtils.isEmpty(userid)&&!"admin".equals(UserUtils.getUser().getUsername())) {
            entityWrapper.eq("userid", userid);
        }
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<TshopBase> pagejson = new PageResponse<>(tshopBaseService.list(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }


    @GetMapping(value = "add")
    public ModelAndView add(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("data", new TshopBase());
        // 查询所有的角色List
        return displayModelAndView ("edit_shop");
    }
    @PostMapping("add")
    @Log(logType = LogType.INSERT)
    public Response add(TshopBase entity, BindingResult result,
                        HttpServletRequest request, HttpServletResponse response) {
        // 验证错误
        this.checkError(entity,result);
        entity.setPlatform("jd");
        entity.setUserid(UserUtils.getPrincipal().getId());
        tshopBaseService.insert(entity);
        return Response.ok("添加成功");
    }
    @GetMapping(value = "{id}/update")
    public ModelAndView update(@PathVariable("id") String id, Model model, HttpServletRequest request,
                               HttpServletResponse response) {
        TshopBase shopbase = tshopBaseService.selectById(id);
        model.addAttribute("data", shopbase);
        return displayModelAndView ("edit_shop");
    }
    @PostMapping("{id}/update")
    @Log(logType = LogType.UPDATE)
    public Response update(TshopBase entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        // 验证错误
        this.checkError(entity,result);
        entity.setUserid(UserUtils.getPrincipal().getId());
        tshopBaseService.insertOrUpdate(entity);
        return Response.ok("更新成功");
    }

    @PostMapping("{id}/delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response delete(@PathVariable("id") String id) {
        tshopBaseService.deleteById(id);
        return Response.ok("删除成功");
    }
    /*
    批量删除
    */
    @PostMapping("batch/delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response batchDelete(@RequestParam("ids") String[] ids) {
        List<String> idList = java.util.Arrays.asList(ids);
        tshopBaseService.deleteBatchIds(idList);
        return Response.ok("删除成功");
    }


    @PostMapping(value = "validate")
    public ValidResponse validate(DuplicateValid duplicateValid, HttpServletRequest request) {
        ValidResponse validResponse = new ValidResponse();
        Boolean valid = Boolean.FALSE;
        try {
            EntityWrapper<TshopBase> entityWrapper = new EntityWrapper<TshopBase>(entityClass);
            valid = tshopBaseService.doValid(duplicateValid,entityWrapper);
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

    @RequestMapping(value = "selMyShowBaseList", method = { RequestMethod.GET, RequestMethod.POST })
    @PageableDefaults(sort = "id=desc")
    public void selMyShowBaseList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {
        EntityWrapper<TshopBase> entityWrapper = new EntityWrapper<>(entityClass);
        entityWrapper.eq("userid", UserUtils.getPrincipal().getId());

        List<TshopBase> list = tshopBaseService.listWithNoPage(queryable,entityWrapper);
//        List<TmyTaskDetail> newlist = new ArrayList<TmyTaskDetail>();
//        for (TmyTaskDetail t :list){
//            t.setTasktype(DictUtils.getDictLabel(t.getTasktype(),"tasktype",t.getTasktype()));
//            t.setTaskstateName(DictUtils.getDictLabel(t.getTaskstate(),"taskstate",t.getTaskstate()));
//            newlist.add(t);
//        }
        String content = JSON.toJSONString(list);
        StringUtils.printJson(response,content);
    }


}