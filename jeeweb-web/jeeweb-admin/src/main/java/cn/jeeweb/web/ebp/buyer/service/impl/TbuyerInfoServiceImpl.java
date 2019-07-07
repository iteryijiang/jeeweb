package cn.jeeweb.web.ebp.buyer.service.impl;

import cn.jeeweb.common.mybatis.mvc.parse.QueryToWrapper;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.query.data.Page;
import cn.jeeweb.common.query.data.PageImpl;
import cn.jeeweb.common.query.data.Pageable;
import cn.jeeweb.common.query.data.Queryable;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.ebp.buyer.entity.TbuyerInfo;
import cn.jeeweb.web.ebp.buyer.mapper.TbuyerInfoMapper;
import cn.jeeweb.web.ebp.buyer.service.TBuyerGroupMemberService;
import cn.jeeweb.web.ebp.buyer.service.TbuyerInfoService;
import cn.jeeweb.web.ebp.exception.MyProcessException;
import cn.jeeweb.web.modules.sys.entity.User;
import cn.jeeweb.web.modules.sys.service.IUserService;
import cn.jeeweb.web.utils.UserUtils;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.mapper.Wrapper;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service("tbuyerInfoService")
public class TbuyerInfoServiceImpl extends CommonServiceImpl<TbuyerInfoMapper, TbuyerInfo> implements TbuyerInfoService {

	@Resource(name = "buyerGroupMemberService")
	TBuyerGroupMemberService buyerGroupMemberService;
	@Resource(name = "userService")
	IUserService userService;

	@Override
	public TbuyerInfo getTbuyerInfoById(String id) {
		return baseMapper.selectBuyerInfoById(id);
	}

	@Override
	public TbuyerInfo selectBuyerInfoByUserId(String userId){
		return baseMapper.selectBuyerInfoByUserId(userId);
	}

	@Override
	public Page<TbuyerInfo> selectBuyerInfoPageList(Queryable queryable, Wrapper<TbuyerInfo> wrapper) {
		QueryToWrapper<TbuyerInfo> queryToWrapper = new QueryToWrapper<TbuyerInfo>();
		queryToWrapper.parseCondition(wrapper, queryable);
		queryToWrapper.parseSort(wrapper, queryable);
		Pageable pageable = queryable.getPageable();
		com.baomidou.mybatisplus.plugins.Page<TbuyerInfo> page = new com.baomidou.mybatisplus.plugins.Page<TbuyerInfo>(pageable.getPageNumber(), pageable.getPageSize());
		wrapper.eq("1", "1");
		page.setRecords(baseMapper.selectBuyerInfoList(page, wrapper));
		return new PageImpl<TbuyerInfo>(page.getRecords(), queryable.getPageable(), page.getTotal());
	}

	@Override
	public List<TbuyerInfo> selectBuyerInfoListByUserIdGroupId(String buyerIds,String groupId){
		Map<String,Object> paramMap=new HashMap<>();
		paramMap.put("userId",buyerIds);
		paramMap.put("groupId",groupId);
		return baseMapper.selectBuyerInfoListByUserIdGroupId(paramMap);
	}

	@Override
	public void updateTbuyerInfoById(TbuyerInfo obj) {
		TbuyerInfo objDb=getTbuyerInfoById(obj.getId());
		if(objDb == null){
			throw  new MyProcessException("操作失败[更新买手信息异常]");
		}
		//编辑买手的相关信息
		int num=baseMapper.updateBuyerInfoById(obj);
		if(num != 1){
			throw  new MyProcessException("操作失败[更新买手信息异常]");
		}
		User userObj=new User();
		userObj.setRealname(obj.getBuyername());
		userObj.setPhone(obj.getPhone());
		userObj.setId(objDb.getUserid());
		userObj.setUpdateBy(UserUtils.getUser());
		userObj.setUpdateDate(DateUtils.getCurrentTime());
		userService.updateUserInfoByUserId(userObj);
	}

	@Override
	public void updateTbuyerInfoForJoinGroup(String buyerId,String groupId){
		TbuyerInfo buyerObj=getTbuyerInfoById(buyerId);
		if(groupId.equals(buyerObj.getGroupId())){
			throw  new MyProcessException("当前买手所属分组未发生变化");
		}
		buyerGroupMemberService.updateBuyerInfoForChangeGroup(buyerObj.getUserid(),groupId);
	}

	@Override
	public void deleteBuyerGroupMember(List<String> buyerIdList, String groupId) {
		buyerGroupMemberService.updateBuyerInfoForQuitGroup(buyerIdList,groupId);
	}

	@Override
	public void addBuyerGroupMember(List<String> buyerIdList,String groupId) {
		buyerGroupMemberService.updateBuyerInfoForJoinGroup(buyerIdList,groupId);
	}

	@Override
	public void updateForSetBuyerGroupId(String buyerUserId,String newGroupId){
		Map<String,Object> paramMap=new HashMap<>();
		paramMap.put("userId",buyerUserId);
		paramMap.put("groupId",newGroupId);
		paramMap.put("updateBy", UserUtils.getUser().getId());
		paramMap.put("updateDate", DateUtils.getCurrentTime());
		int num=baseMapper.updateForSetBuyerGroupId(paramMap);
		if(num != 1){
			throw  new MyProcessException("更新买手分组信息失败");
		}
	}

	@Override
	public void updateForBatchSetBuyerGroupId(String buyerUserIds,String newGroupId){
		Map<String,Object> paramMap=new HashMap<>();
		paramMap.put("userId",buyerUserIds);
		paramMap.put("groupId",newGroupId);
		paramMap.put("updateBy",UserUtils.getUser().getId());
		paramMap.put("updateDate",DateUtils.getCurrentTime());
		int num=baseMapper.updateForBatchSetBuyerGroupId(paramMap);
		if(num != buyerUserIds.split(",").length){
			throw new MyProcessException("变更员工所属分组失败");
		}
	}

	@Override
	public void updateForDeleteBuyerGroupId(String buyerUserId,String groupId){
		Map<String,Object> updateParamMap=new HashMap<>();
		updateParamMap.put("userId",buyerUserId);
		updateParamMap.put("groupId",groupId);
		updateParamMap.put("updateBy",UserUtils.getUser().getId());
		updateParamMap.put("updateDate",DateUtils.getCurrentTime());
		int num=baseMapper.updateForDeleteBuyerGroupId(updateParamMap);
		if(num != 1){
			throw  new MyProcessException("更新买手分组信息失败[删除关联数据失败]");
		}
	}
}
