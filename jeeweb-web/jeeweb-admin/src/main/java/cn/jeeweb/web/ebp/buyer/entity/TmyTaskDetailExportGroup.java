package cn.jeeweb.web.ebp.buyer.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

public class TmyTaskDetailExportGroup {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	@Excel(name = "订单状态", orderNum = "7",replace= {"已接单、待下单_1", "已下单、待发货_2", "已发货、待收货_3", "已收货、完成_4"},width = 30)
	private String taskstate;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0

	@Excel(name = "实付金额", orderNum = "8", width = 15)
	private BigDecimal pays;//实付金额

	@Excel(name = "京东账号", orderNum = "5",width = 20)
	private String buyerjdnick;//京东账号	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0

	@Excel(name = "订单号", orderNum = "6",width = 20)
	private String jdorderno; //京东订单号

	@Excel(name = "领取时间", orderNum = "0",format="yyyy-MM-dd HH:mm",width = 30)
	private String receivingdates;//接受任务时间

	@Excel(name = "完成时间", orderNum = "1",format="yyyy-MM-dd HH:mm",width = 30)
	private String orderdates;//买手下单时间

	@Excel(name = "商家名称", orderNum = "3",width = 20)
	private String shopidName;

	@Excel(name = "商家号", orderNum = "2",width = 20)
	private String shopLoginname;


	@Excel(name = "店铺名", orderNum = "4",width = 20)
	private String shopname;



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskstate() {
		return taskstate;
	}

	public void setTaskstate(String taskstate) {
		this.taskstate = taskstate;
	}

	public BigDecimal getPays() {
		return pays;
	}

	public void setPays(BigDecimal pays) {
		this.pays = pays;
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

	public String getShopidName() {
		return shopidName;
	}

	public void setShopidName(String shopidName) {
		this.shopidName = shopidName;
	}

	public String getShopLoginname() {
		return shopLoginname;
	}

	public void setShopLoginname(String shopLoginname) {
		this.shopLoginname = shopLoginname;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
}
