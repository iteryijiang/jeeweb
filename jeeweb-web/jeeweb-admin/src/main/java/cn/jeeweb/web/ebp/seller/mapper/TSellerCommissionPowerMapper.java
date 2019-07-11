package cn.jeeweb.web.ebp.seller.mapper;

import cn.jeeweb.web.ebp.buyer.entity.TBuyerLevel;
import cn.jeeweb.web.ebp.seller.entity.TSellerCommissionDateRange;
import cn.jeeweb.web.ebp.seller.entity.TSellerLevel;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
@Mapper
public interface TSellerCommissionPowerMapper extends BaseMapper<TSellerLevel> {

	/**
	 * 销售等级
	 *
	 * @param page
	 * @param wrapper
	 * @return
	 */
	List<TSellerLevel> selectSellerLevelPageList(Pagination page, @Param("ew") Wrapper<TSellerLevel> wrapper);

	/**
	 * 主键 ID获取等级
	 *
	 * @param id
	 * @return
	 */
	TSellerLevel selectSellerLevelById(String id);

	/**
	 * 获取销售佣金区间信息
	 *
	 * @param page
	 * @param wrapper
	 * @return
	 */
	List<TSellerCommissionDateRange> selectSellerCommissionRangePageList(Pagination page, @Param("ew") Wrapper<TSellerCommissionDateRange> wrapper);

	/***
	 * 主键 ID获取数据
	 *
	 * @param id
	 * @return
	 */
	TSellerCommissionDateRange selectSellerCommissionRangeById(String id);

	/**
	 * 编辑更改佣金信息
	 *
	 * @param obj
	 * @return
	 */
	int updateSellerCommissionRangeById(TSellerCommissionDateRange obj);

	/**
	 * 新增数据
	 *
	 * @param obj
	 * @return
	 */
	int insertSellerCommissionRange(TSellerCommissionDateRange obj);

	/**
	 * 判断区间是否发生重复冲突
	 *
	 * @param map
	 * @return
	 */
	int getSellerCommissionRangCount(@Param("map") Map map);
}