package cn.jeeweb.web.ebp.notice.base;


import cn.jeeweb.web.ebp.notice.base.websockt.WebSocketServer;

public class MessageServerFactory {

	private static MessageServer instance = null;

	private MessageServerFactory() {
	}

	public synchronized static MessageServer getInstance() {
		if (instance == null) {
			instance = new WebSocketServer();
		}
		return instance;
	}

	public synchronized static MessageServer getInstance(int port) {
		if (instance == null) {
			instance = new WebSocketServer(port);
		}
		return instance;
	}

}
