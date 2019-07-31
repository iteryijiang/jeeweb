package cn.jeeweb.web.ebp.logistics.entity;

import cn.jeeweb.common.utils.StringUtils;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/***
 * 商户显示的订单列表字段
 *
 */
@Data
public class TShopOrderShowData implements Serializable {
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
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品简拼
     *
     */
    private String goodsNameShort;
    /**
     * 商品图片
     */
    private String goodsImgUrl;

    /**
     * 上屏规格描述
     */
    private String goodsSpecDesc;
    /**
     * 商品单价
     */
    private BigDecimal goodsPrice;
    /**
     * 商品数量
     */
    private int goodsNum;
    /*
    订单总金额
     */
    private BigDecimal orderTotalMoney;
    /**
     * 订单优惠金额
     */
    private BigDecimal orderCouponMoney;
    /*
    订单支付金额
     */
    private BigDecimal orderPayMoney;

    /**
     * 买手任务订单订单状态
     */
    private int buyerTaskStatus;
    /**
     * 买家留言
     */
    private String buyerMsg;

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
     * 商户任ID
     */
    private String shopTaskId;
    /**
     * 商户任务单号
     */
    private String shopTaskNo;

    public String getGoodsNameShort() {
        if(StringUtils.isNotEmpty(getGoodsName()) && getGoodsName().length()>30){
            setGoodsNameShort(getGoodsName().substring(0,30));
        }else{
            setGoodsNameShort(getGoodsName());
        }
        return goodsNameShort;
    }

    public void setGoodsNameShort(String goodsNameShort) {
        this.goodsNameShort = goodsNameShort;
    }
}
