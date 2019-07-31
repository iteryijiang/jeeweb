package cn.jeeweb.web.ebp.logistics.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


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
    private Date orderDtime;
    /**
     * 支付时间
     */
    private Date orderPayTime;

    /**
     *订单总金额
     */
    private BigDecimal orderTotalMoney=BigDecimal.ZERO;

    /**
     * 买手名称
     */
    private String buyerLoginName;

    /**
     * 买手ID
     *
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
     * 物流信息
     *
     */
    private String logisticsInfo="暂无信息";

    /**
     * 订单状态标记
     *
     */
    private int orderStatus;

    /**
     * 订单状态名称
     *
     */
    private String orderStatusName;

    /**
     * 订单出库佣金
     *
     */
    private BigDecimal outStoreCommissionPrice =BigDecimal.ZERO;
    /**
     * 店铺ID
     */
    private String shopUserId;
    /**
     * 店铺名称
     */
    private String shopName;

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }
}
