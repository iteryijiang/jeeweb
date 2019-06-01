package cn.jeeweb.web.ebp.enums;

/**
 * 系统默认固定的角色
 * 
 * @author ytj
 *
 */
public enum BasicRoleEnum {
	SHOP("3a0e855344644a7a904ec74846d46cc3", "operation", "商户运营"),
	BUYER("402880e45b5d7636015b5d8baca60000", "normal", "买手"),
	ADMIN("40288ab85a362150015a3675ca950006", "admin", "系统管理员"),
	FINANCE("bf956405e2df4de5a92e68462cb810d1", "finance", "财务"),
	SELLING("ea9402a4fb914905be62b3152eab56ca", "selling", "销售"),;
	public String roleId;
	public String roleCode;
	public String roleName;

	BasicRoleEnum(String roleId, String roleCode, String roleName) {
		this.roleId = roleId;
		this.roleCode = roleCode;
		this.roleName = roleName;
	}
}
