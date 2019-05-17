package cn.jeeweb.web.ebp.notice.enums;

/**
 * 消息通知优先等级
 */
public enum NoticeLevelEnum {
  ONE(1),
  TWO(2),
  THREE(3),
  ;

  //消息范围类型数值
  public int levelValue;

  private NoticeLevelEnum(int levelValue){
    this.levelValue=levelValue;
  }
}
