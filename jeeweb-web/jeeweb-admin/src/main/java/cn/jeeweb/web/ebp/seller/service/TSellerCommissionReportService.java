package cn.jeeweb.web.ebp.seller.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerLevel;
import cn.jeeweb.web.ebp.seller.entity.TSellerCommissionReport;
import cn.jeeweb.web.ebp.seller.entity.TSellerCommissionReportDetail;
import com.baomidou.mybatisplus.mapper.Wrapper;

import java.util.Date;
import java.util.List;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
public interface TSellerCommissionReportService extends ICommonService<TSellerCommissionReport> {

	/**
	 * 列表查询
	 *
	 * @param queryable
	 * @param wrapper
	 * @return
	 */
	Page<TSellerCommissionReport> selectSellerCommissionReportPageList(Queryable queryable, Wrapper<TSellerCommissionReport> wrapper);


	/***
	 * 查询汇总数据(按照销售编号汇总某一段时间之内的数据)
	 *
	 * @param queryable
	 * @param wrapper
	 * @return
	 */
	Page<TSellerCommissionReport> selectGroupPageList(Queryable queryable, Wrapper<TSellerCommissionReport> wrapper);

	/**
	 * 定时任务生成
	 *
	 * @param sourceDate
	 */
	void insertSellerCommissionReportForCreate(Date sourceDate);

	/**
	 * 生成时间段内退单的佣金
	 *
	 * @param beginDate
	 * @param endDate
	 */
	void insertSellerCommissionReportForBackTask(Date beginDate,Date endDate);

	/**
	 * 查询明细
	 *
	 * @param seller
	 * @param atime
	 * @param dateRange
	 * @return
	 */
	List<TSellerCommissionReportDetail> selectSellerCommissionReportDetailList(String seller,Date atime,String dateRange);

}