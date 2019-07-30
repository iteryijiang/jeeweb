package cn.jeeweb.web.ebp.logistics.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/***
 * 商户显示的订单列表字段
 *
 */
@Data
public class TShopOrderShowTitle implements Serializable {
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
     * 订单商品总数量
     */
    private int orderGoodsTotalNum;

    /**
     *订单总金额
     */
    private BigDecimal orderTotalMoney;

    /**
     * 买手名称
     */
    private String buyerLoginName;

    /**
     * 物流信息
     *
     */
    private String logisticsInfo;

    /**
     * 订单状态
     *
     */
    private int orderStatus;

    /**
     * 订单出库佣金
     *
     */
    private BigDecimal outStoreCommissionPrice ;
}
