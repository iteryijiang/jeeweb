package cn.jeeweb.web.ebp.buyer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerGroup;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerGroupMember;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
@Mapper
public interface TBuyerGroupMapper extends BaseMapper<TBuyerGroup> {
	
	int updateBuyerGroupForUpdateLeader(TBuyerGroup obj);
	
	int getBuyerGroupMemberCountByGroupId(long buyerGroupId);
	
	int addBuyerGroupMember(List<TBuyerGroupMember> objList);
}