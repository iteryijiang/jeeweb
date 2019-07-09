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
     * 开始天数
     */
    private int beginDayNum;

    /**
     * 结束天数
     */
    private int endDayNum;

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
}
