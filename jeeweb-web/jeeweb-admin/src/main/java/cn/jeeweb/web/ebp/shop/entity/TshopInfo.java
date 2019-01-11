package cn.jeeweb.web.ebp.shop.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

@TableName("T_shop_Info")
@SuppressWarnings("serial")
public class TshopInfo extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	private String shopname;//	varchar	200	0	-1	0	0	0	0		0	商户名称	utf8	utf8_general_ci		0	0
	private String loginname;//	varchar	50	0	-1	0	0	0	0		0	登录账号	utf8	utf8_general_ci		0	0
	private String accountlevel;//	varchar	32	0	-1	0	0	0	0		0	账号等级	utf8	utf8_general_ci		0	0
	private BigDecimal totaldeposit;//	double	10	2	-1	0	0	0	0		0	总押金				0	0
	private BigDecimal taskdeposit;//	double	10	2	-1	0	0	0	0		0	任务冻结押金				0	0
	private BigDecimal extractdeposit;//	double	10	2	-1	0	0	0	0		0	提现冻结押金				0	0
	private BigDecimal availabledeposit;//	double	10	2	-1	0	0	0	0		0	可用押金				0	0
	private String status;//	varchar	32	0	-1	0	0	0	0		0	状态	utf8	utf8_general_ci		0	0
	private String userid;//	varchar	32	0	-1	0	0	0	0		0	用户ID	utf8	utf8_general_ci		0	0


	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getAccountlevel() {
		return accountlevel;
	}

	public void setAccountlevel(String accountlevel) {
		this.accountlevel = accountlevel;
	}

	public BigDecimal getTotaldeposit() {
		return totaldeposit;
	}

	public void setTotaldeposit(BigDecimal totaldeposit) {
		this.totaldeposit = totaldeposit;
	}

	public BigDecimal getTaskdeposit() {
		return taskdeposit;
	}

	public void setTaskdeposit(BigDecimal taskdeposit) {
		this.taskdeposit = taskdeposit;
	}

	public BigDecimal getExtractdeposit() {
		return extractdeposit;
	}

	public void setExtractdeposit(BigDecimal extractdeposit) {
		this.extractdeposit = extractdeposit;
	}

	public BigDecimal getAvailabledeposit() {
		return availabledeposit;
	}

	public void setAvailabledeposit(BigDecimal availabledeposit) {
		this.availabledeposit = availabledeposit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}
