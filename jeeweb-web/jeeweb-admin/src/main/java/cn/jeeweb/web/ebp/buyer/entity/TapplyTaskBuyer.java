package cn.jeeweb.web.ebp.buyer.entity;
import cn.jeeweb.web.common.entity.DataEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

@TableName("t_apply_task_buyer")
@SuppressWarnings("serial")
public class TapplyTaskBuyer extends DataEntity<String> {

  /** id */
  @TableId(value = "id", type = IdType.UUID)
  private String id;
  /**
   * 反馈类型
   */
  private String applyType;
  /**
   * 买手任务单号
   */
  private String buyerTaskId;
  /**
   * 买手 ID
   */
  private String buyerId;
  /**
   * 商户任务 ID
   */
  private String shopTaskId;
  /**
   * 商户 ID
   */
  private String shopId;
  /**
   * 申请状态
   */
  private int applyStatus;
  /**
   * 申请描述
   */
  private String applyDesc;
  /**
   * 申请时间
   */
  @JSONField(format="yyyy-MM-dd HH:mm:ss")
  private Date applyTime;
  /**
   * 处理时间
   */
  private Date handleTime;
  /**
   * 处理人
   */
  private String handleMan;
  /**
   * 处理方式
   */
  private int handleMethod;

/*****以下不属于表结构******/
  /**
   * 商品名称
   */

  @TableField(exist = false)
  private String goodsName;
  /**
   * 买手编号
   */

  @TableField(exist = false)
  private String buyerNo;
  /**
   * 买手任务单号
   */

  @TableField(exist = false)
  private String buyerTaskNo;
  /**
   * 商户编号
   */
  @TableField(exist = false)
  private String shopNo;
  /**
   * 商户任务单号
   */
  @TableField(exist = false)
  private String shopTaskNo;

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public String getApplyType() {
    return applyType;
  }

  public void setApplyType(String applyType) {
    this.applyType = applyType;
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

  public String getShopTaskId() {
    return shopTaskId;
  }

  public void setShopTaskId(String shopTaskId) {
    this.shopTaskId = shopTaskId;
  }

  public String getShopId() {
    return shopId;
  }

  public void setShopId(String shopId) {
    this.shopId = shopId;
  }

  public int getApplyStatus() {
    return applyStatus;
  }

  public void setApplyStatus(int applyStatus) {
    this.applyStatus = applyStatus;
  }

  public String getApplyDesc() {
    return applyDesc;
  }

  public void setApplyDesc(String applyDesc) {
    this.applyDesc = applyDesc;
  }

  public Date getApplyTime() {
    return applyTime;
  }

  public void setApplyTime(Date applyTime) {
    this.applyTime = applyTime;
  }

  public Date getHandleTime() {
    return handleTime;
  }

  public void setHandleTime(Date handleTime) {
    this.handleTime = handleTime;
  }

  public String getHandleMan() {
    return handleMan;
  }

  public void setHandleMan(String handleMan) {
    this.handleMan = handleMan;
  }

  public int getHandleMethod() {
    return handleMethod;
  }

  public void setHandleMethod(int handleMethod) {
    this.handleMethod = handleMethod;
  }

  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }

  public String getBuyerNo() {
    return buyerNo;
  }

  public void setBuyerNo(String buyerNo) {
    this.buyerNo = buyerNo;
  }

  public String getBuyerTaskNo() {
    return buyerTaskNo;
  }

  public void setBuyerTaskNo(String buyerTaskNo) {
    this.buyerTaskNo = buyerTaskNo;
  }

  public String getShopNo() {
    return shopNo;
  }

  public void setShopNo(String shopNo) {
    this.shopNo = shopNo;
  }

  public String getShopTaskNo() {
    return shopTaskNo;
  }

  public void setShopTaskNo(String shopTaskNo) {
    this.shopTaskNo = shopTaskNo;
  }
}
