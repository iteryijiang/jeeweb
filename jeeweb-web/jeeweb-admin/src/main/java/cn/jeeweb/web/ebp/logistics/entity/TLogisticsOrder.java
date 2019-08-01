package cn.jeeweb.web.ebp.logistics.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户订单出库物流信息
 *
 */
@TableName("t_logistics_order")
@SuppressWarnings("serial")
@Data
public class TLogisticsOrder  extends DataEntity<String> {
    /** id */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 商品申请出库批次编号
     */
    private String outStoreBatchId;

    /**
     * 京东单号
     */
    private String jdOrderNo;
    /**
     * 下单时间
     */
    private Date orderDtime;
    /**
     * 支付时间
     */
    private Date orderPayTime;
    /**
     * 商品数量
     */
    private int goodsTotalNum;
    /*
    订单总金额
     */
    private BigDecimal orderTotalMoney;
    /**
     * 订单优惠金额
     */
    private BigDecimal orderCouponTotalMoney;
    /*
    订单支付金额
     */
    private BigDecimal orderPayTotalMoney;
    /**
     * 订单状态
     */
    private int orderStatus=0;
    /**
     * 订单物流信息
     */
    private String logisticsInfo;
    /**
     * 买家留言
     */
    private String buyerMsg;
    /**
     * 买家昵称
     */
    @TableField(exist = false)
    private String buyerJdNickName;
    /**
     * 买手ID
     */
    private String buyerId;
    /**
     * 买家平台登录号
     */
    @TableField(exist = false)
    private String buyerLoginNo;
    /**
     * 买家京东登录号
     */
    @TableField(exist = false)
    private String buyerJdLoginNo;
    /**
     * 店铺ID
     */
    private String shopUserId;
    /**
     * 商户任务单号
     *
     */
    private String shopTaskId;

    /**
     * 商户任务单号
     *
     */
    private String shopTaskNo;
    /**
     * 店铺名称
     */
    @TableField(exist = false)
    private String shopName;
    /**
     * 店铺订单备注
     */
    private String shopRemark;

    /**
     * 商品出库应支付佣金金额
     */
    private BigDecimal outStoreShouldPayMoney;

    /**
     * 出库时间
     *
     */
    private Date outStoreTime;

    /**
     * 出库操作人
     */
    private String outStoreOpeMan;

    /**
     * 商品确认出库批次ID
     */
    private String outStoreAckBatchId;

    /**
     *
     * 商品出库商户佣金支付记录编号
     */
    private String outStoreAckPayOrderId;

    /**
     * 确认出库操作人
     *
     */
    private String outStoreAckMan;
    /**
     * 商品出库商户确认时间
     *
     */
    private Date outStoreAckTime;

    /**
     * 商品出库商户支付佣金金额
     */
    private BigDecimal outStoreAckPayMoney=BigDecimal.ZERO;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
