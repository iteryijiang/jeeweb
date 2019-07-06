package cn.jeeweb.web.ebp.buyer.mapper;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerGroup;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerGroupMember;
import org.apache.ibatis.annotations.Param;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
@Mapper
public interface TBuyerGroupMapper extends BaseMapper<TBuyerGroup> {

	List<TBuyerGroup> selectBuyerGroupPageList(Pagination page, @Param("ew") Wrapper<TBuyerGroup> wrapper);
	
	List<TBuyerGroup> findBuyerGroupByKeyWord(@Param("map") Map<String,Object> map);

	int updateBuyerGroupForUpdateLeader(TBuyerGroup obj);

	int updateBuyerGroupById(TBuyerGroup obj);

}