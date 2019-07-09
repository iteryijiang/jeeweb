package cn.jeeweb.web.ebp.seller.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import cn.jeeweb.web.ebp.enums.CommissionTypeEnum;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;

/**
 * 销售等级与时间区段之间的映射关系
 *
 * @author ytj
 */
@TableName("t_sell_level_date_range_commission")
@SuppressWarnings("serial")
public class TSellerLevelDateRangeCommission extends DataEntity<String> {

    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 等级ID
     */
    private String levelId;

    /**
     * 时间段ID
     */
    private String dateRangeId;
    /**
     * 佣金类型1按比例1固定
     */
    private String commissionType;
    /**
     * 每单系数
     */
    private String commissionRatio;
    /**
     * 固定金额
     */
    private String commissionValue;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getDateRangeId() {
        return dateRangeId;
    }

    public void setDateRangeId(String dateRangeId) {
        this.dateRangeId = dateRangeId;
    }

    public String getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(String commissionType) {
        this.commissionType = commissionType;
    }

    public String getCommissionRatio() {
        return commissionRatio;
    }

    public void setCommissionRatio(String commissionRatio) {
        this.commissionRatio = commissionRatio;
    }

    public String getCommissionValue() {
        return commissionValue;
    }

    public void setCommissionValue(String commissionValue) {
        this.commissionValue = commissionValue;
    }
}
