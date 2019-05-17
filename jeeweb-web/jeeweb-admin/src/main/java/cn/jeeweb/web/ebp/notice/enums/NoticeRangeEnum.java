package cn.jeeweb.web.ebp.notice.enums;

/**
 * 消息通知范围
 *
 */
public enum NoticeRangeEnum {
  ALL(0),//全体可见
  SINGLE(1),//单个人可见
  GROUP(2),//组内成员可见
  ;

  //消息范围类型数值
  public int rangeValue;

  private NoticeRangeEnum(int rangeValue){
    this.rangeValue=rangeValue;
  }
}
