package cn.jeeweb.web.ebp.buyer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.Wrapper;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.Queryable;
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
		int num=baseMapper.insert(obj);
		if(num !=1) {
			throw new RuntimeException("保存数据失败");
		}
	}
	
	@Override
	public void deleteBuyerGroup(long id,String lastRepair) {
		//判断买手分组下手否含有成员
		int count=getBuyerGroupMemberCountByGroupId(id);
		if(count>0) {
			throw new RuntimeException("当前买手分组下存在成员,不允许删除");
		}
		baseMapper.deleteById(id);
	}
	
	private int getBuyerGroupMemberCountByGroupId(long buyerGroupId) {
		return getBuyerGroupMemberCountByGroupId(buyerGroupId);
	}

	@Override
	public void updateBuyerGroupForUpdateLeader(TBuyerGroup obj) {
		int retObj=baseMapper.updateBuyerGroupForUpdateLeader(obj);
		if(retObj!=1){
			throw new RuntimeException("保存数据失败");
		}
	}

	@Override
	public void updateBuyerGroup(TBuyerGroup obj) {
		int retObj=baseMapper.updateById(obj);
		if(retObj!=1){
			throw new RuntimeException("保存数据失败");
		}
	}

	@Override
	public TBuyerGroup getBuyerGroupById(long id) {
		TBuyerGroup retObj=baseMapper.selectById(id);
		return retObj;
	}

	@Override
	public void deleteBuyerGroupMember(String buyerMemberIds) {
		List<Long> ids=new ArrayList<Long>();
		for(String id:buyerMemberIds.split(",")) {
			if(StringUtils.isNoneBlank(id)) {
				ids.add(Long.valueOf(id));
			}
		}
		baseMapper.deleteBatchIds(ids);
	}

	@Override
	public void addBuyerGroupMember(List<TBuyerGroupMember> objList) {
		baseMapper.addBuyerGroupMember(objList);
	}

	@Override
	public Page<TBuyerGroupMember> selectApplyPageList(Queryable queryable, Wrapper<TBuyerGroupMember> wrapper) {
		// TODO Auto-generated method stub
		return null;
	}
}