package cn.jeeweb.web.ebp.notice.base;

/**
 * @version v1.0.1
 * @Author ytj
 * @Date 2019/5/13
 * @Description
 */
public class ThreadUtil {
  public static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
