package cn.jeeweb.web.ebp.buyer.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("t_buyer_info")
@SuppressWarnings("serial")
public class TbuyerInfo extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	private String buyerName;//	varchar	200	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private String loginName;//	varchar	200	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private String accountLevel;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private Double phoneNum;//	double	10	4	-1	0	0	0	0		0					0	0
	private Double totalMoney;//	double	10	4	-1	0	0	0	0		0					0	0
	private String withdrawalMoney;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
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

	public Double getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(Double phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getWithdrawalMoney() {
		return withdrawalMoney;
	}

	public void setWithdrawalMoney(String withdrawalMoney) {
		this.withdrawalMoney = withdrawalMoney;
	}
}
