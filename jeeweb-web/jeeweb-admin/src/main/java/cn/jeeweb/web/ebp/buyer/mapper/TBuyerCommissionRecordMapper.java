package cn.jeeweb.web.ebp.buyer.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerCommissionRecord;

/**
 * 买手佣金记录
 * 每天定时任务生成
 * 
 * @author ytj
 *
 */
@Mapper
public interface TBuyerCommissionRecordMapper extends BaseMapper<TBuyerCommissionRecord> {
	
	/**
	 * 分页查询佣金明细
	 * 
	 * @param page
	 * @param wrapper
	 * @return
	 */
	List<TBuyerCommissionRecord> selectPageList(Pagination page, @Param("ew") Wrapper<TBuyerCommissionRecord> wrapper) ;

	/**
	 * 分页查询佣金汇总数据
	 * 
	 * @param page
	 * @param wrapper
	 * @return
	 */
	List<TBuyerCommissionRecord> selectGroupPageList(Pagination page, @Param("ew") Wrapper<TBuyerCommissionRecord> wrapper) ;

	
	/***
	 * 根据买手ID+账期获取对应的买手佣金信息
	 * 
	 * @param map
	 * @return
	 */
	TBuyerCommissionRecord getTBuyerCommissionRecordByBuyerIdAtime(@Param("map") Map<String, Object> map);

	/**
	 * 获得某个月份的额明细数据
	 *
	 * @param map
	 * @return
	 */
	List<TBuyerCommissionRecord> getTBuyerCommissionRecordByBuyerIdMonth(@Param("map") Map<String, Object> map);

	/**
	 * 添加买手佣金信息
	 *
	 * @param map
	 * @return
	 */
	int insertBuyerCommissionInfo(@Param("map") Map<String, Object> map);

	/**
	 * 添加买手任务金
	 *
	 * @param map
	 * @return
	 */
	int insertBuyerCommissionTaskNumTemp(@Param("map") Map<String, Object> map);

	/**
	 * 添加分组提成佣金
	 *
	 * @param map
	 * @return
	 */
	int insertBuyerCommissionGroupTemp(@Param("map") Map<String, Object> map);

	/**
	 *
	 * 更改佣金表分组数据
	 *
	 * @param map
	 * @return
	 */
	int updateBuyerGroupMoney(@Param("map") Map<String, Object> map);

	/**
	 * 更改佣金表任务数据
	 *
	 * @param map
	 * @return
	 */
	int updateBuyerTaskNum(@Param("map") Map<String, Object> map);

	/**
	 * 清空临时表数据
	 * 任务数据
	 *
	 * @return
	 */
	int updateBuyerCommissionTaskForTruncateTemp();

	/**
	 * 清空临时表
	 * 分组数据
	 *
	 * @return
	 */
	int updateBuyerCommissionGroupForTruncateTemp();

	/**
	 * 更改佣金信息
	 * 退单操作
	 *
	 * @param obj
	 * @return
	 */
	int updateBuyCommissionForBack(TBuyerCommissionRecord obj);

}
