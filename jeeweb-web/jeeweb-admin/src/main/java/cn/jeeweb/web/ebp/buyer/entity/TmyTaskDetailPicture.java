package cn.jeeweb.web.ebp.buyer.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("t_my_task_detail_picture")
@SuppressWarnings("serial")
public class TmyTaskDetailPicture extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	private String mytaskpictureurl;//	varchar	200	0	-1	0	0	0	0		0	任务图片	utf8	utf8_general_ci		0	0
	private String tdid;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}


	public String getMytaskpictureurl() {
		return mytaskpictureurl;
	}

	public void setMytaskpictureurl(String mytaskpictureurl) {
		this.mytaskpictureurl = mytaskpictureurl;
	}

	public String getTdid() {
		return tdid;
	}

	public void setTdid(String tdid) {
		this.tdid = tdid;
	}
}
