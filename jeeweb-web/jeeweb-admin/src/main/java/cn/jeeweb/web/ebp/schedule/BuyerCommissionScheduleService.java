package cn.jeeweb.web.ebp.schedule;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.ebp.buyer.service.TBuyerCommissionRecordService;

/**
 * 每天运行一次生成买手的佣金信息
 * 
 * @author ytj
 *
 */
@Component("buyerCommissionScheduleService")
public class BuyerCommissionScheduleService {
	@Autowired
	private TBuyerCommissionRecordService buyerCommissionRecordService;

	public void run() {
		Date currentDate=DateUtils.getCurrentDate();
		Date sourceDate=DateUtils.dateAddDay(currentDate,-1);
		try {
			buyerCommissionRecordService.addBuyerCommissionRecord(sourceDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
