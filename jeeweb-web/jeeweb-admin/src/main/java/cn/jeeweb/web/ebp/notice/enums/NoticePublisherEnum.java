package cn.jeeweb.web.ebp.notice.enums;

/***
 * 消息通知发布者
 *
 */
public enum NoticePublisherEnum {
  //平台发布消息
  SYSTEM(10000),
  //商家发布消息
  SHOP(20000),
  //买手发布消息
  BUYER(30000),
  ;

  //消息类型编码
  public int typeValue;

  private NoticePublisherEnum(int typeValue){
    this.typeValue=typeValue;
  }
  //系统消息=>平台消息
  //任务消息=>商家任务(发布任务，撤回任务)，买手任务(领取任务，提出问题)
  //物流消息=>任务状态流转
}
