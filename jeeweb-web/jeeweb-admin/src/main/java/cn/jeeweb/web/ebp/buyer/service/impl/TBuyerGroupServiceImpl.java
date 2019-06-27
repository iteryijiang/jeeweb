package cn.jeeweb.web.ebp.buyer.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerGroup;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerGroupMember;
import cn.jeeweb.web.ebp.buyer.mapper.TBuyerGroupMapper;
import cn.jeeweb.web.ebp.buyer.service.TBuyerGroupService;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
@Transactional
@Service("BuyerGroupService")
public class TBuyerGroupServiceImpl extends CommonServiceImpl<TBuyerGroupMapper,TBuyerGroup> implements TBuyerGroupService{

	@Override
	public void addBuyerGroup(TBuyerGroup obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBuyerGroupForUpdateLeader(TBuyerGroup obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBuyerGroupForUpdateMember(List<TBuyerGroupMember> objList) {
		// TODO Auto-generated method stub
		
	}
}