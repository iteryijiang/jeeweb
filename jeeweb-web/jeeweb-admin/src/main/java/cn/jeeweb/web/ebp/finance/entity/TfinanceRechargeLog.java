package cn.jeeweb.web.ebp.finance.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
	private String taskid;//	varchar	32	0	-1	0	0	0	0		0	店铺ID	utf8	utf8_general_ci		0	0
	@Excel(name = "交易类型", orderNum = "6",width = 20)
	private String tradetype;//	varchar	32	0	-1	0	0	0	0		0	操作类型	utf8	utf8_general_ci		0	0
	private BigDecimal producedeposit;//	bigint	255	0	-1	0	0	0	0		0	操作金额				0	0
	@Excel(name = "账户余额", orderNum = "5",width = 20)
	private BigDecimal availabledeposit;//	bigint	255	0	-1	0	0	0	0		0	剩余金额				0	0
	private String rechargeId;//	varchar	32	0	-1	0	0	0	0		0	引用表t_finance_recharge主键ID	utf8	utf8_general_ci		0	0

	/** 创建时间 */
	@TableField(value = "create_date", fill = FieldFill.INSERT)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@Excel(name = "日期", orderNum = "9",format="yyyy-MM-dd HH:mm",width = 30)
	private Date createDate;

	@TableField(exist = false)
	@Excel(name = "商户号", orderNum = "1",width = 20)
	private String loginname;
	@TableField(exist = false)
	@Excel(name = "店铺", orderNum = "8",width = 20)
	private String shopname;
	@TableField(exist = false)
	@Excel(name = "收入金额", orderNum = "3",width = 20)
	private String producedepositIncomeName;//收入
	@TableField(exist = false)
	@Excel(name = "支出金额", orderNum = "4",width = 20)
	private String producedepositPayName;//支出
	@TableField(exist = false)
	@Excel(name = "任务编码", orderNum = "7",width = 20)
	private String taskno;
	@TableField(exist = false)
	@Excel(name = "商户来源", orderNum = "2",width = 20)
	private String fromInnerOuter;



	public TfinanceRechargeLog(){

	}

	public TfinanceRechargeLog(String shopid,String tradetype,BigDecimal producedeposit,BigDecimal availabledeposit){
		this.shopid = shopid;
		this.tradetype = tradetype;
		this.producedeposit = producedeposit;
		this.availabledeposit = availabledeposit;
	}

	public TfinanceRechargeLog(String shopid,String storeid,String taskid,String tradetype,BigDecimal producedeposit,BigDecimal availabledeposit){
		this.shopid = shopid;
		this.storeid = storeid;
		this.taskid = taskid;
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

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getProducedepositIncomeName() {
		return producedepositIncomeName;
	}

	public void setProducedepositIncomeName(String producedepositIncomeName) {
		this.producedepositIncomeName = producedepositIncomeName;
	}

	public String getProducedepositPayName() {
		return producedepositPayName;
	}

	public void setProducedepositPayName(String producedepositPayName) {
		this.producedepositPayName = producedepositPayName;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getTaskno() {
		return taskno;
	}

	public void setTaskno(String taskno) {
		this.taskno = taskno;
	}

	public String getFromInnerOuter() {
		return fromInnerOuter;
	}

	public void setFromInnerOuter(String fromInnerOuter) {
		this.fromInnerOuter = fromInnerOuter;
	}

	public String getRechargeId() {
		return rechargeId;
	}

	public void setRechargeId(String rechargeId) {
		this.rechargeId = rechargeId;
	}
}
