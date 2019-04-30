package cn.jeeweb.web.ebp.shop.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;

@TableName("t_shop_grade_info")
@SuppressWarnings("serial")
public class TshopGradeInfo extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;

	private String shopgrade;//	varchar	32	0	-1	0	0	0	0		0	商户等级	utf8	utf8_general_ci		0	0
	private int onehundred;//	int	11	0	-1	0	0	0	0		0	一百				0	0
	private int onehundredfive;//	int	11	0	-1	0	0	0	0		0	一百五				0	0
	private int twohundred;//	int	11	0	-1	0	0	0	0		0	两百				0	0
	private int twohundredfive;//	int	11	0	-1	0	0	0	0		0	两百五				0	0
	private int threehundred;//	int	11	0	-1	0	0	0	0		0	三百				0	0
	private int fourhundred;//	int	11	0	-1	0	0	0	0		0	四百				0	0
	private int fivehundred;//	int	11	0	-1	0	0	0	0		0	五百				0	0


	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getShopgrade() {
		return shopgrade;
	}

	public void setShopgrade(String shopgrade) {
		this.shopgrade = shopgrade;
	}

	public int getOnehundred() {
		return onehundred;
	}

	public void setOnehundred(int onehundred) {
		this.onehundred = onehundred;
	}

	public int getOnehundredfive() {
		return onehundredfive;
	}

	public void setOnehundredfive(int onehundredfive) {
		this.onehundredfive = onehundredfive;
	}

	public int getTwohundred() {
		return twohundred;
	}

	public void setTwohundred(int twohundred) {
		this.twohundred = twohundred;
	}

	public int getTwohundredfive() {
		return twohundredfive;
	}

	public void setTwohundredfive(int twohundredfive) {
		this.twohundredfive = twohundredfive;
	}

	public int getThreehundred() {
		return threehundred;
	}

	public void setThreehundred(int threehundred) {
		this.threehundred = threehundred;
	}

	public int getFourhundred() {
		return fourhundred;
	}

	public void setFourhundred(int fourhundred) {
		this.fourhundred = fourhundred;
	}

	public int getFivehundred() {
		return fivehundred;
	}

	public void setFivehundred(int fivehundred) {
		this.fivehundred = fivehundred;
	}
}
