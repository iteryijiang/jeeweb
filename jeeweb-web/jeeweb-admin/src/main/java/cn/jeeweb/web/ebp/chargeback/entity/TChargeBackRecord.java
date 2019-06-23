package cn.jeeweb.web.ebp.chargeback.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;


@TableName("t_charge_back_record")
@SuppressWarnings("serial")
public class TChargeBackRecord extends DataEntity<String> {

    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 执行时间
     */
    private Date dtime;
    /**
     * 退单原因
     */
    private String chargeBackReason;
    /**
     * 退单金额
     */
    private BigDecimal chargeBackMoney;
    /**
     * 买手任务单号
     */
    private String buyerTaskNo;
    /**
     * 买手任务 ID
     */
    private String buyerTaskId;
    /**
     * 买手 ID
     */
    private String buyerId;
    /**
     * 买手名称
     */
    private String buyerNo;
    /**
     * 商户 ID
     */
    private String shopId;
    /**
     * 商家名称
     */
    private String shopName;
    /**
     * 商户任务 ID
     */
    private String shopTaskId;
    /**
     * 商户任务单号
     */
    private String shopTaskNo;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 电商平台类型
     */
    private int ecommerceType;
    /**
     * 电商平台订单编号
     */
    private String ecommerceOrderNo;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public Date getDtime() {
        return dtime;
    }

    public void setDtime(Date dtime) {
        this.dtime = dtime;
    }

    public String getChargeBackReason() {
        return chargeBackReason;
    }

    public void setChargeBackReason(String chargeBackReason) {
        this.chargeBackReason = chargeBackReason;
    }

    public BigDecimal getChargeBackMoney() {
        return chargeBackMoney;
    }

    public void setChargeBackMoney(BigDecimal chargeBackMoney) {
        this.chargeBackMoney = chargeBackMoney;
    }

    public String getBuyerTaskNo() {
        return buyerTaskNo;
    }

    public void setBuyerTaskNo(String buyerTaskNo) {
        this.buyerTaskNo = buyerTaskNo;
    }

    public String getBuyerTaskId() {
        return buyerTaskId;
    }

    public void setBuyerTaskId(String buyerTaskId) {
        this.buyerTaskId = buyerTaskId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerNo() {
        return buyerNo;
    }

    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopTaskId() {
        return shopTaskId;
    }

    public void setShopTaskId(String shopTaskId) {
        this.shopTaskId = shopTaskId;
    }

    public String getShopTaskNo() {
        return shopTaskNo;
    }

    public void setShopTaskNo(String shopTaskNo) {
        this.shopTaskNo = shopTaskNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getEcommerceType() {
        return ecommerceType;
    }

    public void setEcommerceType(int ecommerceType) {
        this.ecommerceType = ecommerceType;
    }

    public String getEcommerceOrderNo() {
        return ecommerceOrderNo;
    }

    public void setEcommerceOrderNo(String ecommerceOrderNo) {
        this.ecommerceOrderNo = ecommerceOrderNo;
    }
}
