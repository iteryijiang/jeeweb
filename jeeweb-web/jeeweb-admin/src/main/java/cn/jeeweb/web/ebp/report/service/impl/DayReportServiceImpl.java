package cn.jeeweb.web.ebp.report.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.ebp.report.entity.DayReport;
import cn.jeeweb.web.ebp.report.mapper.DayReportMapper;
import cn.jeeweb.web.ebp.report.service.DayReportService;
import cn.jeeweb.web.modules.sys.entity.User;

@Service("dayReportService")
@Transactional
public class DayReportServiceImpl extends CommonServiceImpl<DayReportMapper, DayReport> implements DayReportService{

	@Override
	public void addDayReportForCreate(Date sourceDate) {
		DayReport insertObj=new DayReport();
		insertObj.setAtime(sourceDate);
		//期初期末余额
		setDayReportBeginEndBalance(insertObj);
		//任务单数+链接数
		setDayReportTaskCountAndLinkCount(insertObj);
		User createBy=new User();
		createBy.setUsername("sys_schedule");
		insertObj.setCreateBy(createBy);
		insertObj.setCreateDate(DateUtils.getCurrentTime());
		int insertCount=baseMapper.insert(insertObj);
		if(insertCount!=1) {
			throw new RuntimeException("保存日报数据失败");
		}
	}
	
	
	/**
	 * 获取前一天的日报数据
	 * 
	 * @param sourceDate
	 * @return
	 */
	private void setDayReportBeginEndBalance(DayReport insertObj) {
		Date queryDate=DateUtils.addDays(insertObj.getAtime(), -1);
		//起初余额
		String queryDateStr=DateUtils.formatDate(queryDate, "yyyy-MM-dd");
		DayReport preDayReport= getDayReport(queryDateStr);
		if(preDayReport !=null) {
			insertObj.setBeginingBalance(preDayReport.getEndingBalance());
		}
		//期末余额
	}
	
	private void setDayReportTaskCountAndLinkCount(DayReport insertObj) {
		//获取内外部任务总数
		//获取内外部连接总数
		//获取单双连接总数
	}
	

	@Override
	public List<DayReport> getDayReportList(String beginDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DayReport getAvgDayReport(String beginDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public DayReport getDayReport(String queryDate){
		return null;
	}

}
