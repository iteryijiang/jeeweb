package cn.jeeweb.web.ebp.report.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import cn.jeeweb.web.common.entity.DataEntity;

/**
 * 日报数据
 * 
 * @author ytj
 *
 */
@TableName("t_day_report")
@SuppressWarnings("serial")
public class TDayReport extends DataEntity<String> {

	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	/**
	 * 所属账期
	 */
	private Date atime;
	
	/**
	 * 执行时间
	 */
	private Date dtime;
	/**
	 * 期初余额
	 */
	private BigDecimal beginingBalance=BigDecimal.ZERO;
	/**
	 * 期末余额
	 */
	private BigDecimal endingBalance=BigDecimal.ZERO;
	/**
	 * 当天收到充值金额
	 */
	private BigDecimal totalRechargeDeposit=BigDecimal.ZERO;
	/**
	 * 当天冻结金额
	 */
	private BigDecimal todayFreezing=BigDecimal.ZERO;

	/**
	 * 当天佣金总金额
	 */
	private BigDecimal todayCommission=BigDecimal.ZERO;
	/**
	 * 当天支付金额
	 */
	private BigDecimal activePayMoney=BigDecimal.ZERO;
	
	/**
	 * 任务总量
	 * 
	 */
	private long taskTotalCount=0;
	
	/**
	 * 内部任务数量
	 */
	private long internalTaskCount=0;
	/**
	 * 外部任务数量
	 */
	private long outerTaskCount=0;
	
	/**
	 * 内部任务占比
	 * 
	 */
	private BigDecimal internalTaskRatio=BigDecimal.ZERO;
	
	/**
	 * 外部任务占比
	 * 
	 */
	private BigDecimal outerTaskRatio=BigDecimal.ZERO;
	
	/**
	 * 内部链接总数
	 */
	private long internalTaskLinkCount=0;

	/**
	 * 外部任务链接总数
	 */
	private long outerTaskLinkCount=0;
	
	/**
	 * 内部链接占比
	 */
	private BigDecimal internalTaskLinkRatio=BigDecimal.ZERO;

	/**
	 * 外部任务链接占比
	 */
	private BigDecimal outerTaskLinkRatio=BigDecimal.ZERO;
	
	/**
	 * 0佣金任务数量
	 */
	private long zeroCommissionTaskCount=0;
	/**
	 * 0佣金任务链接数量
	 */
	private long zeroCommissionTaskLinkCount=0;
	/**
	 * 总任务链接数量
	 */
	private long totalTaskLinkCount=0;
	/**
	 * 任务单链接数量
	 */
	private long singleTaskLinkCount=0;
	/**
	 * 任务双链接数量
	 */
	private long doubleTaskLinkCount=0;
	
	/**
	 * 单链接占比
	 * 
	 */
	private BigDecimal singleTaskLinkRatio=BigDecimal.ZERO;
	
	/**
	 * 双链接占比
	 * 
	 */
	private BigDecimal doubleTaskLinkRatio=BigDecimal.ZERO;
	
	/**
	 * 问题任务数量
	 */
	private long problemTaskCount=0;
	/**
	 * 问题任务链接数量
	 */
	private long problemTaskLinkCount=0;

	/**
	 * 商户撤销任务数量
	 */
	private long shopRevokeTaskCount=0;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getAtime() {
		return atime;
	}

	public void setAtime(Date atime) {
		this.atime = atime;
	}

	public Date getDtime() {
		return dtime;
	}

	public void setDtime(Date dtime) {
		this.dtime = dtime;
	}

	public BigDecimal getBeginingBalance() {
		return beginingBalance;
	}

	public void setBeginingBalance(BigDecimal beginingBalance) {
		this.beginingBalance = beginingBalance;
	}

	public BigDecimal getEndingBalance() {
		return endingBalance;
	}

	public void setEndingBalance(BigDecimal endingBalance) {
		this.endingBalance = endingBalance;
	}

	public BigDecimal getTotalRechargeDeposit() {
		return totalRechargeDeposit;
	}

	public void setTotalRechargeDeposit(BigDecimal totalRechargeDeposit) {
		this.totalRechargeDeposit = totalRechargeDeposit;
	}

	public BigDecimal getTodayFreezing() {
		return todayFreezing;
	}

	public void setTodayFreezing(BigDecimal todayFreezing) {
		this.todayFreezing = todayFreezing;
	}

	public BigDecimal getTodayCommission() {
		return todayCommission;
	}

	public void setTodayCommission(BigDecimal todayCommission) {
		this.todayCommission = todayCommission;
	}

	public BigDecimal getActivePayMoney() {
		return activePayMoney;
	}

	public void setActivePayMoney(BigDecimal activePayMoney) {
		this.activePayMoney = activePayMoney;
	}

	public long getInternalTaskCount() {
		return internalTaskCount;
	}

	public void setInternalTaskCount(long internalTaskCount) {
		this.internalTaskCount = internalTaskCount;
	}

	public long getInternalTaskLinkCount() {
		return internalTaskLinkCount;
	}

	public void setInternalTaskLinkCount(long internalTaskLinkCount) {
		this.internalTaskLinkCount = internalTaskLinkCount;
	}

	public long getOuterTaskCount() {
		return outerTaskCount;
	}

	public void setOuterTaskCount(long outerTaskCount) {
		this.outerTaskCount = outerTaskCount;
	}

	public long getOuterTaskLinkCount() {
		return outerTaskLinkCount;
	}

	public void setOuterTaskLinkCount(long outerTaskLinkCount) {
		this.outerTaskLinkCount = outerTaskLinkCount;
	}

	public long getZeroCommissionTaskCount() {
		return zeroCommissionTaskCount;
	}

	public void setZeroCommissionTaskCount(long zeroCommissionTaskCount) {
		this.zeroCommissionTaskCount = zeroCommissionTaskCount;
	}

	public long getZeroCommissionTaskLinkCount() {
		return zeroCommissionTaskLinkCount;
	}

	public void setZeroCommissionTaskLinkCount(long zeroCommissionTaskLinkCount) {
		this.zeroCommissionTaskLinkCount = zeroCommissionTaskLinkCount;
	}

	public long getTotalTaskLinkCount() {
		return totalTaskLinkCount;
	}

	public void setTotalTaskLinkCount(long totalTaskLinkCount) {
		this.totalTaskLinkCount = totalTaskLinkCount;
	}

	public long getSingleTaskLinkCount() {
		return singleTaskLinkCount;
	}

	public void setSingleTaskLinkCount(long singleTaskLinkCount) {
		this.singleTaskLinkCount = singleTaskLinkCount;
	}

	public long getDoubleTaskLinkCount() {
		return doubleTaskLinkCount;
	}

	public void setDoubleTaskLinkCount(long doubleTaskLinkCount) {
		this.doubleTaskLinkCount = doubleTaskLinkCount;
	}

	public long getProblemTaskCount() {
		return problemTaskCount;
	}

	public void setProblemTaskCount(long problemTaskCount) {
		this.problemTaskCount = problemTaskCount;
	}

	public long getProblemTaskLinkCount() {
		return problemTaskLinkCount;
	}

	public void setProblemTaskLinkCount(long problemTaskLinkCount) {
		this.problemTaskLinkCount = problemTaskLinkCount;
	}

	public long getShopRevokeTaskCount() {
		return shopRevokeTaskCount;
	}

	public void setShopRevokeTaskCount(long shopRevokeTaskCount) {
		this.shopRevokeTaskCount = shopRevokeTaskCount;
	}

	public BigDecimal getSingleTaskLinkRatio() {
		return singleTaskLinkRatio;
	}

	public void setSingleTaskLinkRatio(BigDecimal singleTaskLinkRatio) {
		this.singleTaskLinkRatio = singleTaskLinkRatio;
	}

	public BigDecimal getDoubleTaskLinkRatio() {
		return doubleTaskLinkRatio;
	}

	public void setDoubleTaskLinkRatio(BigDecimal doubleTaskLinkRatio) {
		this.doubleTaskLinkRatio = doubleTaskLinkRatio;
	}

	public long getTaskTotalCount() {
		return taskTotalCount;
	}

	public void setTaskTotalCount(long taskTotalCount) {
		this.taskTotalCount = taskTotalCount;
	}

	public BigDecimal getInternalTaskRatio() {
		return internalTaskRatio;
	}

	public void setInternalTaskRatio(BigDecimal internalTaskRatio) {
		this.internalTaskRatio = internalTaskRatio;
	}

	public BigDecimal getOuterTaskRatio() {
		return outerTaskRatio;
	}

	public void setOuterTaskRatio(BigDecimal outerTaskRatio) {
		this.outerTaskRatio = outerTaskRatio;
	}

	public BigDecimal getInternalTaskLinkRatio() {
		return internalTaskLinkRatio;
	}

	public void setInternalTaskLinkRatio(BigDecimal internalTaskLinkRatio) {
		this.internalTaskLinkRatio = internalTaskLinkRatio;
	}

	public BigDecimal getOuterTaskLinkRatio() {
		return outerTaskLinkRatio;
	}

	public void setOuterTaskLinkRatio(BigDecimal outerTaskLinkRatio) {
		this.outerTaskLinkRatio = outerTaskLinkRatio;
	}

}
