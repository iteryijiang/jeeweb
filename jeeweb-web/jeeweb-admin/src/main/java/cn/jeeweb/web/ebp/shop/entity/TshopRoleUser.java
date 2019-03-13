package cn.jeeweb.web.ebp.shop.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;

@TableName("t_shop_role_user")
@SuppressWarnings("serial")
public class TshopRoleUser extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	private String userid;//	varchar	32	0	-1	0	0	0	0		0	用户主键	utf8	utf8_general_ci		0	0
	private String shopid;//	varchar	32	0	-1	0	0	0	0		0	商户主键	utf8	utf8_general_ci		0	0
	private String storeid;//	varchar	32	0	-1	0	0	0	0		0	店铺主键	utf8	utf8_general_ci		0	0

	@TableField(exist = false)
	private String username;
	@TableField(exist = false)
	private String shopname;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}

	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
}
