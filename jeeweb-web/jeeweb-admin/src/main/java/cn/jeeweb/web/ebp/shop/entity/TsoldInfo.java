package cn.jeeweb.web.ebp.shop.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;

@TableName("T_sold_Info")
@SuppressWarnings("serial")
public class TsoldInfo extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	private String soldname;//	varchar	32	0	-1	0	0	0	0		0	用户姓名	utf8	utf8_general_ci		0	0
	private String soldlogin;//	varchar	32	0	-1	0	0	0	0		0	用户账号	utf8	utf8_general_ci		0	0
	private String accountlevel;//	varchar	32	0	-1	0	0	0	0		0	用户等级	utf8	utf8_general_ci		0	0
	private BigDecimal totaldeposit;//	decimal	10	2	-1	0	0	0	0		0	总金额				0	0
	private BigDecimal availabledeposit;//	decimal	10	2	-1	0	0	0	0		0	可用金额				0	0
	private String userid;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0


	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}


	public String getSoldname() {
		return soldname;
	}

	public void setSoldname(String soldname) {
		this.soldname = soldname;
	}

	public String getSoldlogin() {
		return soldlogin;
	}

	public void setSoldlogin(String soldlogin) {
		this.soldlogin = soldlogin;
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

	public BigDecimal getAvailabledeposit() {
		return availabledeposit;
	}

	public void setAvailabledeposit(BigDecimal availabledeposit) {
		this.availabledeposit = availabledeposit;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}
