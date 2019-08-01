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
import cn.jeeweb.web.ebp.buyer.entity.*;
import cn.jeeweb.web.ebp.buyer.service.TapplyTaskBuyerHandleService;
import cn.jeeweb.web.ebp.buyer.service.TapplyTaskBuyerService;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailService;
import cn.jeeweb.web.ebp.enums.BasicRoleEnum;
import cn.jeeweb.web.ebp.enums.BuyerTaskStatusEnum;
import cn.jeeweb.web.ebp.enums.NotificationTypeRangeEnum;
import cn.jeeweb.web.ebp.enums.YesNoEnum;
import cn.jeeweb.web.ebp.finance.entity.TfinanceBuyerReport;
import cn.jeeweb.web.ebp.finance.service.TfinanceBuyerReportService;
import cn.jeeweb.web.ebp.shop.entity.TshopBase;
import cn.jeeweb.web.ebp.shop.entity.TshopInfo;
import cn.jeeweb.web.ebp.shop.entity.TtaskBase;
import cn.jeeweb.web.ebp.shop.entity.TtaskPictureComment;
import cn.jeeweb.web.ebp.shop.service.TshopBaseService;
import cn.jeeweb.web.ebp.shop.service.TshopInfoService;
import cn.jeeweb.web.ebp.shop.service.TtaskBaseService;
import cn.jeeweb.web.ebp.shop.service.TtaskPictureCommentService;
import cn.jeeweb.web.ebp.shop.util.TaskUtils;
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
	@Autowired
	private TapplyTaskBuyerService tapplyTaskBuyerService;
	@Autowired
	private TapplyTaskBuyerHandleService tapplyTaskBuyerHandleService;
	@Autowired
	private TtaskBaseService ttaskBaseService;
	@Autowired
	private TshopInfoService tshopInfoService;
	@Autowired
	private TshopBaseService tshopBaseService;
	@Autowired
	private TtaskPictureCommentService ttaskPictureCommentService;

	@GetMapping
	@RequiresMethodPermissions("view")
	public ModelAndView list(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = displayModelAndView("list");
		return mav;
	}

	@GetMapping(value = "{id}/buyerDetail")
	@RequiresMethodPermissions("buyerDetail")
	public ModelAndView buyerDetail(@PathVariable("id") String id, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		boolean bool = false;
		if (id.indexOf("_") >= 0) {
			if (!"admin".equals(UserUtils.getUser().getUsername())
					&& !UserUtils.getRoleStringList().contains("finance")) {
				bool = true;
			}
		}
		model.addAttribute("showHidden", bool);
		model.addAttribute("id", id);
		ModelAndView mav = displayModelAndView("list_buyer_detail");
		return mav;
	}

	@GetMapping(value = "buyerDetailGroup")
	@RequiresMethodPermissions("buyerDetailGroup")
	public ModelAndView buyerDetailGroup(Model model, HttpServletRequest request, HttpServletResponse response) {
		boolean bool = false;
		if (!"admin".equals(UserUtils.getUser().getUsername()) && !UserUtils.getRoleStringList().contains("finance")) {
			bool = true;
		}
		model.addAttribute("showHidden", bool);
		ModelAndView mav = displayModelAndView("list_buyer_detailgroup");
		return mav;
	}

	@GetMapping(value = "shopbasehtml")
	@RequiresMethodPermissions("shopbasehtml")
	public ModelAndView shopbasehtml(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = displayModelAndView("list_shop_base");
		return mav;
	}

	@GetMapping(value = "{mytaskId}/{state}/myTaskPicture")
	public ModelAndView taskPicture(@PathVariable("mytaskId") String mytaskId,@PathVariable("state") String state,Model model, HttpServletRequest request, HttpServletResponse response) {
		TmyTaskDetail td = tmyTaskDetailService.selectById(mytaskId);
		Map map = new HashMap();
		map.put("buyerno",td.getBuyerno());
		List<TtaskPictureComment> list = ttaskPictureCommentService.listMytaskPic(map);
		List idlist = new ArrayList();
		if(list!=null){
			for (TtaskPictureComment tpc:list) {
				if(!idlist.contains(tpc.getTdid())){
					idlist.add(tpc.getTdid());
				}

			}
		}

		model.addAttribute("state", state);
		model.addAttribute("data",td);
		model.addAttribute("idlist",JSON.toJSONString(idlist));
		model.addAttribute("list",JSON.toJSONString(list));
		ModelAndView mav = displayModelAndView("MyTaskPicture");
		return mav;
	}
	@PostMapping("add")
	@Log(logType = LogType.INSERT)
	@RequiresMethodPermissions("add")
	public Response add(@RequestBody TmyTaskDetail tmyTaskDetail, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			tmyTaskDetailService.insert(tmyTaskDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 保存之后
		return Response.ok("添加成功");
	}

	@PostMapping("update")
	@Log(logType = LogType.UPDATE)
	@RequiresMethodPermissions("update")
	public Response update(@RequestBody TmyTaskDetail entity, BindingResult result, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			TmyTaskDetail td = tmyTaskDetailService.selectById(entity.getId());
			tmyTaskDetailService.insertOrUpdate(td);
		} catch (Exception e) {
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
		PageResponse<TmyTaskDetail> pagejson = new PageResponse<TmyTaskDetail>(
				tmyTaskDetailService.list(queryable, entityWrapper));
		String content = JSON.toJSONString(pagejson, filter);
		StringUtils.printJson(response, content);
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
	public void ajaxListDetail(@PathVariable("id") String id, Queryable queryable,
			PropertyPreFilterable propertyPreFilterable, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		TfinanceBuyerReport fbr = ttService.selectById(id);
		EntityWrapper<TmyTaskDetail> entityWrapper = new EntityWrapper<>(entityClass);
		propertyPreFilterable.addQueryProperty("id");
		String[] creates = new String[2];
		if (fbr == null) {
			String[] ids = id.split("_");
			creates = TaskUtils.whereNewDate(ids[1], ids[1]);
			if(tshopBaseService.selectById(ids[0])!=null){
				entityWrapper.eq("sb.id", ids[0]);
			}else {
				entityWrapper.eq("bi.userid", ids[0]);

			}
		} else {
			creates = TaskUtils.whereNewDate(DateUtils.formatDate(fbr.getCountcreatedate(), "yyyy-MM-dd"),
					DateUtils.formatDate(fbr.getCountcreatedate(), "yyyy-MM-dd"));
			entityWrapper.eq("t.buyerid", fbr.getBuyerid());
		}

		entityWrapper.between("t.create_date", creates[0], creates[1]);
		entityWrapper.setTableAlias("t");
		// 预处理
		if (queryable.getCondition() != null) {
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
		PageResponse<TmyTaskDetail> pagejson = new PageResponse<TmyTaskDetail>(
				tmyTaskDetailService.listDetail(queryable, entityWrapper));
		String content = JSON.toJSONString(pagejson, filter);
		StringUtils.printJson(response, content);
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
	public void ajaxListDetailGroup(Queryable queryable, PropertyPreFilterable propertyPreFilterable,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		EntityWrapper<TmyTaskDetail> entityWrapper = new EntityWrapper<>(entityClass);
		propertyPreFilterable.addQueryProperty("id");
		entityWrapper.setTableAlias("t");
		String d = DateUtils.formatDate(DateUtils.dateAddDay(new Date(), -1), "yyyy-MM-dd");
		String[] creates = TaskUtils.whereNewDate(d, d);
		if (queryable.getCondition() != null) {
			Condition.Filter filter = queryable.getCondition().getFilterFor("receivingdates");
			if (filter != null) {
				queryable.getCondition().remove(filter);
			}
			creates = TaskUtils.whereDate(filter);
		}

		entityWrapper.between("t.receivingdate", creates[0], creates[1]);
		// 预处理
		if (queryable.getCondition() != null) {
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
		entityWrapper.groupBy("buyeridName").groupBy("buyeridLogin").groupBy("receivingdates").groupBy("orderdates")
				.groupBy("shopLoginname").groupBy("shopidName").groupBy("shopname").groupBy("buyerjdnick")
				.groupBy("jdorderno").groupBy("taskstate");
		QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
		SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
		PageResponse<TmyTaskDetail> pagejson = new PageResponse<TmyTaskDetail>(
				tmyTaskDetailService.listDetailGroup(queryable, entityWrapper));
		String content = JSON.toJSONString(pagejson, filter);
		StringUtils.printJson(response, content);
	}

	@RequestMapping(value = "listShopBaseDetail", method = { RequestMethod.GET, RequestMethod.POST })
	@RequiresMethodPermissions("listShopBaseDetail")
	public void listShopBaseDetail(Queryable queryable, PropertyPreFilterable propertyPreFilterable,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		EntityWrapper<TmyTaskDetail> entityWrapper = new EntityWrapper<>(entityClass);
		String[] s = { "id", "tUrl" };
		propertyPreFilterable.addQueryProperty(s);
		entityWrapper.setTableAlias("t");
		// 预处理
		if (queryable.getCondition() != null) {
			Condition.Filter filter_orderdate = queryable.getCondition().getFilterFor("orderdate");
			if (filter_orderdate != null) {
				queryable.getCondition().remove(filter_orderdate);
				queryable.getCondition().and(Condition.Operator.between, "orderdate",
						TaskUtils.whereDate(filter_orderdate));
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
			PageResponse<TmyTaskDetail> pagejson = new PageResponse<TmyTaskDetail>(
					tmyTaskDetailService.listShopBaseDetail(queryable, entityWrapper));
			String content = JSON.toJSONString(pagejson, filter);
			StringUtils.printJson(response, content);
		} else {
			SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
//            new PageImpl<TmyTaskDetail>(new ArrayList<>(), queryable.getPageable(), 0);
			PageResponse<TmyTaskDetail> pagejson = new PageResponse<TmyTaskDetail>();
			String content = JSON.toJSONString(pagejson, filter);
			StringUtils.printJson(response, content);
		}
//        if(queryable.getCondition()==null||queryable.getCondition().getFilterFor("receivingdate")==null) {
//            String[] creates = TaskUtils.whereNewDate("", "");
//            entityWrapper.between("receivingdate", creates[0], creates[1]);
//        }

	}

	@GetMapping("{id}/{buyerjdnick}/upbuyerjdnick")
	@Log(logType = LogType.UPDATE)
	public void upbuyerjdnick(@PathVariable("id") String id, @PathVariable("buyerjdnick") String buyerjdnick,
			HttpServletRequest request, HttpServletResponse response) {
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
	public void upjdorderno(@PathVariable("id") String id, @PathVariable("jdorderno") String jdorderno,
			HttpServletRequest request, HttpServletResponse response) {
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
			map.put("storename", StringUtils.isEmpty(td.getStorename()) ? "NA" : td.getStorename());
			map.put("buyerid", StringUtils.isEmpty(td.getBuyerid()) ? "NA" : td.getBuyerid());
			map.put("mytaskid", StringUtils.isEmpty(td.getMytaskid()) ? "NA" : td.getMytaskid());
			List<TmyTaskDetail> list = tmyTaskDetailService.selectByMap(map);
			if (list != null && !list.isEmpty()) {
				for (TmyTaskDetail ttd : list) {
					ttd.setJdorderno(jdorderno);
				}
				tmyTaskDetailService.updateBatchById(list);
			}

		} catch (Exception e) {
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
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok("修改失败！");
		}
		return Response.ok("修改成功！");

	}

	@GetMapping("{id}/{buyerjdnick}/upTaskDetail")
	@Log(logType = LogType.UPDATE)
	public void upTaskDetail(@PathVariable("id") String id, @PathVariable("buyerjdnick") String buyerjdnick,
			HttpServletRequest request, HttpServletResponse response) {
		/// {jdorderno}@PathVariable("jdorderno") String jdorderno,
		Map map = new HashMap();
		map.put("mytaskid", id);
		List<TmyTaskDetail> list = tmyTaskDetailService.selectByMap(map);
		for (TmyTaskDetail ttd : list) {
			ttd.setBuyerjdnick(buyerjdnick);
//            ttd.setJdorderno(jdorderno);

		}
		tmyTaskDetailService.updateBatchById(list);
	}

	@RequestMapping("{id}/export")
	public void export(@PathVariable("id") String id, ModelMap map, Queryable queryable,
			PropertyPreFilterable propertyPreFilterable, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		TfinanceBuyerReport fbr = ttService.selectById(id);
		EntityWrapper<TmyTaskDetail> entityWrapper = new EntityWrapper<>(entityClass);
		propertyPreFilterable.addQueryProperty("id");
		String[] creates = new String[2];
		if (fbr == null) {
			String[] ids = id.split("_");
			creates = TaskUtils.whereNewDate(ids[1], ids[1]);
			if(tshopBaseService.selectById(ids[0])!=null){
				entityWrapper.eq("sb.id", ids[0]);
			}else {
				entityWrapper.eq("bi.userid", ids[0]);

			}
		} else {
			creates = TaskUtils.whereNewDate(DateUtils.formatDate(fbr.getCountcreatedate(), "yyyy-MM-dd"),
					DateUtils.formatDate(fbr.getCountcreatedate(), "yyyy-MM-dd"));
			entityWrapper.eq("t.buyerid", fbr.getBuyerid());
		}

		entityWrapper.between("t.create_date", creates[0], creates[1]);
		// 预处理
		if (queryable.getCondition() != null) {
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
		List<TmyTaskDetail> userList = tmyTaskDetailService.listNoPageDetail(queryable, entityWrapper);
		String title = "买手财务报表详情";
		ExportParams params = new ExportParams(title, title, ExcelType.XSSF);
		if (fbr == null && !"admin".equals(UserUtils.getUser().getUsername())
				&& !UserUtils.getRoleStringList().contains("finance")) {
			List<TmyTaskDetailExport> new_list = new ArrayList<TmyTaskDetailExport>();
			for (TmyTaskDetail ttd : userList) {
				TmyTaskDetailExport ttde = new TmyTaskDetailExport();
				try {
					BeanUtils.copyProperties(ttd, ttde);
				} catch (Exception e) {
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
		} else {
			map.put(NormalExcelConstants.DATA_LIST, userList);
			map.put(NormalExcelConstants.CLASS, TmyTaskDetail.class);
		}
		map.put(NormalExcelConstants.PARAMS, params);
		map.put("fileName", title + "-" + DateUtils.getDateTime());
		PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
	}

	@RequestMapping("exportGroup")
	public void exportGroup(ModelMap map, Queryable queryable, PropertyPreFilterable propertyPreFilterable,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		EntityWrapper<TmyTaskDetail> entityWrapper = new EntityWrapper<>(entityClass);
		propertyPreFilterable.addQueryProperty("id");
		entityWrapper.setTableAlias("t");
		String d = DateUtils.formatDate(DateUtils.dateAddDay(new Date(), -1), "yyyy-MM-dd");
		String[] creates = TaskUtils.whereNewDate(d, d);
		if (queryable.getCondition() != null) {
			Condition.Filter filter = queryable.getCondition().getFilterFor("receivingdates");
			if (filter != null) {
				queryable.getCondition().remove(filter);
			}
			creates = TaskUtils.whereDate(filter);
		}

		entityWrapper.between("t.receivingdate", creates[0], creates[1]);
		// 预处理
		if (queryable.getCondition() != null) {
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
		entityWrapper.groupBy("buyeridName").groupBy("buyeridLogin").groupBy("receivingdates").groupBy("orderdates")
				.groupBy("shopLoginname").groupBy("shopidName").groupBy("shopname").groupBy("buyerjdnick")
				.groupBy("jdorderno").groupBy("taskstate");

		QueryableConvertUtils.convertQueryValueToEntityValue(queryable, entityClass);
		List<TmyTaskDetail> userList = tmyTaskDetailService.listNoPageDetailGroup(queryable, entityWrapper);
		String title = "买手财务报表汇总";
		ExportParams params = new ExportParams(title, title, ExcelType.XSSF);
		List<TmyTaskDetailExportGroup> new_list = new ArrayList<TmyTaskDetailExportGroup>();
		for (TmyTaskDetail ttd : userList) {
			TmyTaskDetailExportGroup ttde = new TmyTaskDetailExportGroup();
			try {
				BeanUtils.copyProperties(ttd, ttde);
			} catch (Exception e) {
				e.printStackTrace();
			}
			new_list.add(ttde);
		}
		map.put(NormalExcelConstants.DATA_LIST, new_list);
		map.put(NormalExcelConstants.CLASS, TmyTaskDetailExportGroup.class);
		map.put(NormalExcelConstants.PARAMS, params);
		map.put("fileName", title + "-" + DateUtils.getDateTime());
		PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
	}

	/**
	 * 点击进入异常订单申请
	 *
	 * @param model
	 * @param taskId
	 * @return
	 */
	@GetMapping("{taskId}/unusualTaskApply")
	@Log(logType = LogType.SELECT)
	public ModelAndView unusualTaskApply(@PathVariable("taskId") String taskId, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		// 获取任务信息(包括任务单号、商品相关信息)
		TmyTaskDetail taskObj = null;
		TapplyTaskBuyer ttb = new TapplyTaskBuyer();
		try {
			taskObj = tmyTaskDetailService.selectById(taskId);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			taskObj = new TmyTaskDetail();
			taskObj.setId(taskId);
			model.addAttribute("taskObj", taskObj);
			model.addAttribute("ttb", ttb);
			return displayModelAndView("unusualTaskApply");
		}
	}

	/**
	 * 申请保存
	 *
	 * @param jsonObject
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("addUnusualTaskApply")
	@Log(logType = LogType.INSERT)
	public Response unusualTaskApply(@RequestBody JSONObject jsonObject, HttpServletRequest request,
			HttpServletResponse response) {
		// 获取任务信息(包括任务单号、商品相关信息)
		TapplyTaskBuyer obj = new TapplyTaskBuyer();
		obj.setBuyerTaskId(jsonObject.getString("taskId"));
		obj.setApplyType(jsonObject.getString("applyType"));
		obj.setApplyDesc(jsonObject.getString("applyDesc"));
		if (StringUtils.isEmpty(obj.getBuyerTaskId()) || StringUtils.isEmpty(obj.getApplyType())
				|| StringUtils.isEmpty(obj.getApplyDesc())) {
			return Response.error("参数错误[未获取到买手任务ID|异常申请类型|异常说明]！");
		}
		tapplyTaskBuyerService.insertTapplyTaskBuyer(obj);
		return Response.ok("保存成功！");
	}

	/***
	 * 管理员进入异常订单申请列表
	 * 
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "applyTaskList")
	@RequiresMethodPermissions("applyTaskList")
	public ModelAndView applyTaskList(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = displayModelAndView("unUsalTaskApplyList");
		return mav;
	}
	
	/***
	 * 进入异常订单申请报表列表
	 * 进入的页面只有查看详情页面,没有其他的操作
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "goToApplyTaskReportList")
	@RequiresMethodPermissions("applyTaskList")
	public ModelAndView goToApplyTaskReportList(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = displayModelAndView("unUsalTaskApplyReportList");
		return mav;
	}
	

	/***
	 * AJAX查询查询异常订单申请列表数据
	 *
	 * @param queryable
	 * @param propertyPreFilterable
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "ajaxApplyTaskList", method = { RequestMethod.GET, RequestMethod.POST })
	@Log(logType = LogType.SELECT)
	@RequiresMethodPermissions("applyTaskList")
	public void getUnusualTaskApply(Queryable queryable, PropertyPreFilterable propertyPreFilterable,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		String content = null;
		try {
			EntityWrapper<TapplyTaskBuyer> entityWrapper = new EntityWrapper<TapplyTaskBuyer>(TapplyTaskBuyer.class);
			propertyPreFilterable.addQueryProperty("id");
			//设置检索所属的商户/买手/角色ID
			setApplyTaskQueryRoleId(entityWrapper);
			if (queryable.getCondition() != null) {
				// 配置过滤参数,需要调整
				Condition.Filter shopNoFilter = queryable.getCondition().getFilterFor("shopName");
				if (shopNoFilter != null) {
					queryable.getCondition().remove(shopNoFilter);
					entityWrapper.like("shopName", shopNoFilter.getValue().toString());
				}
				Condition.Filter buyerTaskNoFilter = queryable.getCondition().getFilterFor("buyerTaskNo");
				if (buyerTaskNoFilter != null) {
					queryable.getCondition().remove(buyerTaskNoFilter);
					entityWrapper.like("buyerTaskNo", buyerTaskNoFilter.getValue().toString());
				}
				Condition.Filter shopTaskNoFilter = queryable.getCondition().getFilterFor("shopTaskNo");
				if (shopTaskNoFilter != null) {
					queryable.getCondition().remove(shopTaskNoFilter);
					entityWrapper.like("shopTaskNo", shopTaskNoFilter.getValue().toString());
				}
				Condition.Filter buyerNoFilter = queryable.getCondition().getFilterFor("buyerNo");
				if (buyerNoFilter != null) {
					queryable.getCondition().remove(buyerNoFilter);
					entityWrapper.like("buyerNo", buyerNoFilter.getValue().toString());
				}
				Condition.Filter applyTypeFilter = queryable.getCondition().getFilterFor("applyType");
				if (applyTypeFilter != null) {
					queryable.getCondition().remove(applyTypeFilter);
					entityWrapper.like("applyType", applyTypeFilter.getValue().toString());
				}
				Condition.Filter applyStatusFilter = queryable.getCondition().getFilterFor("applyStatus");
				if (applyStatusFilter != null) {
					queryable.getCondition().remove(applyStatusFilter);
					entityWrapper.like("applyStatus", applyStatusFilter.getValue().toString());
				}
				Condition.Filter applyTimeFilter = queryable.getCondition().getFilterFor("applyTime");
				if (applyTimeFilter != null) {
					queryable.getCondition().remove(applyTimeFilter);
					queryable.getCondition().and(Condition.Operator.between, "applyTime",
							TaskUtils.whereDate(applyTimeFilter));
				}
			}
			entityWrapper.orderBy("applyStatus", true);
			entityWrapper.orderBy("applyTime", false);
			// 预处理
			QueryableConvertUtils.convertQueryValueToEntityValue(queryable, TapplyTaskBuyer.class);
			SerializeFilter filter = propertyPreFilterable.constructFilter(TapplyTaskBuyer.class);
			PageResponse<TapplyTaskBuyer> pagejson = new PageResponse<TapplyTaskBuyer>(
					tapplyTaskBuyerService.selectApplyPageList(queryable, entityWrapper));
			content = JSON.toJSONString(pagejson, filter);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			StringUtils.printJson(response, content);
		}
	}

	/**
	 * 设置查询申请所属的用户ID
	 * 
	 * @param queryWrapper
	 */
	private void setApplyTaskQueryRoleId(EntityWrapper<TapplyTaskBuyer> queryWrapper) {
		// 当前登录人所属角色
		Set<String> roleSet = UserUtils.getRoleStringList();
		// 当前登录人
		User loginUser = UserUtils.getUser();
		if (!roleSet.isEmpty()) {
			for (String roleId : roleSet) {
				// 管理员
				if (BasicRoleEnum.ADMIN.roleCode.equals(roleId)) {
					return;
				}
				// 商户运营,设置商户的ID
				if (BasicRoleEnum.SHOP.roleCode.equals(roleId)) {
					queryWrapper.eq("shop_id", loginUser.getId());
					return;
				}
				// 买手,设置买手的ID
				if (BasicRoleEnum.BUYER.roleCode.equals(roleId)) {
					queryWrapper.eq("buyer_id", loginUser.getId());
					return;
				}
			}
		}
	}

	/**
	 * 异常订单处理 初始化取消买手申请
	 *
	 * @param applyId:申请ID
	 * @param gotoPage：1进入可以取消申请的页面0进入报表页面不可编辑
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("getApplyTaskDetail/{gotoPage}/{applyId}")
	@Log(logType = LogType.SELECT)
	public ModelAndView getApplyTaskDetail(@PathVariable("gotoPage") int gotoPage, @PathVariable("applyId") String applyId,Model model,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav=null;
		if(YesNoEnum.YES.code == gotoPage) {
			mav = displayModelAndView("showTaskApplyDetail");
		}else {
			mav = displayModelAndView("showTaskApplyDetailReport");
		}
		try {
			//获得申请信息
			TapplyTaskBuyer obj = tapplyTaskBuyerService.selectApplyTaskById(applyId);
			//商户任务信息
			TtaskBase tb = ttaskBaseService.selectById(obj.getShopTaskId());//tmyTaskDetail.getTaskid()
			TshopInfo tsi = tshopInfoService.selectOne(tb.getShopid());
			TshopBase tsb = tshopBaseService.selectById(tb.getStorename());
			model.addAttribute("tb", tb);
			model.addAttribute("tsi", tsi);
			model.addAttribute("tsb", tsb);
			// 进行中，完成数
			int taskstatus0 = 0, taskstatus1 = 0;
			int taskstate2 = 0, taskstate4 = 0;
			List<Map> list = tmyTaskDetailService.groupBytaskstatus(tb.getId());
			for (int i = 0; i < list.size(); i++) {
				Map map = list.get(i);
				if ("0".equals(map.get("taskstatus").toString())) {
					taskstatus0 = Integer.parseInt(map.get("counts").toString());
				} else if ("1".equals(map.get("taskstatus").toString())) {
					taskstatus1 = Integer.parseInt(map.get("counts").toString());
				}
			}
			List<Map> listState = tmyTaskDetailService.groupBytaskstate(tb.getId());
			for (int i = 0; i < listState.size(); i++) {
				Map map = listState.get(i);
				if ("2".equals(map.get("taskstate").toString())) {
					taskstate2 = Integer.parseInt(map.get("counts").toString());
				} else if ("4".equals(map.get("taskstate").toString())) {
					taskstate4 = Integer.parseInt(map.get("counts").toString());
				}
			}
			model.addAttribute("taskstatus0", taskstatus0);
			model.addAttribute("taskstatus1", taskstatus1);
			model.addAttribute("taskstate2", taskstate2);
			model.addAttribute("taskstate4", taskstate4);
			model.addAttribute("createDateFormat", DateUtils.formatDate(tb.getCreateDate(), "yyyy-MM-dd"));
			model.addAttribute("effectdateFormat", DateUtils.formatDate(tb.getEffectdate(), "yyyy-MM-dd"));
			model.addAttribute("applyObj", obj);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mav;
	}
	

	/**
	 * 异常订单处理 取消商户任务
	 *
	 * @param applyId
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("cancelApplyTaskForShop/{applyId}")
	@Log(logType = LogType.UPDATE)
	public Response cancelApplyTaskForShop(@PathVariable("applyId") String applyId, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			TapplyTaskBuyerHandle obj = new TapplyTaskBuyerHandle();
			obj.setApplyTaskId(applyId);
			obj.setHandleMethod(2);
			obj.setCreateBy(UserUtils.getUser());
			obj.setRemarks("管理员取消商户任务");
			tapplyTaskBuyerHandleService.updateTapplyTaskBuyerForHandle(obj);
			return Response.ok("操作成功！");
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return Response.error("操作失败[" + ex.getMessage() + "]！");
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.error("操作失败[系统异常]！");
		}
	}

	/**
	 * 异常订单处理 初始化取消买手申请
	 *
	 * @param applyId
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("initTurnDownApplyTask/{applyId}")
	@Log(logType = LogType.SELECT)
	public ModelAndView initTurnDownApplyTask(@PathVariable("applyId") String applyId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = displayModelAndView("turnDownTaskApply");
		try {
			TapplyTaskBuyer obj = tapplyTaskBuyerService.selectById(applyId);
			model.addAttribute("applyObj", obj);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mav;
	}

	/**
	 * 异常订单处理 驳回买手申请
	 *
	 * @param jsonObject
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("cancelApplyTaskForBuyer")
	@Log(logType = LogType.UPDATE)
	public Response cancelApplyTaskForBuyer(@RequestBody JSONObject jsonObject, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (StringUtils.isEmpty(jsonObject.getString("applyId"))
					|| StringUtils.isEmpty(jsonObject.getString("remarks"))) {
				return Response.error("操作失败[未获取到请求参数信息]！");
			}
			TapplyTaskBuyerHandle obj = new TapplyTaskBuyerHandle();
			obj.setApplyTaskId(jsonObject.getString("applyId"));
			obj.setHandleMethod(1);
			obj.setCreateBy(UserUtils.getUser());
			obj.setRemarks(jsonObject.getString("remarks"));
			tapplyTaskBuyerHandleService.updateTapplyTaskBuyerForHandle(obj);
			return Response.ok("操作成功！");
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return Response.error("操作失败[" + ex.getMessage() + "]！");
		} catch (Exception ex) {
			ex.printStackTrace();
			return Response.error("操作失败[系统异常]！");
		}
	}

	/**
	 * 动态加载最近的20条需要完成的任务单
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "getLatestNeedFinishTaskList", method = { RequestMethod.GET, RequestMethod.POST })
	@PageableDefaults(sort = "id=desc")
	@Log(logType = LogType.SELECT)
	public String getLatestNeedFinishTaskList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try{
			EntityWrapper<TmyTaskDetail> entityWrapper = new EntityWrapper<>(TmyTaskDetail.class);
			propertyPreFilterable.addQueryProperty("id");
			entityWrapper.eq("t.ispicture", YesNoEnum.YES.code);
			entityWrapper.eq("t.taskstate", BuyerTaskStatusEnum.WAITING_ACCEPT.code);
			Date currentTime=DateUtils.getCurrentTime();
			entityWrapper.between("t.receivingdate",DateUtils.dateAddDay(currentTime,-3),currentTime);
			//设置检索所属的商户/买手/角色ID
			setNeedFinishTaskQueryRoleId(entityWrapper);
			entityWrapper.orderBy("create_date", false);
			SerializeFilter filter = propertyPreFilterable.constructFilter(TmyTaskDetail.class);
			PageResponse<TmyTaskDetail> pagejson = new PageResponse<>(tmyTaskDetailService.listNeedFinishTask(queryable,entityWrapper));
			//String content = JSON.toJSONString(pagejson, filter);
			return JSONObject.toJSONString(pagejson);
		}catch (Exception ex){
			ex.printStackTrace();
			return null;
		}

	}
	/**
	 * 设置查询申请所属的用户ID
	 *
	 * @param queryWrapper
	 */
	private void setNeedFinishTaskQueryRoleId(EntityWrapper<TmyTaskDetail> queryWrapper) {
		// 当前登录人
		Set<String> roleSet = UserUtils.getRoleStringList();
		User loginUser = UserUtils.getUser();
		if (!roleSet.isEmpty()) {
			for (String roleId : roleSet) {
				// 管理员
				if (BasicRoleEnum.ADMIN.roleCode.equals(roleId)) {
					return;
				}
				// 商户运营,设置商户的ID
				if (BasicRoleEnum.SHOP.roleCode.equals(roleId)) {
					queryWrapper.eq("sb.userid", loginUser.getId());
					return;
				}
				if (BasicRoleEnum.SELLING.roleCode.equals(roleId)) {
					queryWrapper.eq("t.buyerid", "***");
					return;
				}
				// 买手,设置买手的ID
				if (BasicRoleEnum.BUYER.roleCode.equals(roleId)) {
					queryWrapper.eq("t.buyerid", loginUser.getId());
					return;
				}
			}
		}
	}

}