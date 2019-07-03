package cn.jeeweb.web.ebp.buyer.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import cn.jeeweb.web.common.entity.DataEntity;

/**
 * 买手佣金记录
 * 每天定时任务生成
 * 
 * @author ytj
 *
 */
@TableName("t_buyer_commission_record")
@SuppressWarnings("serial")
public class TBuyerCommissionRecord extends DataEntity<Long> {

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	
	/**
	 * 佣金账期
	 */
	private Date atime;
	/**
	 * 任务佣金
	 */
	private BigDecimal taskMoney=BigDecimal.ZERO;
	
	/***
	 *生成记录时的任务佣金 
	 */
	private BigDecimal taskMoneyInit=BigDecimal.ZERO;
	
	/**
	 * 小组提成
	 * 
	 */
	private BigDecimal groupMoney=BigDecimal.ZERO;
	
	/**
	 * 生成记录时的小组提成
	 * 
	 */
	private BigDecimal groupMoneyInit=BigDecimal.ZERO;
	
	/**
	 * 最终佣金金额
	 * 有可能回退单，需要变更佣金金额
	 */
	private BigDecimal commissionMoney=BigDecimal.ZERO;
	
	/**
	 * 生成记录时的佣金金额
	 */
	private BigDecimal commissionMoneyInit=BigDecimal.ZERO;
	
	/**
	 * 任务数量
	 * 
	 */
	private long taskNum=0;
	
	/***
	 * 生成记录时的任务数量
	 * 
	 */
	private long taskNumInit=0;
	/**
	 * 任务链接数量
	 */
	private long taskLinkNum=0;
	
	/**
	 * 生成佣金记录时的任务链接数量
	 */
	private long taskLinkNumInit=0;
	
	/**
	 * 买手ID
	 */
	private String buyerId;
	
	/**
	 * 买手名称 
	 */
	private String buyerName;
	
	/**
	 * 生成佣金记录时买手等级id
	 */
	private long buyerLevel;
	
	/**
	 * 生成佣金记录时买手等级名称
	 */
	private String buyerLevelName;
	
	/**
	 * 生成佣金记录时间
	 */
	private Date dtime;
	
	/**
	 * 买手所属组的组长信息
	 * 
	 */
	private String buyerGroupLeader;

	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Date getAtime() {
		return atime;
	}

	public void setAtime(Date atime) {
		this.atime = atime;
	}

	public BigDecimal getCommissionMoney() {
		return commissionMoney;
	}

	public void setCommissionMoney(BigDecimal commissionMoney) {
		this.commissionMoney = commissionMoney;
	}

	public BigDecimal getCommissionMoneyInit() {
		return commissionMoneyInit;
	}

	public void setCommissionMoneyInit(BigDecimal commissionMoneyInit) {
		this.commissionMoneyInit = commissionMoneyInit;
	}

	public long getTaskLinkNum() {
		return taskLinkNum;
	}

	public void setTaskLinkNum(long taskLinkNum) {
		this.taskLinkNum = taskLinkNum;
	}

	public long getTaskLinkNumInit() {
		return taskLinkNumInit;
	}

	public void setTaskLinkNumInit(long taskLinkNumInit) {
		this.taskLinkNumInit = taskLinkNumInit;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public long getBuyerLevel() {
		return buyerLevel;
	}

	public void setBuyerLevel(long buyerLevel) {
		this.buyerLevel = buyerLevel;
	}

	public String getBuyerLevelName() {
		return buyerLevelName;
	}

	public void setBuyerLevelName(String buyerLevelName) {
		this.buyerLevelName = buyerLevelName;
	}

	public Date getDtime() {
		return dtime;
	}

	public void setDtime(Date dtime) {
		this.dtime = dtime;
	}

	public String getBuyerGroupLeader() {
		return buyerGroupLeader;
	}

	public void setBuyerGroupLeader(String buyerGroupLeader) {
		this.buyerGroupLeader = buyerGroupLeader;
	}

	public BigDecimal getTaskMoney() {
		return taskMoney;
	}

	public void setTaskMoney(BigDecimal taskMoney) {
		this.taskMoney = taskMoney;
	}

	public BigDecimal getTaskMoneyInit() {
		return taskMoneyInit;
	}

	public void setTaskMoneyInit(BigDecimal taskMoneyInit) {
		this.taskMoneyInit = taskMoneyInit;
	}

	public BigDecimal getGroupMoney() {
		return groupMoney;
	}

	public void setGroupMoney(BigDecimal groupMoney) {
		this.groupMoney = groupMoney;
	}

	public BigDecimal getGroupMoneyInit() {
		return groupMoneyInit;
	}

	public void setGroupMoneyInit(BigDecimal groupMoneyInit) {
		this.groupMoneyInit = groupMoneyInit;
	}

	public long getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(long taskNum) {
		this.taskNum = taskNum;
	}

	public long getTaskNumInit() {
		return taskNumInit;
	}

	public void setTaskNumInit(long taskNumInit) {
		this.taskNumInit = taskNumInit;
	}
	
	
}
