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
	 * 最终佣金金额
	 * 有可能回退单，需要变更佣金金额
	 */
	private BigDecimal commissionMoney;
	
	/**
	 * 生成记录时的佣金金额
	 */
	private BigDecimal commissionMoneyInit;
	
	/**
	 * 任务链接数量
	 */
	private long taskLinkNum;
	
	/**
	 * 生成佣金记录时的任务链接数量
	 */
	private long taskLinkNumInit;
	
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
}
