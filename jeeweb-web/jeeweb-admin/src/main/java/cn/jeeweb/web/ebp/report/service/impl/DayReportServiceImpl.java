package cn.jeeweb.web.ebp.report.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
		insertObj.setDtime(DateUtils.getCurrentTime());
		Map<String, Object> queryParam= installDayReportTimeParam(insertObj.getAtime());
		//期初期末余额
		setDayReportBeginEndBalance(insertObj,queryParam);
		//本期充值
		setDayReportTotalRechargeDeposit(insertObj, queryParam);
		//内部任务+外部任务
		setDayReportInnerOuterTaskCount(insertObj, queryParam);
		//内部链接+外部链接
		setDayReportInnerOuterTaskLinkCount(insertObj, queryParam);
		//单链接+双链接
		setDayReportSingleDoubleLinkCount(insertObj,queryParam);
		//0佣金任务单+0佣金链接
		setDayReportZeroCommissionTaskCountAndLInkCount(insertObj, queryParam);
		//问题单数量+问题单链接数
		setDayReportProblemTaskCountAndLInkCount(insertObj, queryParam);
		//冻结金额+佣金+当天实际支付金额
		getTotalForeezeCommissionActPay(insertObj, queryParam);
		User createBy=new User();
		createBy.setUsername("sys_schedule");
		createBy.setId("sys_schedule");
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
		//期末余额(当前商户所有的可用押金汇总)
		BigDecimal totalAvaiableDeposit=baseMapper.getTotalAvailableDeposit();
		insertObj.setEndingBalance(totalAvaiableDeposit);
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
				BigDecimal ratio=new BigDecimal(100).multiply(new BigDecimal(insertObj.getInternalTaskCount())).divide(new BigDecimal(insertObj.getTaskTotalCount()),2, RoundingMode.HALF_UP);
				insertObj.setInternalTaskRatio(ratio);
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
				BigDecimal ratio=new BigDecimal(100).multiply(new BigDecimal(insertObj.getInternalTaskLinkCount())).divide(new BigDecimal(insertObj.getTotalTaskLinkCount()),2, RoundingMode.HALF_UP);
				insertObj.setInternalTaskLinkRatio(ratio);
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
			BigDecimal ratio=new BigDecimal(100).multiply(new BigDecimal(insertObj.getSingleTaskLinkCount())).divide(new BigDecimal(insertObj.getTotalTaskLinkCount()),2, RoundingMode.HALF_UP);
			insertObj.setSingleTaskLinkRatio(ratio);
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
	 * 问题单数量
	 *
	 * @param insertObj
	 * @param queryParam
	 */
	private void setDayReportProblemTaskCountAndLInkCount(TDayReport insertObj,Map<String, Object> queryParam) {
		//admin处理的问题单+问题申请链接数量
		Map<String, Object> linkResultMap=baseMapper.getProblemTaskCountLinkCountByAdminHandle(queryParam);
		if(linkResultMap !=null && !linkResultMap.isEmpty()) {
			if(linkResultMap.get("problemTaskCount") !=null) {
				insertObj.setProblemTaskCount(Long.valueOf(linkResultMap.get("problemTaskCount").toString()));
			}
			if(linkResultMap.get("problemTaskLinkCount") !=null) {
				insertObj.setProblemTaskLinkCount(Long.valueOf(linkResultMap.get("problemTaskLinkCount").toString()));
			}
		}
	}

	/**
	 * 获取三个数据：冻结金额+当天实际佣金+当天任务实际支付金额
	 *
	 * @param insertObj
	 * @param queryParam
	 */
	private void getTotalForeezeCommissionActPay(TDayReport insertObj,Map<String, Object> queryParam) {
		//admin处理的问题单+问题申请链接数量
		List<Map<String, Object>> resultMapList=baseMapper.getTotalForeezeCommissionActPay(queryParam);
		if(resultMapList != null && !resultMapList.isEmpty()){
			for(Map<String, Object> mapTemp:resultMapList){
				String dataType=mapTemp.get("dataType").toString();
				BigDecimal dataTypeValue=new BigDecimal(mapTemp.get("totalMoney").toString());
				switch (dataType){
					case "todayFreezing"://冻结金额
						insertObj.setTodayFreezing(dataTypeValue);
						break;
					case "todayCommission"://当天佣金
						insertObj.setTodayCommission(dataTypeValue);
						break;
					case "activePayMoney"://任务实际支付金额
						insertObj.setActivePayMoney(dataTypeValue);
						break;
				}
			}
		}
	}

	/**
	 * 创建生成日报的查询时间参数
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
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate",endDate);
		return baseMapper.getTDayReportListByAtime(paramMap);
	}

	@Override
	public TDayReport getAvgDayReport(String beginDate, String endDate) {
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate",endDate);
		TDayReport sumObj=baseMapper.getTDayReportForSumByAtime(paramMap);
		if(sumObj == null) {
			return new TDayReport();
		}
		//计算平均值数据
		//单链接占比、双链接占比、内部链接占比、外部任务链接占比、内部任务占比、外部任务占比
		BigDecimal hundred=new BigDecimal(100);
		if(sumObj.getTotalTaskLinkCount()>0) {
			BigDecimal singleLinkRatio=hundred.multiply(new BigDecimal(sumObj.getSingleTaskLinkCount())).divide(new BigDecimal(sumObj.getTotalTaskLinkCount()),2, RoundingMode.HALF_UP);
			sumObj.setSingleTaskLinkRatio(singleLinkRatio);
			sumObj.setDoubleTaskLinkRatio(hundred.subtract(sumObj.getSingleTaskLinkRatio()));
			BigDecimal internalTaskLinkRatio=hundred.multiply(new BigDecimal(sumObj.getInternalTaskLinkCount())).divide(new BigDecimal(sumObj.getTotalTaskLinkCount()),2, RoundingMode.HALF_UP);
			sumObj.setInternalTaskLinkRatio(internalTaskLinkRatio);
			sumObj.setOuterTaskLinkRatio(hundred.subtract(sumObj.getInternalTaskLinkRatio()));
		}
		if(sumObj.getTaskTotalCount()>0) {
			BigDecimal internalTaskRatio=hundred.multiply(new BigDecimal(sumObj.getInternalTaskCount())).divide(new BigDecimal(sumObj.getTaskTotalCount()),2, RoundingMode.HALF_UP);
			sumObj.setInternalTaskRatio(internalTaskRatio);
			sumObj.setOuterTaskRatio(hundred.subtract(sumObj.getInternalTaskRatio()));
		}
		return sumObj;
	}
	
	@Override
	public TDayReport getDayReport(String queryDate){
		return baseMapper.getTDayReportByAtime(queryDate);
	}

	public static void main(String[] args) throws Exception {
		BigDecimal number1=new BigDecimal(11);
		BigDecimal number2=new BigDecimal(3);
		System.out.println(number1.divide(number2,2, RoundingMode.HALF_UP));
	}

}
