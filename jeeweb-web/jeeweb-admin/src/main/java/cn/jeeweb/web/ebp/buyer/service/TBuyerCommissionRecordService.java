package cn.jeeweb.web.ebp.buyer.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerCommissionRecord;

/**
 * 买手佣金记录
 * 每天定时任务生成
 * 
 * @author ytj
 *
 */
public interface TBuyerCommissionRecordService extends ICommonService<TBuyerCommissionRecord> {
	
	/***
	 * 查询明细
	 * 
	 * @param queryable
	 * @param wrapper
	 * @return
	 */
	Page<TBuyerCommissionRecord> selectPageList(Queryable queryable, Wrapper<TBuyerCommissionRecord> wrapper);

	/***
	 * 获得某个月的明细数据
	 *
	 * @param month
	 * @param buyerId
	 * @return
	 */
	List<TBuyerCommissionRecord> selectListByBuyerIdMonth(int month, String buyerId);
	
	/***
	 * 查询汇总数据(按照买手编号汇总某一段时间之内的数据)
	 * 
	 * @param queryable
	 * @param wrapper
	 * @return
	 */
	Page<TBuyerCommissionRecord> selectGroupPageList(Queryable queryable, Wrapper<TBuyerCommissionRecord> wrapper);
	
	/**
	 * 生成买手佣金信息
	 * 
	 * @param sourceDate
	 */
	void addBuyerCommissionRecord(Date sourceDate);

	/**
	 * 生成买手退单的佣金
	 *
	 * @param beginDate
	 * @param endDate
	 */
	void addBuyerCommissionRecordForbackTask(Date beginDate,Date endDate);
	
	/**
	 * 退单更改买手佣金信息
	 * 三个字段：atime、taskMoney、buyerId
	 * 
	 * @param obj
	 */
	void updateBuyerCommissionRecordForBack(TBuyerCommissionRecord obj);
}
