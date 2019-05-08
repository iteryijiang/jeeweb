package cn.jeeweb.web.ebp.buyer.entity;
import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

@TableName("t_apply_task_buyer_handle")
@SuppressWarnings("serial")
public class TapplyTaskBuyerHandle extends DataEntity<String> {

  /** id */
  @TableId(value = "id", type = IdType.UUID)
  private String id;
  /**
   * 异常任务申请 ID
   */
  private String applyTaskId;
  /**
   * 异常订单处理方式
   */
  private int handleMethod;
  /**
   * 买手任务ID
   */
  private String buyerTaskId;
  /**
   * 商家任务 ID
   */
  private String shopTaskId;

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public String getApplyTaskId() {
    return applyTaskId;
  }

  public void setApplyTaskId(String applyTaskId) {
    this.applyTaskId = applyTaskId;
  }

  public String getBuyerTaskId() {
    return buyerTaskId;
  }

  public void setBuyerTaskId(String buyerTaskId) {
    this.buyerTaskId = buyerTaskId;
  }

  public String getShopTaskId() {
    return shopTaskId;
  }

  public void setShopTaskId(String shopTaskId) {
    this.shopTaskId = shopTaskId;
  }

  public int getHandleMethod() {
    return handleMethod;
  }

  public void setHandleMethod(int handleMethod) {
    this.handleMethod = handleMethod;
  }


}
