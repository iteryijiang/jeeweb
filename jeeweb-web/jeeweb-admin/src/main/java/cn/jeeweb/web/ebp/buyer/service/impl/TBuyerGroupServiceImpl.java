package cn.jeeweb.web.ebp.buyer.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.web.ebp.buyer.entity.TapplyTaskBuyer;
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
@Service("buyerGroupService")
public class TBuyerGroupServiceImpl extends CommonServiceImpl<TBuyerGroupMapper,TBuyerGroup> implements TBuyerGroupService{


	@Override
	public Page<TBuyerGroup> selectBuyerGroupPageList(Queryable queryable, Wrapper<TBuyerGroup> wrapper){
		QueryToWrapper<TBuyerGroup> queryToWrapper = new QueryToWrapper<TBuyerGroup>();
		queryToWrapper.parseCondition(wrapper, queryable);
		queryToWrapper.parseSort(wrapper, queryable);
		Pageable pageable = queryable.getPageable();
		com.baomidou.mybatisplus.plugins.Page<TBuyerGroup> page = new com.baomidou.mybatisplus.plugins.Page<TBuyerGroup>(pageable.getPageNumber(), pageable.getPageSize());
		wrapper.eq("1", "1");
		page.setRecords(baseMapper.selectBuyerGroupPageList(page, wrapper));
		return new PageImpl<TBuyerGroup>(page.getRecords(), queryable.getPageable(), page.getTotal());
	}

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