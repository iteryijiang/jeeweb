package cn.jeeweb.web.ebp.finance.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import cn.jeeweb.web.modules.sys.entity.User;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

@TableName("t_finance_recharge")
@SuppressWarnings("serial")
public class TfinanceRecharge extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;
	private BigDecimal rechargedeposit;//	bigint	255	0	-1	0	0	0	0		0	充值金额				0	0
	private String rechargetype;//	varchar	32	0	-1	0	0	0	0		0	充值类型	utf8	utf8_general_ci		0	0
	private String rechargeno;//	varchar	32	0	-1	0	0	0	0		0	流水号	utf8	utf8_general_ci		0	0
	private String paymentno;//	varchar	32	0	-1	0	0	0	0		0	支付单号	utf8	utf8_general_ci		0	0
	private String shopid;//	varchar	32	0	-1	0	0	0	0		0	商家号	utf8	utf8_general_ci		0	0
	private BigDecimal totaldeposit;//	bigint	255	0	-1	0	0	0	0		0	总金额				0	0
	private String rechargefile;//	varchar	255	0	-1	0	0	0	0		0	支付凭证	utf8	utf8_general_ci		0	0
	private int recordStatus=0;//int(4) NULL DEFAULT 0 COMMENT '充值记录状态0正常1已撤销'


	/** 创建时间 */
	@TableField(value = "create_date", fill = FieldFill.INSERT)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createDate;

	@TableField(exist = false)
	private String loginname;
	@TableField(exist = false)
	private String username;
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getRechargedeposit() {
		return rechargedeposit;
	}

	public void setRechargedeposit(BigDecimal rechargedeposit) {
		this.rechargedeposit = rechargedeposit;
	}

	public String getRechargetype() {
		return rechargetype;
	}

	public void setRechargetype(String rechargetype) {
		this.rechargetype = rechargetype;
	}

	public String getRechargeno() {
		return rechargeno;
	}

	public void setRechargeno(String rechargeno) {
		this.rechargeno = rechargeno;
	}

	public String getPaymentno() {
		return paymentno;
	}

	public void setPaymentno(String paymentno) {
		this.paymentno = paymentno;
	}

	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}

	public BigDecimal getTotaldeposit() {
		return totaldeposit;
	}

	public void setTotaldeposit(BigDecimal totaldeposit) {
		this.totaldeposit = totaldeposit;
	}

	public String getRechargefile() {
		return rechargefile;
	}

	public void setRechargefile(String rechargefile) {
		this.rechargefile = rechargefile;
	}

	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public int getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(int recordStatus) {
		this.recordStatus = recordStatus;
	}
}
