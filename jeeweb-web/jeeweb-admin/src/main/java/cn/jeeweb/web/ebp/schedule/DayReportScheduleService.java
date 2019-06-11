package cn.jeeweb.web.ebp.schedule;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.ebp.report.service.DayReportService;

/**
 * 每天运行一次生成平台的日报汇总数据
 * 
 * @author ytj
 *
 */
@Component("dayReportScheduleService")
public class DayReportScheduleService {
	@Autowired
	private DayReportService dayReportService;

	public void run() {
		//即将生成日报的日期
		Date sourceDate=DateUtils.dateAddDay(null, -1);
		try {
			dayReportService.addDayReportForCreate(sourceDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
