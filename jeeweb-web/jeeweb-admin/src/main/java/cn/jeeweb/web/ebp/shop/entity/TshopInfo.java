package cn.jeeweb.web.ebp.shop.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

@TableName("T_shop_Info")
@SuppressWarnings("serial")
public class TshopInfo extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	private String shopName;//	varchar	200	0	-1	0	0	0	0		0	商户名称	utf8	utf8_general_ci		0	0
	private String loginName;//	varchar	50	0	-1	0	0	0	0		0	登录账号	utf8	utf8_general_ci		0	0
	private String accountLevel;//	varchar	32	0	-1	0	0	0	0		0	账号等级	utf8	utf8_general_ci		0	0
	private Double TotalDeposit;//	double	10	2	-1	0	0	0	0		0	总押金				0	0
	private Double taskDeposit;//	double	10	2	-1	0	0	0	0		0	任务冻结押金				0	0
	private Double extractDeposit;//	double	10	2	-1	0	0	0	0		0	提现冻结押金				0	0
	private Double AvailableDeposit;//	double	10	2	-1	0	0	0	0		0	可用押金				0	0
	private String status;//	varchar	32	0	-1	0	0	0	0		0	状态	utf8	utf8_general_ci		0	0
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

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getAccountLevel() {
		return accountLevel;
	}

	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}

	public Double getTotalDeposit() {
		return TotalDeposit;
	}

	public void setTotalDeposit(Double totalDeposit) {
		TotalDeposit = totalDeposit;
	}

	public Double getTaskDeposit() {
		return taskDeposit;
	}

	public void setTaskDeposit(Double taskDeposit) {
		this.taskDeposit = taskDeposit;
	}

	public Double getExtractDeposit() {
		return extractDeposit;
	}

	public void setExtractDeposit(Double extractDeposit) {
		this.extractDeposit = extractDeposit;
	}

	public Double getAvailableDeposit() {
		return AvailableDeposit;
	}

	public void setAvailableDeposit(Double availableDeposit) {
		AvailableDeposit = availableDeposit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
