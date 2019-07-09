package cn.jeeweb.web.ebp.seller.mapper;

import cn.jeeweb.web.ebp.buyer.entity.TBuyerLevel;
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

	List<TSellerLevel> selectBuyerLevelPageList(Pagination page, @Param("ew") Wrapper<TSellerLevel> wrapper);

	TBuyerLevel selectBuyerLevelById(String id);

	int getBuyerLevelByName(@Param("map") Map map);
	
	int getBuyerLevelByCode(@Param("map") Map map);
	
	int updateBuyerLevel(TBuyerLevel obj);
}