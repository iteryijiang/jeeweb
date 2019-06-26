package cn.jeeweb.web.ebp.buyer.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import cn.jeeweb.web.common.entity.DataEntity;

/**
 * 买手分组成员
 * 问题=>一个买手组长有没有可能在多个组承担组长职位
 * 
 * @author ytj
 *
 */
@TableName("t_buyer_group_member")
@SuppressWarnings("serial")
public class TBuyerGroupMember extends DataEntity<Long> {

	@TableId(value = "id", type = IdType.AUTO)
	private long id;

	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	
	
}
