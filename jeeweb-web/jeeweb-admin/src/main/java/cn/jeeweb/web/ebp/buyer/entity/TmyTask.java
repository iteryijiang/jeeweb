package cn.jeeweb.web.ebp.buyer.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.sql.Date;

@TableName("t_my_task")
@SuppressWarnings("serial")
public class TmyTask extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;
	private String taskid;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private String mytaskno;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private Double totalprice;//	double	10	2	-1	0	0	0	0		0					0	0
	private String state;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0


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

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public Double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Double totalprice) {
		this.totalprice = totalprice;
	}
}
