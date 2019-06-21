package cn.jeeweb.web.ebp.buyer.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.jeeweb.web.common.entity.DataEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;

@TableName("t_my_task_detail")
@SuppressWarnings("serial")
public class TmyTaskDetail extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;
	private String goodsname;//
	private String buyerid;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private String taskid;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	@Excel(name = "订单状态", orderNum = "10",replace= {"已接单、待下单_1", "已下单、待发货_2", "已发货、待收货_3", "已收货、完成_4"},width = 30)
	private String taskstate;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private String tasktype;//	任务类型：京东/淘宝varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private BigDecimal commision;//
	@Excel(name = "实付金额", orderNum = "11", width = 15)
	private BigDecimal pays;//实付金额

	@Excel(name = "京东账号", orderNum = "8",width = 20)
	private String buyerjdnick;//京东账号	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	@Excel(name = "订单号", orderNum = "9",width = 20)
	private String jdorderno; //京东订单号
	private String buyerclient;//下单终端：手机，电脑
	private String expressno; //快递单号
	private String evaluate; //好评 评价
	private String taskstatus;//	varchar	32	0	-1	0	0	0	0		0	下单状态（进行中,已完成）	utf8	utf8_general_ci		0	0

	@JSONField(format="yyyy-MM-dd HH:mm")
	private java.util.Date receivingdate;//接受任务时间
	@JSONField(format="yyyy-MM-dd HH:mm")
	@Excel(name = "完成时间", orderNum = "2",format="yyyy-MM-dd HH:mm",width = 30)
	private java.util.Date orderdate;//买手下单时间
	@JSONField(format="yyyy-MM-dd HH:mm")
	private java.util.Date deliverydate;//商家发货时间
	@JSONField(format="yyyy-MM-dd HH:mm")
	private java.util.Date confirmdate;//确认收货时间
	private String mytaskid;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private String taskshopurl;
	private String isdouble;//	varchar	32	0	-1	0	0	0	0		0	是否多单	utf8	utf8_general_ci		0	0
	private String storename;//	varchar	32	0	-1	0	0	0	0		0	店铺	utf8	utf8_general_ci		0	0
	private BigDecimal bankamount;//银行抵扣金额
	/**
	 * 标记为异常任务状态
	 * 0正常1标记为异常
	 */
	private int errorStatus=0;

	/** 创建时间 */
	@TableField(value = "create_date", fill = FieldFill.INSERT)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createDate;

	@TableField(exist = false)
	@Excel(name = "平台编号", orderNum = "0",width = 20)
	private String buyeridName;

	@TableField(exist = false)
	@Excel(name = "平台登录名", orderNum = "1",width = 20)
	private String buyeridLogin;

	@TableField(exist = false)
	@Excel(name = "商家名称", orderNum = "4",width = 20)
	private String shopidName;

	@TableField(exist = false)
	@Excel(name = "商家号", orderNum = "3",width = 20)
	private String shopLoginname;

	@TableField(exist = false)
	private String taskstateName;

	@TableField(exist = false)
	@Excel(name = "店铺名", orderNum = "5",width = 20)
	private String shopname;

	@TableField(exist = false)
	private String imgurl;

	@TableField(exist = false)
	private Double tPrice;

	@TableField(exist = false)
	private String keyword;

	@TableField(exist = false)
	private String qrcodeurl;

	@TableField(exist = false)
	private String spec1;

	@TableField(exist = false)
	private String spec2;

	@TableField(exist = false)
	private String tUrl;

	@TableField(exist = false)
	private String brand;

	@TableField(exist = false)
	@Excel(name = "货号", orderNum = "7",width = 20)
	private String article;

	@TableField(exist = false)
	private String tTitle;

	@TableField(exist = false)
	private String receivingdates;

	@TableField(exist = false)
	private String orderdates;

	@TableField(exist = false)
	@Excel(name = "SKU", orderNum = "6",width = 20)
	private String skuid;

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

	public String getMytaskid() {
		return mytaskid;
	}

	public void setMytaskid(String mytaskid) {
		this.mytaskid = mytaskid;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public Double gettPrice() {
		return tPrice;
	}

	public void settPrice(Double tPrice) {
		this.tPrice = tPrice;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getQrcodeurl() {
		return qrcodeurl;
	}

	public void setQrcodeurl(String qrcodeurl) {
		this.qrcodeurl = qrcodeurl;
	}

	public String getSpec1() {
		return spec1;
	}

	public void setSpec1(String spec1) {
		this.spec1 = spec1;
	}

	public String getSpec2() {
		return spec2;
	}

	public void setSpec2(String spec2) {
		this.spec2 = spec2;
	}

	public String gettUrl() {
		return tUrl;
	}

	public void settUrl(String tUrl) {
		this.tUrl = tUrl;
	}

	public String getTaskshopurl() {
		return taskshopurl;
	}

	public void setTaskshopurl(String taskshopurl) {
		this.taskshopurl = taskshopurl;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getShopidName() {
		return shopidName;
	}

	public void setShopidName(String shopidName) {
		this.shopidName = shopidName;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getShopLoginname() {
		return shopLoginname;
	}

	public void setShopLoginname(String shopLoginname) {
		this.shopLoginname = shopLoginname;
	}

	public String getBuyeridLogin() {
		return buyeridLogin;
	}

	public void setBuyeridLogin(String buyeridLogin) {
		this.buyeridLogin = buyeridLogin;
	}

	public String gettTitle() {
		return tTitle;
	}

	public void settTitle(String tTitle) {
		this.tTitle = tTitle;
	}

	public String getIsdouble() {
		return isdouble;
	}

	public void setIsdouble(String isdouble) {
		this.isdouble = isdouble;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public BigDecimal getBankamount() {
		return bankamount;
	}

	public void setBankamount(BigDecimal bankamount) {
		this.bankamount = bankamount;
	}

	public String getReceivingdates() {

		return receivingdates;
	}

	public void setReceivingdates(String receivingdates) {
		this.receivingdates = receivingdates;
	}

	public String getOrderdates() {
		return orderdates;
	}

	public void setOrderdates(String orderdates) {
		this.orderdates = orderdates;
	}

	public int getErrorStatus() {
		return errorStatus;
	}

	public void setErrorStatus(int errorStatus) {
		this.errorStatus = errorStatus;
	}

	public String getSkuid() {
		return skuid;
	}

	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}
}
