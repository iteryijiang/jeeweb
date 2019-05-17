package cn.jeeweb.web.ebp.notice.base;


import cn.jeeweb.web.ebp.notice.base.websockt.WebsocketListenerManager;

public class ListenerManagerFactory {

	private ListenerManagerFactory() {
	}

	private static ListenerManager instance = new WebsocketListenerManager();

	public static ListenerManager getInstance() {
		return instance;
	}
}
