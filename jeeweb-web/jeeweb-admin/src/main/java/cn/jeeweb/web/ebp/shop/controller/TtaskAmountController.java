package cn.jeeweb.web.ebp.shop.controller;

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
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import cn.jeeweb.web.ebp.shop.entity.TtaskAmount;
import cn.jeeweb.web.ebp.shop.service.TtaskAmountService;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/shop/TtaskAmount")
@ViewPrefix("ebp/shop")
@RequiresPathPermission("shop:TtaskAmount")
@Log(title = "费用明细")
public class TtaskAmountController extends BaseBeanController<TtaskAmount> {

    @Autowired
    private TtaskAmountService ttaskAmountService;
    @Autowired
    private TtaskBaseService ttaskBaseService;

    @GetMapping
    @RequiresMethodPermissions("view")
    public ModelAndView TaskList(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("lisTtaskAmount");
        return mav;
    }

    @GetMapping(value = "add")
    public ModelAndView add(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("data", new TtaskAmount());
        return displayModelAndView ("edit_TtaskAmount");
    }

    @PostMapping("add")
    @Log(logType = LogType.INSERT)
    public Response add(TtaskAmount entity, BindingResult result,
                        HttpServletRequest request, HttpServletResponse response) {
        // 验证错误
        this.checkError(entity,result);
        ttaskAmountService.insert(entity);
        return Response.ok("添加成功");
    }

    @GetMapping(value = "{id}/update")
    public ModelAndView update(@PathVariable("id") String id, Model model, HttpServletRequest request,
                               HttpServletResponse response) {
        TtaskAmount TtaskAmount = ttaskAmountService.selectById(id);
        model.addAttribute("data", TtaskAmount);
        return displayModelAndView ("edit_TtaskAmount");
    }

    @PostMapping("{id}/update")
    @Log(logType = LogType.UPDATE)
    @RequiresMethodPermissions("update")
    public Response update(TtaskAmount entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        try {
            TtaskAmount tb = ttaskAmountService.selectById(entity.getId());
            ttaskAmountService.insertOrUpdate(tb);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Response.ok("更新成功");
    }

    @PostMapping("delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response delete(@PathVariable("id") String id) {
        ttaskAmountService.deleteById(id);
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
        EntityWrapper<TtaskAmount> entityWrapper = new EntityWrapper<>(entityClass);
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
        }
        entityWrapper.groupBy("t.userid");
        // 预处理
        QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
        SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
        PageResponse<TtaskAmount> pagejson = new PageResponse<>(ttaskAmountService.list(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }

    @RequestMapping(value = "selectPictureList", method = { RequestMethod.GET, RequestMethod.POST })
    @PageableDefaults(sort = "id=desc")
    @Log(logType = LogType.SELECT)
    public void selBaseIdMyTaskList(@RequestBody JSONObject jsonObject, HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {

        String taskId = jsonObject.getString("taskId");
        Map map = new HashMap();
        map.put("taskid",taskId);
        List<TtaskAmount> list = ttaskAmountService.selectPictureList(map);
        List<TtaskAmount> newlist = new ArrayList<TtaskAmount>();
        for (TtaskAmount t :list){
            t.setTasktype(DictUtils.getDictLabel(t.getTasktype(),"taskAmounttype",t.getTasktype()));
            newlist.add(t);
        }
        String content = JSON.toJSONString(newlist);
        StringUtils.printJson(response,content);
    }
}