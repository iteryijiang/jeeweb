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
}
