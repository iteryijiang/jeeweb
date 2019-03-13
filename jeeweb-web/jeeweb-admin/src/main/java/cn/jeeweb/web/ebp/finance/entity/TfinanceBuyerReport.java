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

@TableName("t_finance_buyer_report")
@SuppressWarnings("serial")
public class TfinanceBuyerReport extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;
	private String buyerid;//	varchar	255	0	-1	0	0	0	0		0	买手ID	utf8	utf8_general_ci		0	0

	@Excel(name = "买手", orderNum = "1",width = 15)
	private String buyername;//	varchar	32	0	-1	0	0	0	0		0	买手账号	utf8	utf8_general_ci		0	0
	@Excel(name = "任务总价", orderNum = "2",width = 15)
	private Double sumprice;//	decimal	10	2	-1	0	0	0	0		0	任务总价				0	0
	@Excel(name = "确认下单金额", orderNum = "3",width = 15)
	private Double sumorderprice;//	decimal	10	2	-1	0	0	0	0		0	确认下单金额				0	0
	@Excel(name = "确认发货金额", orderNum = "4",width = 15)
	private Double sumdeliveryprice;//	decimal	10	2	-1	0	0	0	0		0	确认发货金额				0	0
	@Excel(name = "完成金额", orderNum = "5",width = 15)
	private Double sumfinishprice;//	decimal	10	2	-1	0	0	0	0		0	完成金额				0	0
	@Excel(name = "链接数", orderNum = "6",width = 15)
	private Long sumnum;//	int	11	0	-1	0	0	0	0		0	链接数				0	0
	@Excel(name = "完成链接数", orderNum = "7",width = 15)
	private Long sumfinishnum;//	int	11	0	-1	0	0	0	0		0	完成链接数				0	0
	private Double brokerage;//	decimal	10	2	-1	0	0	0	0		0	佣金				0	0
	@Excel(name = "状态", orderNum = "8",replace= {"未对账_0", "已对账_1"}, width = 15)
	private String status;//	varchar	32	0	-1	0	0	0	0		0	统计状态	utf8	utf8_general_ci		0	0

	@JSONField(format="yyyy-MM-dd")
	@Excel(name = "日期", orderNum = "0",width = 30)
	private Date countcreatedate;




	@TableField(exist = false)
	private String buyeridName;

	@TableField(exist = false)
	private String loginName;
	/** 创建时间 */
	@TableField(value = "create_date", fill = FieldFill.INSERT)
	@JSONField(format="yyyy-MM-dd HH:mm")
	private Date createDate;

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

	public String getBuyername() {
		return buyername;
	}

	public void setBuyername(String buyername) {
		this.buyername = buyername;
	}

	public Double getSumprice() {
		return sumprice;
	}

	public void setSumprice(Double sumprice) {
		this.sumprice = sumprice;
	}

	public Double getSumorderprice() {
		return sumorderprice;
	}

	public void setSumorderprice(Double sumorderprice) {
		this.sumorderprice = sumorderprice;
	}

	public Double getSumdeliveryprice() {
		return sumdeliveryprice;
	}

	public void setSumdeliveryprice(Double sumdeliveryprice) {
		this.sumdeliveryprice = sumdeliveryprice;
	}

	public Double getSumfinishprice() {
		return sumfinishprice;
	}

	public void setSumfinishprice(Double sumfinishprice) {
		this.sumfinishprice = sumfinishprice;
	}

	public Long getSumnum() {
		return sumnum;
	}

	public void setSumnum(Long sumnum) {
		this.sumnum = sumnum;
	}

	public Long getSumfinishnum() {
		return sumfinishnum;
	}

	public void setSumfinishnum(Long sumfinishnum) {
		this.sumfinishnum = sumfinishnum;
	}

	public Double getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(Double brokerage) {
		this.brokerage = brokerage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCountcreatedate() {
		return countcreatedate;
	}

	public void setCountcreatedate(Date countcreatedate) {
		this.countcreatedate = countcreatedate;
	}

	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getBuyeridName() {
		return buyeridName;
	}

	public void setBuyeridName(String buyeridName) {
		this.buyeridName = buyeridName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}
