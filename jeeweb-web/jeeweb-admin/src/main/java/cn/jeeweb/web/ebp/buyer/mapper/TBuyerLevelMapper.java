package cn.jeeweb.web.ebp.buyer.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerLevel;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
@Mapper
public interface TBuyerLevelMapper extends BaseMapper<TBuyerLevel> {
	
	int getBuyerLevelByName(@Param("map") Map map);
	
	int getBuyerLevelByCode(@Param("map") Map map);
	
	int updateBuyerLevel(TBuyerLevel obj);
}