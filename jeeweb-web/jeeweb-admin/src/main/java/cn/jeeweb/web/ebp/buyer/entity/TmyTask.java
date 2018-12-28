package cn.jeeweb.web.ebp.buyer.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.sql.Date;

@TableName("t_my_task")
@SuppressWarnings("serial")
public class TmyTask extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;
	private String goodsname;//
	private String buyerid;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private String taskid;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private String taskstate;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private String tasktype;//	任务类型：京东/淘宝varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private BigDecimal commision;//
	private BigDecimal pays;

	private String buyerjdnick;//京东账号	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private String jdorderno; //京东订单号
	private String buyerclient;//下单终端：手机，电脑
	private String expressno; //快递单号
	private String evaluate; //好评 评价
	private String taskstatus;//	varchar	32	0	-1	0	0	0	0		0	下单状态（进行中,已完成）	utf8	utf8_general_ci		0	0

	private java.util.Date receivingdate;//接受任务时间
	private java.util.Date orderdate;//买手下单时间
	private java.util.Date deliverydate;//商家发货时间
	private java.util.Date confirmdate;//确认收货时间

	/** 创建时间 */
	@TableField(value = "create_date", fill = FieldFill.INSERT)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createDate;

	@TableField(exist = false)
    private String buyeridName;

	@TableField(exist = false)
	private String taskstateName;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getBuyerid() {
		return buyerid;
	}

	public void setBuyerid(String buyerid) {
		this.buyerid = buyerid;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getTaskstate() {
		return taskstate;
	}

	public void setTaskstate(String taskstate) {
		this.taskstate = taskstate;
	}

	public BigDecimal getCommision() {
		return commision;
	}

	public void setCommision(BigDecimal commision) {
		this.commision = commision;
	}

	public BigDecimal getPays() {
		return pays;
	}

	public void setPays(BigDecimal pays) {
		this.pays = pays;
	}

	public String getTasktype() {
		return tasktype;
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getBuyerjdnick() {
		return buyerjdnick;
	}

	public void setBuyerjdnick(String buyerjdnick) {
		this.buyerjdnick = buyerjdnick;
	}

	public String getJdorderno() {
		return jdorderno;
	}

	public void setJdorderno(String jdorderno) {
		this.jdorderno = jdorderno;
	}

	public String getBuyerclient() {
		return buyerclient;
	}

	public void setBuyerclient(String buyerclient) {
		this.buyerclient = buyerclient;
	}

	public String getExpressno() {
		return expressno;
	}

	public void setExpressno(String expressno) {
		this.expressno = expressno;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public String getTaskstatus() {
		return taskstatus;
	}

	public void setTaskstatus(String taskstatus) {
		this.taskstatus = taskstatus;
	}

    public String getBuyeridName() {
        return buyeridName;
    }

    public void setBuyeridName(String buyeridName) {
        this.buyeridName = buyeridName;
    }

	@Override
	public java.util.Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.util.Date getReceivingdate() {
		return receivingdate;
	}

	public void setReceivingdate(java.util.Date receivingdate) {
		this.receivingdate = receivingdate;
	}

	public java.util.Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(java.util.Date orderdate) {
		this.orderdate = orderdate;
	}

	public java.util.Date getDeliverydate() {
		return deliverydate;
	}

	public void setDeliverydate(java.util.Date deliverydate) {
		this.deliverydate = deliverydate;
	}

	public java.util.Date getConfirmdate() {
		return confirmdate;
	}

	public void setConfirmdate(java.util.Date confirmdate) {
		this.confirmdate = confirmdate;
	}

	public String getTaskstateName() {
		return taskstateName;
	}

	public void setTaskstateName(String taskstateName) {
		this.taskstateName = taskstateName;
	}
}
