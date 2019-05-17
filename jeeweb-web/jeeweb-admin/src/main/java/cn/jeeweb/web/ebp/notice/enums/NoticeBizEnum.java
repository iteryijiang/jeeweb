package cn.jeeweb.web.ebp.notice.enums;

/**
 * 消息通知的业务类型
 */
public enum NoticeBizEnum {
  //普通消息
  COMMOM("10000"),
  //商家发布消息
  SHOP("20000"),
  //买手发布消息
  BUYER("30000"),
  ;

  //消息类型编码
  public String bizCode;

  private NoticeBizEnum(String bizCode){
    this.bizCode=bizCode;
  }
  //系统消息=>平台消息
  //任务消息=>商家任务(发布任务，撤回任务)，买手任务(领取任务，提出问题)
  //物流消息=>任务状态流转
}
