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
	 * 生成佣金报表明细
	 *
	 * @param map
	 * @return
	 */
	int insertSellerCommissionReportDetail(@Param("map") Map map);

	/**
	 * 生成佣金总表
	 *
	 * @param map
	 * @return
	 */
	int insertSellerCommissionReport(@Param("map") Map map);
}