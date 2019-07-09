package cn.jeeweb.web.ebp.seller.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import cn.jeeweb.web.ebp.enums.CommissionTypeEnum;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;

/**
 * 销售等级
 *
 * @author ytj
 */
@TableName("t_seller_level")
@SuppressWarnings("serial")
public class TSellerLevel extends DataEntity<String> {

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
     * 基本薪资
     */
    private BigDecimal salaryBase = BigDecimal.ZERO;

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

    public BigDecimal getSalaryBase() {
        return salaryBase;
    }

    public void setSalaryBase(BigDecimal salaryBase) {
        this.salaryBase = salaryBase;
    }
}
