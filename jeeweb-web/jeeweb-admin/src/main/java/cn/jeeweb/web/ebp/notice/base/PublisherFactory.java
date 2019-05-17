package cn.jeeweb.web.ebp.notice.base;


import cn.jeeweb.web.ebp.notice.base.websockt.WebsocketPublisher;

public class PublisherFactory {

	private PublisherFactory() {
	}

	private static Publisher instance = new WebsocketPublisher();

	public static Publisher getInstance() {
		return instance;
	}
}
