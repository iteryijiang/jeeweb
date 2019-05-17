package cn.jeeweb.web.ebp.notice.base;

import java.util.Queue;

public interface ListenerManager {

	/**
	 * 消息监听器列表
	 * 
	 * @return
	 */
	Queue<Listener> list();

	/**
	 * 注册消息监听器
	 * 
	 * @param listener
	 */
	void registe(Listener listener);

	/**
	 * 注销消息监听器
	 * 
	 * @param listener
	 */
	void unregiste(Listener listener);
}
