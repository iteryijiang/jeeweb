package cn.jeeweb.web.ebp.buyer.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("t_my_task_detail")
@SuppressWarnings("serial")
public class TmyTaskDetail extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	private String orderNo;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private String fileId;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private String state;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
