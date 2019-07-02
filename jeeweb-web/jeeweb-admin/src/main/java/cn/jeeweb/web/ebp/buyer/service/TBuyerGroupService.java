package cn.jeeweb.web.ebp.buyer.service;

import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerGroup;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerGroupMember;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
public interface TBuyerGroupService extends ICommonService<TBuyerGroup> {
	
	/**
	 * 新增买手分组信息
	 * 
	 * @param obj
	 */
	void addBuyerGroup(TBuyerGroup obj);
	
	/***
	 * 编辑买手分组信息
	 * 
	 * @param obj
	 */
	void updateBuyerGroup(TBuyerGroup obj);
	
	/**
	 * 查询买手分组信息
	 * 
	 * @param id
	 * @return
	 */
	TBuyerGroup getBuyerGroupById(long id);
	
	/**
	 * 删除分组
	 * 
	 * @param id
	 * @param lastRepair
	 */
	void deleteBuyerGroup(long id,String lastRepair);
	
	/**
	 * 更改买手分组组长信息
	 * 
	 * @param obj
	 */
	void updateBuyerGroupForUpdateLeader(TBuyerGroup obj);
	
	/**
	 * 删除买手成员信息
	 * 
	 * @param buyerMemberIds
	 */
	void deleteBuyerGroupMember(String buyerMemberIds);
	
	/**
	 * 新增买手成员信息
	 * 
	 * @param objList
	 */
	void addBuyerGroupMember(List<TBuyerGroupMember> objList);
	
	/**
	 * 分页查询买手成员信息
	 * 
	 * @param queryable
	 * @param wrapper
	 * @return
	 */
	Page<TBuyerGroupMember> selectApplyPageList(Queryable queryable, Wrapper<TBuyerGroupMember> wrapper);
}