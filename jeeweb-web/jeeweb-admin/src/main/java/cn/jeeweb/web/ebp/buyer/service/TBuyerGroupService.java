package cn.jeeweb.web.ebp.buyer.service;

import java.util.List;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerGroup;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerGroupMember;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
public interface TBuyerGroupService extends ICommonService<TBuyerGroup> {
	
	
	void addBuyerGroup(TBuyerGroup obj);
	
	void updateBuyerGroupForUpdateLeader(TBuyerGroup obj);
	
	void updateBuyerGroupForUpdateMember(List<TBuyerGroupMember> objList);
}