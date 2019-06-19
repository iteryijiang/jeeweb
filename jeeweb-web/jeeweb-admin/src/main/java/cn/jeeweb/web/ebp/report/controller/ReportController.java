package cn.jeeweb.web.ebp.report.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.jeeweb.common.http.PageResponse;
import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.query.annotation.PageableDefaults;
import cn.jeeweb.common.query.data.PropertyPreFilterable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.aspectj.enums.LogType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.ebp.report.entity.TDayReport;
import cn.jeeweb.web.ebp.report.service.DayReportService;

@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/report/TDayReport")
@ViewPrefix("ebp/report")
@RequiresPathPermission("report:TDayReport")
@Log(title = "报表管理")
public class ReportController extends BaseBeanController<TDayReport> {

	@Autowired
	private DayReportService dayReportService;

	/**
		 * 日报列表查询
		 * 
		 * @param model
		 * @param request
		 * @param response
		 * @return
		 */
	 	//@GetMapping(value = "view")
	    //@RequiresMethodPermissions("view")
	    public ModelAndView list(Model model, HttpServletRequest request, HttpServletResponse response) {
	        ModelAndView mav = displayModelAndView("dayReportList");
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
	@PageableDefaults(sort = "create_date=desc")
	@Log(logType = LogType.SELECT)
	@RequiresMethodPermissions("view")
	public void ajaxList(Queryable queryable, PropertyPreFilterable propertyPreFilterable, HttpServletRequest request,
						 HttpServletResponse response) throws IOException {

		List<TDayReport> list=dayReportService.getDayReportList("2019-06-10","2019-06-16");
		PageResponse<TDayReport> pagejson = new PageResponse<TDayReport>(list);
		SerializeFilter filter = propertyPreFilterable.constructFilter(entityClass);
		String content = JSON.toJSONString(pagejson, filter);
		cn.jeeweb.common.utils.StringUtils.printJson(response,content);
	}


	/***
	 * 进入日报详情页
	 *
	 * @param request
	 * @param response
	 * @return  @RequestBody JSONObject queryObj,
	 */
	@GetMapping("view")
	@RequiresMethodPermissions("view")
	public ModelAndView goToDetailPage(Model model, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = displayModelAndView("dayReportDetail");
		//默认检索时间是前一天
		Date dateTemp=DateUtils.dateAddDay(null, -1);
		String beginDate=DateUtils.formatDate(dateTemp, "yyyy-MM-dd");
		mav.addObject("beginDate",beginDate);
		mav.addObject("endDate",beginDate);
		return mav;
	}
	    
	/**
	 * AJAX查询数据信息
	 *
	 * @param jsonObject
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getDetail", method = { RequestMethod.GET, RequestMethod.POST })
	@Log(logType = LogType.SELECT)
	@RequiresMethodPermissions("view")
	public Response dayreportDetail(@RequestBody JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response) {
		String beginDate=jsonObject.getString("beginDate");
		String endDate=jsonObject.getString("endDate");
		if(StringUtils.isBlank(beginDate)) {
			Date dateTemp=DateUtils.dateAddDay(null, -1);
			beginDate=DateUtils.formatDate(dateTemp, "yyyy-MM-dd");
		}
		if(StringUtils.isBlank(endDate)) {
			Date dateTemp=DateUtils.dateAddDay(null, -1);
			endDate=DateUtils.formatDate(dateTemp, "yyyy-MM-dd");
		}
		TDayReport reportObj=dayReportService.getAvgDayReport(beginDate, endDate);
		return Response.ok(JSONObject.toJSONString(reportObj) );
	}
}
