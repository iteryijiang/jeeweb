package cn.jeeweb.web.ebp.schedule;

import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.ebp.buyer.service.TBuyerCommissionRecordService;
import cn.jeeweb.web.ebp.seller.service.TSellerCommissionReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 每天运行一次生成销售的佣金信息
 * 
 * @author ytj
 *
 */
@Component("sellerCommissionScheduleService")
public class SellerCommissionScheduleService {
	@Resource(name = "sellerCommissionReportService")
	private TSellerCommissionReportService sellerCommissionReportService;

	/**
	 * 每天生成佣金信息
	 *
	 */
	public void run() {
		Date currentDate=DateUtils.getCurrentDate();
		Date sourceDate=DateUtils.dateAddDay(currentDate,-1);
		try {
			sellerCommissionReportService.insertSellerCommissionReportForCreate(sourceDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 每个月2号生成上一个月退单的佣金
	 *
	 */
	public void execBackTaskCommissionJob() {
		Date currentDate=DateUtils.getCurrentDate();
		Date beginDate=DateUtils.lastMonthFirstDay(currentDate);
		Date endDate=DateUtils.lastMonthEndDay(currentDate);
		try {
			sellerCommissionReportService.insertSellerCommissionReportForBackTask(beginDate,endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
