package cn.jeeweb.web.ebp.notice.base;

/**
 * @version v1.0.1
 * @Author ytj
 * @Date 2019/5/13
 * @Description
 */
public interface Listener {
  /**
   * 消息通知
   *
   * @param message
   *            消息
   */
  void onMessage(String message);
}
