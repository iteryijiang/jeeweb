package cn.jeeweb.web.ebp.shop.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

@TableName("t_task_base")
@SuppressWarnings("serial")
public class TtaskFee extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	private String tbid;//	varchar	32	0	-1	0	0	0	0		0	任务id	utf8	utf8_general_ci		0	0
	private String tfClass;//	varchar	32	0	-1	0	0	0	0		0	费用项	utf8	utf8_general_ci		0	0
	private String tfDetail;//	varchar	32	0	-1	0	0	0	0		0	费用明细	utf8	utf8_general_ci		0	0
	private Double subTotal;//	double	10	2	-1	0	0	0	0		0	费用小计				0	0
	private Double discount;//	double	10	2	-1	0	0	0	0		0	优惠折扣				0	0
	private Double total;//	double	10	2	-1	0	0	0	0		0	合计				0	0
	private Date createDate;//	datetime	0	0	-1	0	0	0	0		0	创建时间				0	0
	private String createUser;//	varchar	32	0	-1	0	0	0	0		0	创建人	utf8	utf8_general_ci		0	0


	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getTbid() {
		return tbid;
	}

	public void setTbid(String tbid) {
		this.tbid = tbid;
	}

	public String getTfClass() {
		return tfClass;
	}

	public void setTfClass(String tfClass) {
		this.tfClass = tfClass;
	}

	public String getTfDetail() {
		return tfDetail;
	}

	public void setTfDetail(String tfDetail) {
		this.tfDetail = tfDetail;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
}
