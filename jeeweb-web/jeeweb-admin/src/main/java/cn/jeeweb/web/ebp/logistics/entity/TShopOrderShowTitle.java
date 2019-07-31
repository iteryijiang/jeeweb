package cn.jeeweb.web.ebp.logistics.entity;

import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.ebp.enums.BuyerTaskStatusEnum;
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
     * 买手任务单号
     *
     */
    private String buyerTaskNo;

    /**
     * 商户任务单号
     *
     */
    private String shopTaskNo;
    /**
     * 下单时间
     */
    private Date orderDtime;

    /**
     * 下单时间格式化
     */
    private String orderDtimeFormat;
    /**
     * 支付时间
     */
    private Date orderPayTime;

    /**
     * 支付时间格式化
     *
     */
    private String orderPayTimeFormat;

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
        BuyerTaskStatusEnum obj=BuyerTaskStatusEnum.valueOfCode(getOrderStatus());
        orderStatusName=(obj == null)?"状态异常":obj.codeName;
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getOrderDtimeFormat() {
        if(getOrderDtime() != null){
            orderDtimeFormat= DateUtils.formatDateTime(getOrderDtime());
        }
        return orderDtimeFormat;
    }

    public void setOrderDtimeFormat(String orderDtimeFormat) {
        this.orderDtimeFormat = orderDtimeFormat;
    }

    public String getOrderPayTimeFormat() {
        if(getOrderPayTime() != null){
            orderPayTimeFormat= DateUtils.formatDateTime(getOrderPayTime());
        }
        return orderPayTimeFormat;
    }

    public void setOrderPayTimeFormat(String orderPayTimeFormat) {
        this.orderPayTimeFormat = orderPayTimeFormat;
    }
}
