package cn.jeeweb.web.ebp.shop.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("t_sequence")
@SuppressWarnings("serial")
public class Tsequence extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;
	private String nkey;//	varchar	32	0	-1	0	0	0	0		0		utf8	utf8_general_ci		0	0
	private Long nnum;//	int	10	0	-1	0	0	0	0		0					0	0

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getNkey() {
		return nkey;
	}

	public void setNkey(String nkey) {
		this.nkey = nkey;
	}

	public Long getNnum() {
		return nnum;
	}

	public void setNnum(Long nnum) {
		this.nnum = nnum;
	}
}
