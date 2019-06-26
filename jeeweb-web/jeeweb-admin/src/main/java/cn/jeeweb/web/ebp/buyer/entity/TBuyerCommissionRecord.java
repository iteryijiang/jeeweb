package cn.jeeweb.web.ebp.buyer.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import cn.jeeweb.web.common.entity.DataEntity;

/**
 * 买手佣金记录
 * 每天定时任务生成
 * 
 * @author ytj
 *
 */
@TableName("t_buyer_commission_record")
@SuppressWarnings("serial")
public class TBuyerCommissionRecord extends DataEntity<Long> {

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
