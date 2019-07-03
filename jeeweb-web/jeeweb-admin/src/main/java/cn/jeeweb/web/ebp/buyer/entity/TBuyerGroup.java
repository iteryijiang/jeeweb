package cn.jeeweb.web.ebp.buyer.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import cn.jeeweb.web.common.entity.DataEntity;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
@TableName("t_buyer_group")
@SuppressWarnings("serial")
public class TBuyerGroup extends DataEntity<String> {

	@TableId(value = "id", type = IdType.UUID)
	private String id;

	/***
	 * 分组编号
	 *
	 */
	private String groupCode;

	/**
	 * 分组名称
	 * 
	 */
	private String groupName;
	
	/**
	 * 初始分组组长
	 */
	private String initGroupLeader;
	/**
	 * 初始分组组长名称
	 */
	private String initGroupLeaderName;
	/**
	 * 分组组长
	 */
	private String groupLeader;
	/**
	 * 分组组长名称
	 */
	private String groupLeaderName;
	
	/**
	 * 买手成员
	 * 
	 */
	@TableField(exist = false)
	List<TBuyerGroupMember> buyerMemberList;

	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getInitGroupLeader() {
		return initGroupLeader;
	}

	public void setInitGroupLeader(String initGroupLeader) {
		this.initGroupLeader = initGroupLeader;
	}

	public String getInitGroupLeaderName() {
		return initGroupLeaderName;
	}

	public void setInitGroupLeaderName(String initGroupLeaderName) {
		this.initGroupLeaderName = initGroupLeaderName;
	}

	public String getGroupLeader() {
		return groupLeader;
	}

	public void setGroupLeader(String groupLeader) {
		this.groupLeader = groupLeader;
	}

	public String getGroupLeaderName() {
		return groupLeaderName;
	}

	public void setGroupLeaderName(String groupLeaderName) {
		this.groupLeaderName = groupLeaderName;
	}

	public List<TBuyerGroupMember> getBuyerMemberList() {
		return buyerMemberList;
	}

	public void setBuyerMemberList(List<TBuyerGroupMember> buyerMemberList) {
		this.buyerMemberList = buyerMemberList;
	}

	
	
}
