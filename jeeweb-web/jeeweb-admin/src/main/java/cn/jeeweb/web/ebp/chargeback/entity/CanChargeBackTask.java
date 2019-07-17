package cn.jeeweb.web.ebp.chargeback.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 能够退单 任务单
 * 
 * @author ytj
 *
 */
public class CanChargeBackTask extends DataEntity<String> {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 5831021995282339364L;
	private String id;
	private String buyerTaskId;
    /**
     * 买手任务单号
     */
    private String buyerTaskNo;
    /**
     * 买手 ID
     */
    private String buyerId;
    /**
     * 买手编号
     */
    private String buyerNo;
    /**
     * 买手名称
     */
    private String buyerName;
    /**
     * 商户 ID
     */
    private String shopId;
    /**
     * 商户关联用户表的ID
     * 
     */
    private String shopUserId;
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
     * SKUID
     */
    private String skuid;
    /**
     * 电商平台类型
     */
    private int ecommerceType;
    /**
     * 电商平台订单编号
     */
    private String ecommerceOrderNo;

    /**
     * 任务单状态
     *
     */
    private String buyTaskStatus;

    /**
     * 任务实际支付金额
     */
    private BigDecimal taskPayMoney=BigDecimal.ZERO;
    
    /**
     * 任务佣金
     * 平台扣除的佣金
     * 
     */
    private BigDecimal taskCommission=BigDecimal.ZERO; 
    
    /**
     * 接受任务时间
     *
     */
    @JSONField(format="yyyy-MM-dd HH:mm")
    private java.util.Date receivingdate;

    public String getBuyerTaskId() {
        return buyerTaskId;
    }

    public void setBuyerTaskId(String buyerTaskId) {
        this.buyerTaskId = buyerTaskId;
    }

    public String getBuyerTaskNo() {
        return buyerTaskNo;
    }

    public void setBuyerTaskNo(String buyerTaskNo) {
        this.buyerTaskNo = buyerTaskNo;
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

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
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

    public String getBuyTaskStatus() {
        return buyTaskStatus;
    }

    public void setBuyTaskStatus(String buyTaskStatus) {
        this.buyTaskStatus = buyTaskStatus;
    }

    public Date getReceivingdate() {
        return receivingdate;
    }

    public void setReceivingdate(Date receivingdate) {
        this.receivingdate = receivingdate;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getTaskPayMoney() {
		return taskPayMoney;
	}

	public void setTaskPayMoney(BigDecimal taskPayMoney) {
		this.taskPayMoney = taskPayMoney;
	}

	public BigDecimal getTaskCommission() {
		return taskCommission;
	}

	public void setTaskCommission(BigDecimal taskCommission) {
		this.taskCommission = taskCommission;
	}

	public String getShopUserId() {
		return shopUserId;
	}

	public void setShopUserId(String shopUserId) {
		this.shopUserId = shopUserId;
	}

    public String getSkuid() {
        return skuid;
    }

    public void setSkuid(String skuid) {
        this.skuid = skuid;
    }
}
