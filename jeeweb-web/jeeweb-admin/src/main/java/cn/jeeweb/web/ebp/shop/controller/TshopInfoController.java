package cn.jeeweb.web.ebp.shop.controller;

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
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import cn.jeeweb.web.ebp.shop.util.TaskUtils;
import cn.jeeweb.web.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import java.util.Map;
import java.util.List;


@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/shop/TshopInfo")
@ViewPrefix("ebp/shop")
@RequiresPathPermission("shop:TshopInfo")
@Log(title = "商户管理")
public class TshopInfoController extends BaseBeanController<TshopInfo> {

    @Autowired
    private TshopInfoService tshopInfoService;
    @Autowired
    private TtaskBaseService ttaskBaseService;

    @GetMapping
    @RequiresMethodPermissions("view")
    public ModelAndView TaskList(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = displayModelAndView("listshopinfo");
        return mav;
    }

    @GetMapping(value = "add")
    public ModelAndView add(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("data", new TshopInfo());
        return displayModelAndView ("edit_TshopInfo");
    }

    @PostMapping("add")
    @Log(logType = LogType.INSERT)
    public Response add(TshopInfo entity, BindingResult result,
                        HttpServletRequest request, HttpServletResponse response) {
        // 验证错误
        this.checkError(entity,result);
        tshopInfoService.insert(entity);
        return Response.ok("添加成功");
    }

    @GetMapping(value = "{id}/update")
    public ModelAndView update(@PathVariable("id") String id, Model model, HttpServletRequest request,
                               HttpServletResponse response) {
        TshopInfo tshopInfo = tshopInfoService.selectById(id);
        model.addAttribute("data", tshopInfo);
        return displayModelAndView ("edit_TshopInfo");
    }

    @PostMapping("{id}/update")
    @Log(logType = LogType.UPDATE)
    @RequiresMethodPermissions("update")
    public Response update(TshopInfo entity, BindingResult result,
                           HttpServletRequest request, HttpServletResponse response) {
        try {
            TshopInfo tb = tshopInfoService.selectById(entity.getId());
            tb.setAccountlevel(entity.getAccountlevel());
            tb.setFromInnerOuter(entity.getFromInnerOuter());
            tshopInfoService.insertOrUpdate(tb);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Response.ok("更新成功");
    }

    @PostMapping("delete")
    @Log(logType = LogType.DELETE)
    @RequiresMethodPermissions("delete")
    public Response delete(@PathVariable("id") String id) {
        tshopInfoService.deleteById(id);
        return Response.ok("删除成功");
    }

    /**
     * 关键字查询商铺信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getShopInfoSuggest")
    @ResponseBody
    public JSONArray getShopInfoSuggest(HttpServletRequest request, HttpServletResponse response){
        try{
            String keyWord = request.getParameter("keyword");
            List<TshopInfo> resultList = tshopInfoService.findShopInfoByKeyWord(keyWord);
            if(!resultList.isEmpty()){
                JSONArray retObj=new JSONArray();
                for(TshopInfo obj:resultList){
                    JSONObject objTemp=new JSONObject();
                    objTemp.put("showValue",obj.getUserid());
                    objTemp.put("showText",obj.getShopname()+"["+obj.getLoginname()+"]");
                    retObj.add(objTemp);
                }
                return retObj;
            }
            return null;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }

    }

    /*
     * 商户余额信息统计
     */
    @RequestMapping(value = "sumShopInfo", method = { RequestMethod.GET, RequestMethod.POST })
    public void sumShopInfo(@RequestBody JSONObject jsonObject, HttpServletRequest request,
                                        HttpServletResponse response) throws IOException {
        Map map = new HashMap();
        try {
            String userid = "";
            if (!"admin".equals(UserUtils.getUser().getUsername())) {
                userid = UserUtils.getPrincipal().getId();
            }
            String create1 = jsonObject.getString("create1");
            String create2 = jsonObject.getString("create2");
//            String status = jsonObject.getString("status");
            String loginname = jsonObject.getString("loginname");
            String shopname = jsonObject.getString("shopname");
            String fromInnerOuter = jsonObject.getString("fromInnerOuter");
            String[] creates = TaskUtils.whereNewDate(create1,create2);

            Map m = new HashMap();
            m.put("userid",userid);
            m.put("create1",creates[0]);
            m.put("create2",creates[1]);
            m.put("loginname",(StringUtils.isNotEmpty(loginname)?"%"+loginname+"%":null));
            m.put("shopname",(StringUtils.isNotEmpty(shopname)?"%"+shopname+"%":null));
//            m.put("status",status);
            m.put("fromInnerOuter",fromInnerOuter);
            //获取商户余额，冻结金额等信息
            Integer i = ttaskBaseService.sumTtaskBase(m);
            map.put("sumTaskBase",i);
            Map shopInfoM =  tshopInfoService.selectSumOne(m);
            if(shopInfoM!=null) {
                map.put("sumAvailabledeposit",shopInfoM.get("sumAvailabledeposit"));
                map.put("sumTaskdeposit",shopInfoM.get("sumTaskdeposit"));
                BigDecimal sumTotaldeposit = new BigDecimal(0);
                if(shopInfoM.get("sumAvailabledeposit")!=null&&shopInfoM.get("sumTaskdeposit")!=null){
                    sumTotaldeposit = new BigDecimal(shopInfoM.get("sumAvailabledeposit").toString()).add(new BigDecimal(shopInfoM.get("sumTaskdeposit").toString()));
                }
                map.put("sumTotaldeposit",sumTotaldeposit);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        String content = JSON.toJSONString(map);
        StringUtils.printJson(response,content);
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
        EntityWrapper<TshopInfo> entityWrapper = new EntityWrapper<>(entityClass);
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
        PageResponse<TshopInfo> pagejson = new PageResponse<>(tshopInfoService.list(queryable,entityWrapper));
        String content = JSON.toJSONString(pagejson, filter);
        StringUtils.printJson(response,content);
    }

}