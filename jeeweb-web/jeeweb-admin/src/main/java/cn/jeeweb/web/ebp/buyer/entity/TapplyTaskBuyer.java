package cn.jeeweb.web.ebp.buyer.entity;
import cn.jeeweb.common.utils.DateUtils;
import cn.jeeweb.web.common.entity.DataEntity;
import cn.jeeweb.web.ebp.enums.UnusualTaskHandleMethodEnum;
import cn.jeeweb.web.ebp.enums.YesNoEnum;
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
   * 反馈类型名称
   *
   */
  @TableField(exist = false)
  private String applyTypeName;
  /**
   * 买手任务ID，具体的单个任务链接
   * 主键
   */
  private String buyerTaskId;
  /**
   * m买手任务单号
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
   * 商户任务 ID
   */
  private String shopTaskId;
  /**
   * 商户 ID
   */
  private String shopId;
  /**
   * 商家名称
   */
  private String shopName;
  /**
   * 商品名称
   */
  private String goodsName;
  /**
   * 申请状态
   */
  private int applyStatus;

  /**
   * 反馈类型状态
   *
   */
  @TableField(exist = false)
  private String applyStatusName;
  /**
   * 申请描述
   */
  private String applyDesc;
  /**
   * 申请时间
   */
  @JSONField(format="yyyy-MM-dd HH:mm:ss")
  private Date applyTime;

  @TableField(exist = false)
  private String applyTimeFormat;
  /**
   * 处理时间
   */
  @JSONField(format="yyyy-MM-dd HH:mm:ss")
  private Date handleTime;

  @TableField(exist = false)
  private String handleTimeFormat;
  /**
   * 处理人
   */
  private String handleMan;
  /**
   * 处理方式
   */
  private int handleMethod;

  /**
   * 处理方式名称
   *
   */
  @TableField(exist = false)
  private String handleMethodName;
  /**
   * 商户任务单号
   */
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

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public String getShopTaskNo() {
    return shopTaskNo;
  }

  public void setShopTaskNo(String shopTaskNo) {
    this.shopTaskNo = shopTaskNo;
  }

  public String getApplyTimeFormat() {
    return DateUtils.formatDateTime(applyTime);
  }

  public void setApplyTimeFormat(String applyTimeFormat) {
    this.applyTimeFormat = applyTimeFormat;
  }

  public String getHandleTimeFormat() {
    if(handleTime != null){
      return DateUtils.formatDateTime(handleTime);
    }
    return handleTimeFormat;
  }

  public void setHandleTimeFormat(String handleTimeFormat) {
    this.handleTimeFormat = handleTimeFormat;
  }

  public String getApplyTypeName() {
    return applyTypeName;
  }

  public void setApplyTypeName(String applyTypeName) {
    this.applyTypeName = applyTypeName;
  }

  public String getApplyStatusName() {
    applyStatusName=(YesNoEnum.YES.code == applyStatus)?"已处理":"未处理";
    return applyStatusName;
  }

  public void setApplyStatusName(String applyStatusName) {
    this.applyStatusName = applyStatusName;
  }

  public String getHandleMethodName() {
    UnusualTaskHandleMethodEnum obj=UnusualTaskHandleMethodEnum.valueOfCode(handleMethod);
    if(obj!=null){
      return obj.codeName;
    }
    return handleMethodName;
  }

  public void setHandleMethodName(String handleMethodName) {
    this.handleMethodName = handleMethodName;
  }
}
