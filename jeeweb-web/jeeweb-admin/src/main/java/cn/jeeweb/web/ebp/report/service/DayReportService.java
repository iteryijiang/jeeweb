package cn.jeeweb.web.ebp.report.service;

import java.util.Date;
import java.util.List;

import cn.jeeweb.web.ebp.report.entity.TDayReport;

public interface DayReportService {

	/**
	 * 保存日报数据
	 * 
	 * @param obj
	 */
	void addDayReportForCreate(Date sourceDate);
	
	/**
	 * 按照日期查询日报列表数据
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<TDayReport> getDayReportList(String beginDate,String endDate);
	
	/**
	 * 查询一段时间之内日报平均值数据
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	TDayReport getAvgDayReport(String beginDate,String endDate);
	
	/**
	 * 查询某一天的数据
	 * 
	 * @param queryDate
	 * @return
	 */
	TDayReport getDayReport(String queryDate);
}
