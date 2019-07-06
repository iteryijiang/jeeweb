package cn.jeeweb.web.ebp.buyer.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerGroupMember;
import cn.jeeweb.web.ebp.buyer.entity.TbuyerInfo;
import cn.jeeweb.web.ebp.buyer.mapper.TBuyerGroupMemberMapper;
import cn.jeeweb.web.ebp.buyer.mapper.TbuyerInfoMapper;
import cn.jeeweb.web.ebp.buyer.service.TBuyerGroupMemberService;
import cn.jeeweb.web.ebp.buyer.service.TbuyerInfoService;
import cn.jeeweb.web.ebp.enums.BuyerMemberPositionEnum;
import cn.jeeweb.web.ebp.exception.MyProcessException;
import cn.jeeweb.web.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
@Transactional
@Service("buyerGroupMemberService")
public class TBuyerGroupMemberServiceImpl extends CommonServiceImpl<TBuyerGroupMemberMapper, TBuyerGroupMember>implements TBuyerGroupMemberService {

	@Resource(name = "tbuyerInfoService")
	TbuyerInfoService tbuyerInfoService;


	@Override
	public long getMemberCountByGroupId(String groupId){
		return baseMapper.getMemberCountByGroupId(groupId);
	}

	@Override
	public void updateBuyerInfoForChangeGroup(String buyerUserId,String newGroupId) {
		//获取买手分组信息
		TbuyerInfo buyObj=tbuyerInfoService.selectBuyerInfoByUserId(buyerUserId);
		if(buyObj == null){
			throw  new MyProcessException("保存买手分组信息失败[未匹配上对应的买手信息]");
		}
		//移除原有的分组
		if(StringUtils.isNotBlank(buyObj.getGroupId())){
			updateForQuit(buyObj);
		}
		//添加新的分组
		buyObj.setGroupId(newGroupId);
		TBuyerGroupMember insertObj=initBuyerGroupMember(buyObj);
		int num=baseMapper.insert(insertObj);
		if(num != 1){
			throw  new MyProcessException("更新买手分组信息失败");
		}
		//更改买手对应的分组信息
		tbuyerInfoService.updateForSetBuyerGroupId(buyerUserId,newGroupId);

	}

	/**
	 * 清除买手分组信息
	 *
	 * @param buyObj
	 */
	private void updateForQuit(TbuyerInfo buyObj){
		TBuyerGroupMember delObj=new TBuyerGroupMember();
		delObj.setBuyerId(buyObj.getUserid());
		delObj.setGroupId(buyObj.getGroupId());
		delObj.setQuitTime(DateUtils.getCurrentTime());
		delObj.setBuyerLevelOnQuit(buyObj.getAccountlevel());
		delObj.setBuyerLevelNameOnQuit(buyObj.getLevelName());
		delObj.setQuitCreateBy(UserUtils.getUser().getId());
		delObj.setQuitCreateName(UserUtils.getUser().getUsername());
		int num=baseMapper.deleteBuyerGroupMemberByBuyerIdGroupId(delObj);
		if(num != 1){
			throw  new MyProcessException("更新买手分组信息失败[删除关联数据失败]");
		}
		//更改买手对应的分组信息
		tbuyerInfoService.updateForDeleteBuyerGroupId(buyObj.getUserid(),buyObj.getGroupId());
	}

	/**
	 * 初始化买手分组关系信息
	 *
	 * @param buyObj
	 * @return
	 */
	private TBuyerGroupMember initBuyerGroupMember(TbuyerInfo buyObj){
		TBuyerGroupMember insertObj=new TBuyerGroupMember();
		insertObj.setBuyerId(buyObj.getUserid());
		insertObj.setGroupId(buyObj.getGroupId());
		insertObj.setMemType(BuyerMemberPositionEnum.MEMBER.code);
		insertObj.setJoinTime(DateUtils.getCurrentTime());
		insertObj.setBuyerLevelOnJoin(buyObj.getAccountlevel());
		insertObj.setBuyerLevelNameOnJoin(buyObj.getLevelName());
		insertObj.setJoinCreateBy(UserUtils.getUser().getId());
		insertObj.setJoinCreateName(UserUtils.getUser().getUsername());
		return insertObj;
	}

	@Override
	public void updateBuyerInfoForJoinGroup(List<String> buyerIdList,String newGroupId){
		List<TBuyerGroupMember> insertList=new ArrayList<>();
		List<TbuyerInfo> batchDelBuyerList=new ArrayList<>();
		StringBuffer buyerIds=new StringBuffer("");
		for(String buyerId:buyerIdList){
			//获取买手分组信息
			TbuyerInfo buyObj=tbuyerInfoService.getTbuyerInfoById(buyerId);
			if(buyObj !=null && !newGroupId.equals(buyObj.getGroupId())){
				if(StringUtils.isNotBlank(buyObj.getGroupId())){
					batchDelBuyerList.add(buyObj);
				}
				buyObj.setGroupId(newGroupId);
				insertList.add(initBuyerGroupMember(buyObj));
				buyerIds.append(",'").append(buyObj.getUserid()).append("'");
			}
		}
		if(insertList.isEmpty()){
			throw  new MyProcessException("保存买手分组信息失败[买手分组信息未发生变更]");
		}
		//清理买手原有分组信息
		for(TbuyerInfo delBuyerObj:batchDelBuyerList){
			updateForQuit(delBuyerObj);
		}
		//设置新的分组信息
		insertBatch(insertList);
		tbuyerInfoService.updateForBatchSetBuyerGroupId(buyerIds.substring(1),newGroupId);
	}

	@Override
	public void updateBuyerInfoForQuitGroup(List<String> buyerIdList,String groupId){
		String buyerIds = String.join(",", buyerIdList);
		buyerIds="'"+buyerIds.replaceAll(",","','")+"'";
		List<TbuyerInfo> buyerList= tbuyerInfoService.selectBuyerInfoListByUserIdGroupId(buyerIds,groupId);
		if(buyerList.isEmpty()){
			throw  new MyProcessException("保存买手分组信息失败[未匹配上对应的买手信息]");
		}
		//清理买手原有分组信息
		for(TbuyerInfo delBuyerObj:buyerList){
			updateForQuit(delBuyerObj);
		}
	}

	@Override
	public void updateMemberPositionForLeader(String buyerId, String groupId){
		Map<String,Object> paramMap=new HashMap<>();
		paramMap.put("buyerId",buyerId);
		paramMap.put("groupId",groupId);
		TBuyerGroupMember objDb=baseMapper.getMemberByBuyerIdGroupId(paramMap);
		paramMap.put("updateBy",UserUtils.getUser().getUsername());
		paramMap.put("updateDate",DateUtils.getCurrentTime());
		//不是当前组成员
		if(objDb != null && BuyerMemberPositionEnum.GROUP_LEADER.code == objDb.getMemType()){
			throw  new MyProcessException("d当前成员已经是组长,不需要更改！");
		}
		//组长职位人选变更
		paramMap.put("buyerId",null);
		paramMap.put("memType",BuyerMemberPositionEnum.MEMBER.code);
		paramMap.put("oldMemType",BuyerMemberPositionEnum.GROUP_LEADER.code);
		int num=baseMapper.updateMemberPosition(paramMap);
		if(num != 1){
			throw  new MyProcessException("组长人选变更失败");
		}
		paramMap.put("buyerId",buyerId);
		paramMap.put("memType",BuyerMemberPositionEnum.GROUP_LEADER.code);
		paramMap.put("oldMemType",BuyerMemberPositionEnum.MEMBER.code);
		num=baseMapper.updateMemberPosition(paramMap);
		if(num != 1){
			throw  new MyProcessException("组长人选变更失败");
		}
	}
}