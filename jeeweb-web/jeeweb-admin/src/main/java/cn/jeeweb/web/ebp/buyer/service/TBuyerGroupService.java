package cn.jeeweb.web.ebp.buyer.service;

import java.util.List;

import cn.jeeweb.web.ebp.buyer.entity.TapplyTaskBuyer;
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

	/***
	 * 分页查询分组数据
	 *
	 * @param queryable
	 * @param wrapper
	 * @return
	 */
	Page<TBuyerGroup> selectBuyerGroupPageList(Queryable queryable, Wrapper<TBuyerGroup> wrapper);
	
	/***
	 * 关键字检索买手分组
	 * 
	 * @param keyWord
	 * @return
	 */
	List<TBuyerGroup> findBuyerGroupByKeyWord(String keyWord);
	
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
	TBuyerGroup getBuyerGroupById(String id);
	
	/**
	 * 删除分组
	 * 
	 * @param id
	 */
	void deleteBuyerGroup(String id);
	
	/**
	 * 更改买手分组组长信息
	 * 
	 * @param obj
	 */
	void updateBuyerGroupForUpdateLeader(TBuyerGroup obj);


	/**
	 * 分页查询买手成员信息
	 *
	 * @param queryable
	 * @param wrapper
	 * @return
	 */
	Page<TBuyerGroupMember> selectBuyerMemberPageList(Queryable queryable, Wrapper<TBuyerGroupMember> wrapper);
}