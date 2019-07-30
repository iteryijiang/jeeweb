package cn.jeeweb.web.ebp.logistics.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * 商户订单关联买手任务明细
 *
 */
@TableName("t_logistics_order")
@SuppressWarnings("serial")
@Data
public class TLogisticsOrderDetail extends DataEntity<String> {
    /** id */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 出库订单的单号
     */
    private String masterId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品单价
     */
    private String goodsPrice;
    /**
     * 商品数量
     */
    private String goodsNum;
    /*
    订单总金额
     */
    private String orderMoney;
    /**
     * 订单优惠金额
     */
    private String orderCouponMoney;
    /*
    订单支付金额
     */
    private String orderPayMoney;
    /**
     * 买手任务 ID
     */
    private String buyerTaskId;
    /**
     * 买手任务编号
     */
    private String buyerTaskNo;
    /**
     * 买手任务明细ID
     */
    private String buyerTaskDetailId;

    /**
     * 出库确认状态0未出库1已出库
     *
     */
    private int status=0;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
