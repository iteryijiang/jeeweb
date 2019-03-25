package cn.jeeweb.web.ebp.finance.controller;

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
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.ebp.finance.entity.TfinanceRecharge;
import cn.jeeweb.web.ebp.finance.service.TfinanceRechargeService;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import cn.jeeweb.web.ebp.shop.spider.TsequenceSpider;
import cn.jeeweb.web.ebp.shop.util.TaskUtils;
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
import java.util.*;


@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/finance/TfinanceRecharge")
@ViewPrefix("ebp/finance")
@RequiresPathPermission("finance:TfinanceRecharge")
@Log(title = "抢单管理")
public class TfinanceRechargeController extends BaseBeanController<TfinanceRecharge> {

    @Autowired
    private TfinanceRechargeService tfinanceRechargeService;

    @Autowired
    private TshopInfoService tshopInfoService;


    @GetMapping
    @RequiresMethodPermissions("view")
    public ModelAndView TaskList(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("list");
        return mav;
    }

    @GetMapping(value = "add")
    public ModelAndView add(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("data", new TfinanceRecharge());

        List<TshopInfo> lsit = tshopInfoService.selectByMap(new HashMap<>());
        model.addAttribute("booksQueryList", lsit);

        return displayModelAndView ("edit");
    }

    @PostMapping("add")
    @Log(logType = LogType.INSERT)
    public Response add(TfinanceRecharge entity, BindingResult result,
                        HttpServletRequest request, HttpServletResponse response) {
        // 验证错误
        try {
            this.checkError(entity, result);
            TshopInfo si = tshopInfoService.selectOne(entity.getShopid());
            entity.setRechargeno(TsequenceSpider.getRechargeNo());
            if (si.getTotaldeposit() == null) {
                si.setTotaldeposit(entity.getRechargedeposit());
            } else {
                si.setTotaldeposit(si.getTotaldeposit().add(entity.getRechargedeposit()));
            }
            entity.setTotaldeposit(si.getTotaldeposit());
            tfinanceRechargeService.addTfinanceRecharge(si,entity);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Response.ok("添加成功");
    }

    @PostMapping("update")
    @Log(logType = LogType.UPDATE)
    @RequiresMethodPermissions("update")
    public Response update(@RequestBody TfinanceRecharge entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        try {
            TfinanceRecharge tb = tfinanceRechargeService.selectById(entity.getId());
            tfinanceRechargeService.insertOrUpdate(tb);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Response.ok("更新成功");
    }

    @PostMapping("{id}/delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response delete(@PathVariable("id") String id) {

        TfinanceRecharge entity = tfinanceRechargeService.selectById(id);
        TshopInfo si = tshopInfoService.selectOne(entity.getShopid());
        if (si.getTotaldeposit() == null) {
            return Response.error("删除失败，该商户无充值金额！");
        } else {
            si.setTotaldeposit(si.getTotaldeposit().subtract(entity.getRechargedeposit()));
        }
        if(si.getTotaldeposit().compareTo(BigDecimal.ZERO)<0){
            return Response.error("删除失败，该商金额不够！");
        }

        entity.setTotaldeposit(si.getTotaldeposit());
        tfinanceRechargeService.delTfinanceRecharge(si,entity);
        return Response.ok("删除成功");
    }

    @PostMapping("batch/delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response batchDelete(@RequestParam("ids") String[] ids) {
        List<String> idList = java.util.Arrays.asList(ids);
        if(ids!=null){
            for(int i=0;i<ids.length;i++){
                TfinanceRecharge entity = tfinanceRechargeService.selectById(ids[i]);
                TshopInfo si = tshopInfoService.selectOne(entity.getShopid());
                if (si.getTotaldeposit() == null) {
                    return Response.error("删除失败，该商户无充值金额！");
                } else {
                    si.setTotaldeposit(si.getTotaldeposit().subtract(entity.getRechargedeposit()));
                }
                if(si.getTotaldeposit().compareTo(BigDecimal.ZERO)<0){
                    return Response.error("删除失败，该商金额不够！");
                }

                entity.setTotaldeposit(si.getTotaldeposit());
                tfinanceRechargeService.delTfinanceRecharge(si,entity);
            }
        }
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
        EntityWrapper<TfinanceRecharge> entityWrapper = new EntityWrapper<>(entityClass);
        propertyPreFilterable.addQueryProperty("id");
        String userid = UserUtils.getPrincipal().getId();
        if (!StringUtils.isEmpty(userid)) {
            entityWrapper.eq("t.create_by", userid);
        }
        if(queryable.getCondition()!=null) {
            Condition.Filter filter = queryable.getCondition().getFilterFor("createDate");
            if (filter != null) {
                queryable.getCondition().remove(filter);
                queryable.getCondition().and(Condition.Operator.between, "createDate", TaskUtils.whereDate(filter));
            }
        }
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<TfinanceRecharge> pagejson = new PageResponse<>(tfinanceRechargeService.list(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }
}