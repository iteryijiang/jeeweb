package cn.jeeweb.web.ebp.seller.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import cn.jeeweb.web.ebp.enums.CommissionTypeEnum;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;

/**
 * 销售佣金对应的时间区段
 *
 * @author ytj
 */
@TableName("t_seller_commission_date_range")
@SuppressWarnings("serial")
public class TSellerCommissionDateRange extends DataEntity<String> {

    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 等级ID
     */
    private String levelId;
    @TableField(exist = false)
    private String levelName;
    /**
     * 开始天数
     */
    private int beginDayNum;

    /**
     * 结束天数
     */
    private int endDayNum;
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

    public int getBeginDayNum() {
        return beginDayNum;
    }

    public void setBeginDayNum(int beginDayNum) {
        this.beginDayNum = beginDayNum;
    }

    public int getEndDayNum() {
        return endDayNum;
    }

    public void setEndDayNum(int endDayNum) {
        this.endDayNum = endDayNum;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
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

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
