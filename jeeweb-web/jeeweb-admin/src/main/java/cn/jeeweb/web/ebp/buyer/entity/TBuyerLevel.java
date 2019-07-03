package cn.jeeweb.web.ebp.buyer.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import cn.jeeweb.web.common.entity.DataEntity;

/**
 * 买手等级表
 * 
 * @author ytj
 *
 */
@TableName("t_buyer_level")
@SuppressWarnings("serial")
public class TBuyerLevel extends DataEntity<String> {

	@TableId(value = "id", type = IdType.UUID)
	private String id;
	/**
	 * 等级编码
	 */
	private String levelCode;
	
	/**
	 * 等级名称
	 */
	private String levelName;
	/**
	 * 上一个等级
	 */
	private String preLevelCode;
	/**
	 * 下一个等级
	 */
	private String nextLevelCode;
	/**
	 * 任务链接佣金系数
	 */
	private BigDecimal commissionRatio;

	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getPreLevelCode() {
		return preLevelCode;
	}

	public void setPreLevelCode(String preLevelCode) {
		this.preLevelCode = preLevelCode;
	}

	public String getNextLevelCode() {
		return nextLevelCode;
	}

	public void setNextLevelCode(String nextLevelCode) {
		this.nextLevelCode = nextLevelCode;
	}

	public BigDecimal getCommissionRatio() {
		return commissionRatio;
	}

	public void setCommissionRatio(BigDecimal commissionRatio) {
		this.commissionRatio = commissionRatio;
	}

}
