package cn.jeeweb.web.ebp.buyer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.web.ebp.buyer.entity.TbuyerInfo;
import cn.jeeweb.web.ebp.buyer.mapper.TbuyerInfoMapper;
import cn.jeeweb.web.ebp.buyer.service.TBuyerGroupMemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.annotation.Resource;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
@Transactional
@Service("buyerGroupService")
public class TBuyerGroupServiceImpl extends CommonServiceImpl<TBuyerGroupMapper, TBuyerGroup>implements TBuyerGroupService {

	@Resource(name = "buyerGroupMemberService")
	TBuyerGroupMemberService buyerGroupMemberService;
	@Autowired
	TbuyerInfoMapper buyerInfoMapper;

	@Override
	public Page<TBuyerGroup> selectBuyerGroupPageList(Queryable queryable, Wrapper<TBuyerGroup> wrapper) {
		QueryToWrapper<TBuyerGroup> queryToWrapper = new QueryToWrapper<TBuyerGroup>();
		queryToWrapper.parseCondition(wrapper, queryable);
		queryToWrapper.parseSort(wrapper, queryable);
		Pageable pageable = queryable.getPageable();
		com.baomidou.mybatisplus.plugins.Page<TBuyerGroup> page = new com.baomidou.mybatisplus.plugins.Page<TBuyerGroup>(
				pageable.getPageNumber(), pageable.getPageSize());
		wrapper.eq("1", "1");
		page.setRecords(baseMapper.selectBuyerGroupPageList(page, wrapper));
		return new PageImpl<TBuyerGroup>(page.getRecords(), queryable.getPageable(), page.getTotal());
	}

	@Override
	public List<TBuyerGroup> findBuyerGroupByKeyWord(String keyWord) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("keyWord", keyWord);
		return baseMapper.findBuyerGroupByKeyWord(paramMap);
	}

	@Override
	public void addBuyerGroup(TBuyerGroup obj) {
		int num = baseMapper.insert(obj);
		if (num != 1) {
			throw new RuntimeException("保存数据失败");
		}
	}

	@Override
	public void deleteBuyerGroup(String id) {
		// 判断买手分组下手否含有成员
		int count = getBuyerGroupMemberCountByGroupId(id);
		if (count > 0) {
			throw new RuntimeException("当前买手分组下存在成员,不允许删除");
		}
		baseMapper.deleteById(id);
	}

	private int getBuyerGroupMemberCountByGroupId(String buyerGroupId) {
		return getBuyerGroupMemberCountByGroupId(buyerGroupId);
	}

	@Override
	public void updateBuyerGroupForUpdateLeader(TBuyerGroup obj) {
		TbuyerInfo buyerObj=buyerInfoMapper.selectBuyerInfoById(obj.getGroupLeader());
		if(buyerObj == null){
			throw new RuntimeException("保存数据失败[为获取到买手信息]");
		}
		obj.setGroupLeader(buyerObj.getUserid());
		obj.setGroupLeaderName(buyerObj.getBuyername());
		int retObj = baseMapper.updateBuyerGroupForUpdateLeader(obj);
		if (retObj != 1) {
			throw new RuntimeException("保存数据失败");
		}
		//调整组员中间表数据
		buyerGroupMemberService.updateMemberPositionForLeader(obj.getGroupLeader(),obj.getId());
	}

	@Override
	public void updateBuyerGroup(TBuyerGroup obj) {
		TbuyerInfo buyerObj=buyerInfoMapper.selectBuyerInfoByUserId(obj.getGroupLeader());
		obj.setGroupLeaderName(buyerObj.getBuyername());
		int retObj = baseMapper.updateBuyerGroupById(obj);
		if (retObj != 1) {
			throw new RuntimeException("保存数据失败");
		}
	}

	@Override
	public TBuyerGroup getBuyerGroupById(String id) {
		TBuyerGroup retObj = baseMapper.selectById(id);
		return retObj;
	}

	@Override
	public Page<TBuyerGroupMember> selectBuyerMemberPageList(Queryable queryable, Wrapper<TBuyerGroupMember> wrapper) {
		// TODO Auto-generated method stub
		return null;
	}
}