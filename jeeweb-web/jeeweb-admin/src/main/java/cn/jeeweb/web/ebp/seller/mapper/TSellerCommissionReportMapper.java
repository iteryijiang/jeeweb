package cn.jeeweb.web.ebp.seller.mapper;

import cn.jeeweb.web.ebp.buyer.entity.TBuyerLevel;
import cn.jeeweb.web.ebp.seller.entity.TSellerCommissionReport;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 销售佣金报表
 * 
 * @author ytj
 *
 */
@Mapper
public interface TSellerCommissionReportMapper extends BaseMapper<TSellerCommissionReport> {

	/**
	 * 清空临时表
	 *
	 * @return
	 */
	int updateSellerCommissionDetailTempForTruncate();

	/**
	 * 生成佣金报表明细临时表
	 *
	 * @param map
	 * @return
	 */
	int insertSellerCommissionDetailTemp(@Param("map") Map map);

	/**
	 * 生成佣金报表明细
	 *
	 * @param map
	 * @return
	 */
	int insertSellerCommissionDetail(@Param("map") Map map);

	/**
	 * 生成佣金总表
	 *
	 * @param map
	 * @return
	 */
	int insertSellerCommission(@Param("map") Map map);

}