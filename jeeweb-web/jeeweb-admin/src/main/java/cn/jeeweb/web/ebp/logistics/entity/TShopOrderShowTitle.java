package cn.jeeweb.web.ebp.logistics.entity;

import lombok.Data;

import java.io.Serializable;


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
}
