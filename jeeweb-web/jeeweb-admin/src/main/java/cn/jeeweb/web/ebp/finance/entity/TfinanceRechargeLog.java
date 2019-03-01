package cn.jeeweb.web.ebp.finance.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

@TableName("t_finance_recharge_log")
@SuppressWarnings("serial")
public class TfinanceRechargeLog extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;
	private String shopid;//	varchar	32	0	-1	0	0	0	0		0	商户ID	utf8	utf8_general_ci		0	0
	private String storeid;//	varchar	32	0	-1	0	0	0	0		0	店铺ID	utf8	utf8_general_ci		0	0
	private String tradetype;//	varchar	32	0	-1	0	0	0	0		0	操作类型	utf8	utf8_general_ci		0	0
	private BigDecimal producedeposit;//	bigint	255	0	-1	0	0	0	0		0	操作金额				0	0
	private BigDecimal availabledeposit;//	bigint	255	0	-1	0	0	0	0		0	剩余金额				0	0


	/** 创建时间 */
	@TableField(value = "create_date", fill = FieldFill.INSERT)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createDate;

	@TableField(exist = false)
	private String loginname;
	@TableField(exist = false)
	private String username;

	public TfinanceRechargeLog(String shopid,String tradetype,BigDecimal producedeposit,BigDecimal availabledeposit){
		this.shopid = shopid;
		this.tradetype = tradetype;
		this.producedeposit = producedeposit;
		this.availabledeposit = availabledeposit;
	}

	public TfinanceRechargeLog(String shopid,String storeid,String tradetype,BigDecimal producedeposit,BigDecimal availabledeposit){
		this.shopid = shopid;
		this.storeid = storeid;
		this.tradetype = tradetype;
		this.producedeposit = producedeposit;
		this.availabledeposit = availabledeposit;
	}



	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}

	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	public String getTradetype() {
		return tradetype;
	}

	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}

	public BigDecimal getProducedeposit() {
		return producedeposit;
	}

	public void setProducedeposit(BigDecimal producedeposit) {
		this.producedeposit = producedeposit;
	}

	public BigDecimal getAvailabledeposit() {
		return availabledeposit;
	}

	public void setAvailabledeposit(BigDecimal availabledeposit) {
		this.availabledeposit = availabledeposit;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
