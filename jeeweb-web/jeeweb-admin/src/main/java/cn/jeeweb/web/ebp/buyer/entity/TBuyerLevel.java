package cn.jeeweb.web.ebp.buyer.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import cn.jeeweb.web.common.entity.DataEntity;

/**
 * 买手分组
 * 
 * @author ytj
 *
 */
@TableName("t_buyer_level")
@SuppressWarnings("serial")
public class TBuyerLevel extends DataEntity<Long> {

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
