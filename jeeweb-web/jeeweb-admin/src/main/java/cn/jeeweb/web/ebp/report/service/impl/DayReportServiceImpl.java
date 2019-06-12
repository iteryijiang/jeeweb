package cn.jeeweb.web.ebp.report.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.ebp.report.entity.TDayReport;
import cn.jeeweb.web.ebp.report.mapper.DayReportMapper;
import cn.jeeweb.web.ebp.report.service.DayReportService;
import cn.jeeweb.web.modules.sys.entity.User;

@Service("dayReportService")
@Transactional
public class DayReportServiceImpl extends CommonServiceImpl<DayReportMapper, TDayReport> implements DayReportService{

	@Override
	public void addDayReportForCreate(Date sourceDate) {
		TDayReport insertObj=new TDayReport();
		insertObj.setAtime(sourceDate);
		Map<String, Object> queryParam= installDayReportTimeParam(insertObj.getAtime());
		//期初期末余额
		setDayReportBeginEndBalance(insertObj,queryParam);
		setDayReportTotalRechargeDeposit(insertObj, queryParam);
		//任务单数+链接数
		setDayReportInnerOuterTaskCount(insertObj, queryParam);
		setDayReportInnerOuterTaskLinkCount(insertObj, queryParam);
		setDayReportSingleDoubleLinkCount(insertObj,queryParam);
		setDayReportZeroCommissionTaskCountAndLInkCount(insertObj, queryParam);
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
	 * 当天收取的押金总和
	 * 
	 * @param insertObj
	 * @param queryParam
	 */
	private void setDayReportTotalRechargeDeposit(TDayReport insertObj,Map<String, Object> queryParam) {
		BigDecimal totalRechargeDeposit=baseMapper.getTotalRechargeDeposit(queryParam);
		insertObj.setTotalRechargeDeposit(totalRechargeDeposit);
	}
	
	
	
	/**
	 * 获取前一天的日报数据
	 * 
	 * @param insertObj
	 * @param queryParam
	 */
	private void setDayReportBeginEndBalance(TDayReport insertObj,Map<String, Object> queryParam) {
		Date queryDate=DateUtils.addDays(insertObj.getAtime(), -1);
		//起初余额
		String queryDateStr=DateUtils.formatDate(queryDate, "yyyy-MM-dd");
		TDayReport preDayReport= getDayReport(queryDateStr);
		if(preDayReport !=null) {
			insertObj.setBeginingBalance(preDayReport.getEndingBalance());
		}
		//期末余额
		
	}
	
	/**
	 * 内部外部任务总数+总任务数量
	 * 
	 * @param insertObj
	 * @param queryParam
	 */
	private void setDayReportInnerOuterTaskCount(TDayReport insertObj,Map<String, Object> queryParam) {
		//获取内外部任务总数
		Map<String, Object> innerOuterTaskResultMap=baseMapper.getInnerOuterTaskCount(queryParam);
		if(innerOuterTaskResultMap !=null && !innerOuterTaskResultMap.isEmpty()) {
			if(innerOuterTaskResultMap.get("innerCount") !=null) {
				insertObj.setInternalTaskCount(Long.valueOf(innerOuterTaskResultMap.get("innerCount") .toString()));
			}
			if(innerOuterTaskResultMap.get("outerCount") !=null) {
				insertObj.setOuterTaskCount(Long.valueOf(innerOuterTaskResultMap.get("outerCount") .toString()));
			}
		}
		insertObj.setTaskTotalCount(insertObj.getInternalTaskCount()+insertObj.getOuterTaskCount());
		if(insertObj.getTaskTotalCount()>0) {
			if(insertObj.getInternalTaskCount()>0) {
				BigDecimal ratio=new BigDecimal(100).multiply(new BigDecimal(insertObj.getInternalTaskCount())).divide(new BigDecimal(insertObj.getTaskTotalCount()));
				insertObj.setInternalTaskRatio(ratio.setScale(2, BigDecimal.ROUND_UP));
			}
			if(insertObj.getOuterTaskCount()>0) {
				insertObj.setOuterTaskRatio(new BigDecimal(100).subtract(insertObj.getInternalTaskRatio()));
			}
		}
	}
	
	/**
	 * 内部外部任务链接总数
	 * 
	 * @param insertObj
	 * @param queryParam
	 */
	private void setDayReportInnerOuterTaskLinkCount(TDayReport insertObj,Map<String, Object> queryParam) {
		Map<String, Object> innerOuterLinkResultMap=baseMapper.getInnerOuterTaskLinkCount(queryParam);
		if(innerOuterLinkResultMap !=null && !innerOuterLinkResultMap.isEmpty()) {
			if(innerOuterLinkResultMap.get("innerLinkCount") !=null) {
				insertObj.setInternalTaskLinkCount(Long.valueOf(innerOuterLinkResultMap.get("innerLinkCount") .toString()));
			}
			if(innerOuterLinkResultMap.get("outerLinkCount") !=null) {
				insertObj.setOuterTaskLinkCount(Long.valueOf(innerOuterLinkResultMap.get("outerLinkCount") .toString()));
			}
		}
		insertObj.setTotalTaskLinkCount(insertObj.getInternalTaskLinkCount()+insertObj.getOuterTaskLinkCount());
		if(insertObj.getTotalTaskLinkCount()>0) {
			if(insertObj.getInternalTaskCount()>0) {
				BigDecimal ratio=new BigDecimal(100).multiply(new BigDecimal(insertObj.getInternalTaskLinkCount())).divide(new BigDecimal(insertObj.getTotalTaskLinkCount()));
				insertObj.setInternalTaskLinkRatio(ratio.setScale(2, BigDecimal.ROUND_UP));
			}
			if(insertObj.getOuterTaskLinkCount()>0) {
				insertObj.setOuterTaskLinkRatio(new BigDecimal(100).subtract(insertObj.getInternalTaskRatio()));
			}
		}
	}
	
	/**
	 * 获取单双链接数量
	 * 
	 * @param insertObj
	 * @param queryParam
	 */
	private void setDayReportSingleDoubleLinkCount(TDayReport insertObj,Map<String, Object> queryParam) {
		//获取单双连接总数
		Map<String, Object> linkResultMap=baseMapper.getSingleDoubleTaskLinkCount(queryParam);
		if(linkResultMap !=null && !linkResultMap.isEmpty()) {
			if(linkResultMap.get("doubleLinkCount") !=null) {
				insertObj.setDoubleTaskLinkCount(Long.valueOf(linkResultMap.get("doubleLinkCount") .toString()));
			}
			if(linkResultMap.get("singleLinkCount") !=null) {
				insertObj.setSingleTaskLinkCount(Long.valueOf(linkResultMap.get("singleLinkCount") .toString()));
			}
		}
		if(insertObj.getSingleTaskLinkCount()>0) {
			BigDecimal ratio=new BigDecimal(100).multiply(new BigDecimal(insertObj.getSingleTaskLinkCount())).divide(new BigDecimal(insertObj.getTotalTaskLinkCount()));
			insertObj.setSingleTaskLinkRatio(ratio.setScale(2, BigDecimal.ROUND_UP));
		}
		if(insertObj.getDoubleTaskLinkCount()>0) {
			insertObj.setActivePayMoney(new BigDecimal(100).subtract(insertObj.getSingleTaskLinkRatio()));
		}
	}
	
	/**
	 * 0佣金任务单与连接数
	 * 
	 * @param insertObj
	 * @param queryParam
	 */
	private void setDayReportZeroCommissionTaskCountAndLInkCount(TDayReport insertObj,Map<String, Object> queryParam) {
		Map<String, Object> linkResultMap=baseMapper.getZeroCommissionTaskCountAndLInkCount(queryParam);
		if(linkResultMap !=null && !linkResultMap.isEmpty()) {
			if(linkResultMap.get("zereLinkCount") !=null) {
				insertObj.setZeroCommissionTaskLinkCount(Long.valueOf(linkResultMap.get("zereLinkCount") .toString()));
			}
			if(linkResultMap.get("zereTaskCount") !=null) {
				insertObj.setZeroCommissionTaskCount(Long.valueOf(linkResultMap.get("zereTaskCount") .toString()));
			}
		}
	}
	
	
	/**
	 * 创建生成日报是的时间参数
	 * 
	 * @param sourceDate
	 * @return
	 */
	private Map<String, Object> installDayReportTimeParam(Date sourceDate){
		Map<String, Object> retMap=new HashMap<String, Object>();
		retMap.put("beginTime", DateUtils.getDateBegin(sourceDate));
		retMap.put("endTime", DateUtils.getDateEnd(sourceDate));
		return retMap;
	}
	

	@Override
	public List<TDayReport> getDayReportList(String beginDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TDayReport getAvgDayReport(String beginDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TDayReport getDayReport(String queryDate){
		return null;
	}

}
