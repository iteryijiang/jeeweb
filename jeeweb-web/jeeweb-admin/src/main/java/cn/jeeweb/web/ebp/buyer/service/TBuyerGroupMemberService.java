package cn.jeeweb.web.ebp.buyer.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerGroup;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerGroupMember;
import cn.jeeweb.web.ebp.enums.BuyerMemberPositionEnum;
import com.baomidou.mybatisplus.mapper.Wrapper;

import java.util.List;

/**
 * 买手分组与买手之间的关系
 * 
 * @author ytj
 *
 */
public interface TBuyerGroupMemberService extends ICommonService<TBuyerGroupMember> {

	/**
	 * 根据分组编号获取分组下的成员信息
	 *
	 * @param groupId
	 * @return
	 */
	long getMemberCountByGroupId(String groupId);

	/**
	 * 更改买手所属的分组信息
	 *
	 * @param buyerId
	 * @param newGroupId
	 */
	void updateBuyerInfoForChangeGroup(String buyerUserId,String newGroupId);

	/**
	 * 买手加入分组
	 *
	 * @param buyerIdList
	 * @param newGroupId
	 */
	void updateBuyerInfoForJoinGroup(List<String> buyerIdList,String newGroupId);

	/***
	 * 买手退出分组
	 *
	 * @param buyerIdList
	 * @param groupId
	 */
	void updateBuyerInfoForQuitGroup(List<String> buyerIdList,String groupId);

	/**
	 * 更新组员的职位信息
	 * 职位变更为组长
	 *
	 * @param buyerId
	 * @param groupId
	 */
	void updateMemberPositionForLeader(String buyerId, String groupId);
}