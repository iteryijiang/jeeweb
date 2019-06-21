package cn.jeeweb.web.ebp.report.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import cn.jeeweb.web.ebp.report.entity.TDayReport;

@Mapper
public interface DayReportMapper extends BaseMapper<TDayReport> {

	/**
	 * 获取内部外部的任务单总数
	 * 
	 * @param paramMap
	 * @return
	 *  key:innerCount、outerCount
	 */
	Map<String, Object> getInnerOuterTaskCount(Map<String, Object> paramMap);
	
	/**
	 * 获取内部外部的任务单总数
	 * 
	 * @param paramMap
	 * @return
	 * key:innerLinkCount、outerLinkCount
	 */
	Map<String, Object> getInnerOuterTaskLinkCount(Map<String, Object> paramMap);
 
	/***
	 *  获取0佣金任务单+连接总数
	 * 
	 * 
	 * @param paramMap
	 * @return
	 * key：zereLinkCount、zereTaskCount
	 */
	Map<String, Object> getZeroCommissionTaskCountAndLInkCount(Map<String, Object> paramMap);


	/***
	 * 获取单链接双链接总数总数
	 * 
	 * @param paramMap
	 * @return
	 * key：doubleLinkCount、singleLinkCount
	 */
	Map<String, Object> getSingleDoubleTaskLinkCount(Map<String, Object> paramMap);
	
	/**
	 * 获取商户支付的押金总数
	 * 
	 * @param paramMap
	 * @return
	 */
	BigDecimal getTotalRechargeDeposit(Map<String, Object> paramMap);

	/**
	 * 获取当前所有商户的可用押金汇总
	 *
	 * @return
	 */
	BigDecimal getTotalAvailableDeposit();


	/**
	 * 管理员撤销的任务单以及连接数
	 *
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getProblemTaskCountLinkCountByAdminHandle(Map<String, Object> paramMap);

	/**
	 * 获取冻结金额+佣金+实际支付金额
	 *
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getTotalForeezeCommissionActPay(Map<String, Object> paramMap);


	/**
	 * 获根据账期获取唯一的一条日报数据
	 * 
	 * @param queryAtime
	 * @return
	 */
	TDayReport getTDayReportByAtime(String queryAtime);
	
	/***
	 * 获取两个日期之间的所有日报数据列表
	 * 
	 * @param paramMap
	 * @return
	 */
	List<TDayReport> getTDayReportListByAtime(Map<String, Object> paramMap);
	
	/***
	 * 获取两个日期之间的所有日报数据
	 * 汇总之后的数据
	 * 
	 * @param paramMap
	 * @return
	 */
	TDayReport getTDayReportForSumByAtime(Map<String, Object> paramMap);

}
