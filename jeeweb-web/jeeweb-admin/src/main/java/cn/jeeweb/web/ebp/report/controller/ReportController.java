package cn.jeeweb.web.ebp.report.controller;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import cn.jeeweb.common.mvc.controller.BaseBeanController;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.ebp.report.entity.TDayReport;
import cn.jeeweb.web.ebp.report.service.DayReportService;

@RestController
@RequestMapping("${jeeweb.admin.url.prefix}/report/reportInfo")
@ViewPrefix("ebp/report")
@RequiresPathPermission("report:reportInfo")
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
	 	@GetMapping
	    @RequiresMethodPermissions("view")
	    public ModelAndView list(Model model, HttpServletRequest request, HttpServletResponse response) {
	        ModelAndView mav = displayModelAndView("dayReportList");
	        return mav;
	    }
	 	
	 	/***
	     * 获取单条消息通知
	     *
	     * @param id
	     * @param request
	     * @param response
	     * @return
	     */
	    @GetMapping("getDayReport")
	    @RequiresMethodPermissions("detail")
	    public ModelAndView TaskDetail(@RequestBody JSONObject queryObj,Model model, HttpServletRequest request, HttpServletResponse response) {
	        ModelAndView mav = displayModelAndView("dayReportDetail");
	        String beginDate=queryObj.getString("beginDate");
	        String endDate=queryObj.getString("endDate");
	        if(StringUtils.isBlank(beginDate)) {
	        	Date dateTemp=DateUtils.dateAddDay(null, -1);
	        	beginDate=DateUtils.formatDate(dateTemp, "yyyy-MM-dd");
	        }
	        if(StringUtils.isBlank(endDate)) {
	        	Date dateTemp=DateUtils.dateAddDay(null, -1);
	        	endDate=DateUtils.formatDate(dateTemp, "yyyy-MM-dd");
	        }
	        TDayReport reportObj=dayReportService.getAvgDayReport(beginDate, endDate);
	        model.addAttribute("reportObj",reportObj);
	        return mav;
	    }
}
