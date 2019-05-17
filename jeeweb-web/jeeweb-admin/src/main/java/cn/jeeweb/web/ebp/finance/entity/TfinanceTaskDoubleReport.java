package cn.jeeweb.web.ebp.finance.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.jeeweb.web.common.entity.DataEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

@TableName("t_finance_taskdouble_report")
@SuppressWarnings("serial")
public class TfinanceTaskDoubleReport extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	@JSONField(format="yyyy-MM-dd HH:mm")
	private Date countcreatedate;//	date	0	0	-1	0	0	0	0		0					0	0
	private String userid;//	varchar	32	0	-1	0	0	0	0		0	用户ID	utf8	utf8_general_ci		0	0
	private String loginname;//	varchar	32	0	-1	0	0	0	0		0	用户号	utf8	utf8_general_ci		0	0
	private String username;//	varchar	32	0	-1	0	0	0	0		0	用户名称	utf8	utf8_general_ci		0	0
	private String usertype;//	varchar	32	0	-1	0	0	0	0		0	用户类型	utf8	utf8_general_ci		0	0
	private int doublenum;//	int	10	0	-1	0	0	0	0		0	双链接数				0	0
	private int onenum;//	int	10	0	-1	0	0	0	0		0	单链接数				0	0



	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public Date getCountcreatedate() {
		return countcreatedate;
	}

	public void setCountcreatedate(Date countcreatedate) {
		this.countcreatedate = countcreatedate;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public int getDoublenum() {
		return doublenum;
	}

	public void setDoublenum(int doublenum) {
		this.doublenum = doublenum;
	}

	public int getOnenum() {
		return onenum;
	}

	public void setOnenum(int onenum) {
		this.onenum = onenum;
	}

}
