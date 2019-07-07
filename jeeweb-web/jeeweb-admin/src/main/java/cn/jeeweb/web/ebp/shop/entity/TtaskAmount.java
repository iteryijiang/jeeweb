package cn.jeeweb.web.ebp.shop.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;

@TableName("t_task_amount")
@SuppressWarnings("serial")
public class TtaskAmount extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;
	private String taskid;//	varchar	32	0	-1	0	0	0	0		0	任务ID	utf8	utf8_general_ci		0	0
	private BigDecimal taskamount;//	decimal	10	2	-1	0	0	0	0		0	任务金额单价				0	0
	private String tasktype;//	varchar	32	0	-1	0	0	0	0		0	任务金额类型	utf8	utf8_general_ci		0	0
	private int tasknum;//	int	10	0	-1	0	0	0	0		0	任务金额数量				0	0
	private BigDecimal tasksumamount;//	decimal	10	2	-1	0	0	0	0		0	任务总金额				0	0

	public TtaskAmount(){

	}
	public TtaskAmount(String taskid,BigDecimal taskamount,String tasktype,int tasknum,BigDecimal tasksumamount){
		this.taskid = taskid;
		this.taskamount=taskamount;
		this.tasktype=tasktype;
		this.tasknum=tasknum;
		this.tasksumamount=tasksumamount;
	}


	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public BigDecimal getTaskamount() {
		return taskamount;
	}

	public void setTaskamount(BigDecimal taskamount) {
		this.taskamount = taskamount;
	}

	public String getTasktype() {
		return tasktype;
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}

	public int getTasknum() {
		return tasknum;
	}

	public void setTasknum(int tasknum) {
		this.tasknum = tasknum;
	}

	public BigDecimal getTasksumamount() {
		return tasksumamount;
	}

	public void setTasksumamount(BigDecimal tasksumamount) {
		this.tasksumamount = tasksumamount;
	}
}
