package cn.jeeweb.web.ebp.shop.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("t_task_picture_comment")
@SuppressWarnings("serial")
public class TtaskPictureComment extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	private String taskid;//	varchar	32	0	-1	0	0	0	0		0	任务ID	utf8	utf8_general_ci		0	0
	private String taskpictureurl;//	varchar	200	0	-1	0	0	0	0		0	任务图片	utf8	utf8_general_ci		0	0
	private int taskpicturenum;//	int	10	0	-1	0	0	0	0		0	任务图片数				0	0
	private String textareaname;//	varchar	200	0	-1	0	0	0	0		0	评论内容	utf8	utf8_general_ci		0	0


	@TableField(exist = false)
	private String tdid;

	public TtaskPictureComment(){

	}

	public TtaskPictureComment(String taskid,String taskpictureurl,int taskpicturenum){
		this.taskid=taskid;
		this.taskpictureurl=taskpictureurl;
		this.taskpicturenum=taskpicturenum;
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

	public String getTaskpictureurl() {
		return taskpictureurl;
	}

	public void setTaskpictureurl(String taskpictureurl) {
		this.taskpictureurl = taskpictureurl;
	}

	public int getTaskpicturenum() {
		return taskpicturenum;
	}

	public void setTaskpicturenum(int taskpicturenum) {
		this.taskpicturenum = taskpicturenum;
	}

	public String getTdid() {
		return tdid;
	}

	public void setTdid(String tdid) {
		this.tdid = tdid;
	}

	public String getTextareaname() {
		return textareaname;
	}

	public void setTextareaname(String textareaname) {
		this.textareaname = textareaname;
	}
}
