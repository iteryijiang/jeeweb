package cn.jeeweb.web.ebp.buyer.mapper;

import cn.jeeweb.web.ebp.buyer.entity.TBuyerGroupMember;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
@Mapper
public interface TBuyerGroupMemberMapper extends BaseMapper<TBuyerGroupMember> {


	/**
	 * 获取分组下的成员信息
	 *
	 * @param groupId
	 * @return
	 */
	long getMemberCountByGroupId(String groupId);

	/**
	 * 根据分组+买手ID获取记录信息
	 *
	 * @param map
	 * @return
	 */
	TBuyerGroupMember getMemberByBuyerIdGroupId(@Param("map") Map<String,Object> map);

	/**
	 * 根据买手编号+分组编号删除买手与分组的额关系数据
	 *
	 * @param delObj
	 * @return
	 */
	int deleteBuyerGroupMemberByBuyerIdGroupId(TBuyerGroupMember delObj);

	/**
	 * 买手职位变更
	 *
	 * @param map
	 * @return
	 */
	int updateMemberPosition(@Param("map") Map<String,Object> map);

}