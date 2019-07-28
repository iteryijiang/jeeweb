package cn.jeeweb.web.ebp.logistics.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

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
     * 出库订单的单号
     */
    private String outStoreOrderNo;

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
    private String orderDtime;
    /**
     * 支付时间
     */
    private String orderPayTime;
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
    private String orderTotalMoney;
    /**
     * 订单优惠金额
     */
    private String orderCouponMoney;
    /*
    订单支付金额
     */
    private String orderPayMoney;

    /**
     * 订单状态
     */
    private String orderStatus;
    /**
     * 顶大物流信息
     */
    private String logisticsInfo;
    /**
     * 买家留言
     */
    private String buyerMsg;
    /**
     * 买家昵称
     */
    private String buyerJdNickName;
    /**
     * 买手ID
     */
    private String buyerId;
    /**
     * 买家平台登录号
     */
    private String buyerLoginNo;
    /**
     * 买家京东登录号
     */
    private String buyerJdLoginNo;
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
     * 店铺ID
     */
    private String shopId;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 店铺订单备注
     */
    private String shopRemark;
    /**
     * 商户任ID
     */
    private String shopTaskId;
    /**
     * 商户任务单号
     */
    private String shopTaskNo;

    /**
     * 商品出库应支付佣金金额
     */
    private String outStoreShouldPayMoney;

    /**
     * 出库时间
     *
     */
    private String outStoreTime;

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
    private String outStoreCommissionPayOrderId;
    /**
     * 商品出库商户佣金支付时间
     *
     */
    private String outStoreCommissionPayTime;

    /**
     * 商品出库商户支付佣金金额
     */
    private String outStoreCommissionPayMoney;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
