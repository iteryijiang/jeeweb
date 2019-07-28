package cn.jeeweb.web.ebp.shop.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.math.BigDecimal;

@TableName("T_sold_Info")
@Data
@SuppressWarnings("serial")
public class TsoldInfo extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	private String soldname;//	varchar	32	0	-1	0	0	0	0		0	用户姓名	utf8	utf8_general_ci		0	0
	private String soldlogin;//	varchar	32	0	-1	0	0	0	0		0	用户账号	utf8	utf8_general_ci		0	0
	private String accountlevel;//	varchar	32	0	-1	0	0	0	0		0	用户等级	utf8	utf8_general_ci		0	0
	private BigDecimal totaldeposit;//	decimal	10	2	-1	0	0	0	0		0	总金额				0	0
	private BigDecimal availabledeposit;//	decimal	10	2	-1	0	0	0	0		0	可用金额				0	0
	private String userid;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0

	/**
	 * 销售登记名称
	 *
	 */
	@TableField(exist = false)
	private String levalName;
	/**
	 * 用户冻结状态
	 */
	@TableField(exist = false)
	private int freezeStatus;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

}
