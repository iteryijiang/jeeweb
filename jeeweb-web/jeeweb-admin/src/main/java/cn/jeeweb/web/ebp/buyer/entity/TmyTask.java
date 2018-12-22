package cn.jeeweb.web.ebp.buyer.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("t_my_task")
@SuppressWarnings("serial")
public class TmyTask extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	private String buyerId;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private String taskId;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private String taskState;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
}
