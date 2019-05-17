package cn.jeeweb.web.ebp.notice.base;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @version v1.0.1
 * @Author ytj
 * @Date 2019/5/13
 * @Description
 */
@WebListener
public class MessageServerListener  implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent event) {
    MessageServerFactory.getInstance().start();
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    MessageServerFactory.getInstance().stop();
  }
}
