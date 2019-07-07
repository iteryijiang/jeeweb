package cn.jeeweb.web.ebp.buyer.entity;

import java.math.BigDecimal;

import cn.jeeweb.web.ebp.enums.CommissionTypeEnum;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import cn.jeeweb.web.common.entity.DataEntity;

/**
 * 买手等级表
 *
 * @author ytj
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
    @TableField(exist = false)
    private String preLevelName;
    /**
     * 下一个等级
     */
    private String nextLevelCode;
    @TableField(exist = false)
    private String nextLevelName;

    /**
     * 佣金类型
     */
    private int commissionType=1;
    @TableField(exist = false)
    private String commissionTypeName;
    /**
     * 任务链接佣金系数
     */
    private BigDecimal commissionRatio=BigDecimal.ZERO;
    /**
     * 保底工资
     */
    private BigDecimal minimumWage=BigDecimal.ZERO;
    /**
     * 固定佣金金额
     */
    private BigDecimal commissionValue=BigDecimal.ZERO;


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

    public int getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(int commissionType) {
        this.commissionType = commissionType;
    }

    public BigDecimal getCommissionValue() {
        return commissionValue;
    }

    public void setCommissionValue(BigDecimal commissionValue) {
        this.commissionValue = commissionValue;
    }

    public String getCommissionTypeName() {
        CommissionTypeEnum obj = CommissionTypeEnum.valueOfCode(getCommissionType());
        commissionTypeName = (obj == null) ? "" : obj.codeName;
        return commissionTypeName;
    }

    public void setCommissionTypeName(String commissionTypeName) {
        this.commissionTypeName = commissionTypeName;
    }

    public BigDecimal getMinimumWage() {
        return minimumWage;
    }

    public void setMinimumWage(BigDecimal minimumWage) {
        this.minimumWage = minimumWage;
    }

    public String getPreLevelName() {
        return preLevelName;
    }

    public void setPreLevelName(String preLevelName) {
        this.preLevelName = preLevelName;
    }

    public String getNextLevelName() {
        return nextLevelName;
    }

    public void setNextLevelName(String nextLevelName) {
        this.nextLevelName = nextLevelName;
    }
}
