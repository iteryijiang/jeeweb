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
//	private String taskid;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private String mytaskno;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private Double totalprice;//	double	10	2	-1	0	0	0	0		0					0	0
	private String state;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	/** 创建时间 */
	@TableField(value = "create_date", fill = FieldFill.INSERT)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createDate;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}


	public String getMytaskno() {
		return mytaskno;
	}

	public void setMytaskno(String mytaskno) {
		this.mytaskno = mytaskno;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

//	public String getTaskid() {
//		return taskid;
//	}
//
//	public void setTaskid(String taskid) {
//		this.taskid = taskid;
//	}

	public Double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Double totalprice) {
		this.totalprice = totalprice;
	}

	@Override
	public java.util.Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
}
