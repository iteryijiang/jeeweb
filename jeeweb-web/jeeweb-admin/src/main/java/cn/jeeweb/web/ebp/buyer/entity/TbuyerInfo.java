package cn.jeeweb.web.ebp.buyer.entity;

import cn.jeeweb.web.common.entity.DataEntity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;

@TableName("t_buyer_info")
@SuppressWarnings("serial")
public class TbuyerInfo extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	private String buyername;//	varchar	200	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private String loginname;//	varchar	200	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private String accountlevel;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private BigDecimal phonenum;//	double	10	4	-1	0	0	0	0		0					0	0
	private BigDecimal totalmoney;//	double	10	4	-1	0	0	0	0		0					0	0
	private String withdrawalmoney;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private String userid;//	varchar	32	0	-1	0	0	0	0		0	用户ID	utf8	utf8_general_ci		0	0
	/**
	 * 买手分组
	 */
	private String groupId;
	/**
	 * 买手分组
	 * 
	 */
	@TableField(exist = false)
	private String groupName;
	/**
	 * 买手等级
	 * 
	 */
	@TableField(exist = false)
	private String levelName;
	@TableField(exist = false)
	private String levelCode;
	/**
	 * 用户邮箱
	 */
	@TableField(exist = false)
	private String email;
	/**
	 * 冻结状态
	 */
	@TableField(exist = false)
	private String freezeStatus;
	/**
	 * 手机号码
	 */
	@TableField(exist = false)
	private String phone;
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getBuyername() {
		return buyername;
	}

	public void setBuyername(String buyername) {
		this.buyername = buyername;
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

	public BigDecimal getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(BigDecimal phonenum) {
		this.phonenum = phonenum;
	}

	public BigDecimal getTotalmoney() {
		return totalmoney;
	}

	public void setTotalmoney(BigDecimal totalmoney) {
		this.totalmoney = totalmoney;
	}

	public String getWithdrawalmoney() {
		return withdrawalmoney;
	}

	public void setWithdrawalmoney(String withdrawalmoney) {
		this.withdrawalmoney = withdrawalmoney;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFreezeStatus() {
		return freezeStatus;
	}

	public void setFreezeStatus(String freezeStatus) {
		this.freezeStatus = freezeStatus;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
}
