package cn.jeeweb.web.ebp.buyer.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.buyer.entity.TbuyerInfo;

import java.util.List;

public interface TbuyerInfoService extends ICommonService<TbuyerInfo> {

	/**
	 * 主键ID查询买手信息
	 * 
	 * @param id
	 * @return
	 */
	TbuyerInfo getTbuyerInfoById(String id);

	/**
	 * 根据用户id查询数据
	 *
	 * @param userId
	 * @return
	 */
	TbuyerInfo selectBuyerInfoByUserId(String userId);
	
	/**
	 * 分页查询买手信息
	 * 
	 * @param queryable
	 * @param wrapper
	 * @return
	 */
	 Page<TbuyerInfo> selectBuyerInfoPageList(Queryable queryable, Wrapper<TbuyerInfo> wrapper);

	/***
	 * 根据买手用户id+分组ID获取买手信息
	 *
	 * @param buyerIds
	 * @param groupId
	 * @return
	 */
	List<TbuyerInfo> selectBuyerInfoListByUserIdGroupId(String buyerIds,String groupId);

	/**
	 * 编辑买手信息
	 * 
	 * @param obj
	 */
	void updateTbuyerInfoById(TbuyerInfo obj);

	/**
	 * 买手加入某一个分组
	 *
	 * @param buyerId
	 * @param groupId
	 */
	void updateTbuyerInfoForJoinGroup(String buyerId,String groupId);

	/**
	 * 删除买手成员信息
	 *
	 * @param buyerIdList
	 * @param groupId
	 */
	void deleteBuyerGroupMember(List<String> buyerIdList, String groupId);

	/**
	 * 新增买手成员信息
	 *
	 * @param buyerIdList
	 * @param groupId
	 */
	void addBuyerGroupMember(List<String> buyerIdList,String groupId);

	/**
	 * 更改买手对应的分组信息
	 *
	 * @param buyerUserId
	 * @param newGroupId
	 */
	void updateForSetBuyerGroupId(String buyerUserId,String newGroupId);

	/**
	 * 批量更改买手记录对应的分组信息
	 *
	 * @param buyerUserIds
	 * @param newGroupId
	 */
	void updateForBatchSetBuyerGroupId(String buyerUserIds,String newGroupId);

	/**
	 * 买手ID+分组ID=>清空买手分组信息
	 *
	 * @param buyerUserId
	 * @param groupId
	 */
	void updateForDeleteBuyerGroupId(String buyerUserId,String groupId);
}
