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
	 * 分页查询佣金明细
	 *
	 * @param page
	 * @param wrapper
	 * @return
	 */
	List<TSellerCommissionReport> selectPageList(Pagination page, @Param("ew") Wrapper<TSellerCommissionReport> wrapper) ;

	/**
	 * 分页查询佣金汇总数据
	 *
	 * @param page
	 * @param wrapper
	 * @return
	 */
	List<TSellerCommissionReport> selectGroupPageList(Pagination page, @Param("ew") Wrapper<TSellerCommissionReport> wrapper) ;


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