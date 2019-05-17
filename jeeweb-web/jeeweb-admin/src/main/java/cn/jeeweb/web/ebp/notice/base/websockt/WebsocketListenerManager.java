package cn.jeeweb.web.ebp.notice.base.websockt;

import cn.jeeweb.web.ebp.notice.base.Listener;
import cn.jeeweb.web.ebp.notice.base.ListenerManager;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WebsocketListenerManager implements ListenerManager {

	private Queue<Listener> listenners = new ConcurrentLinkedQueue<Listener>();

	@Override
	public void registe(Listener listener) {
		if (listener == null) {
			throw new NullPointerException("listener is null.");
		}
		this.listenners.add(listener);

	}

	@Override
	public void unregiste(Listener listener) {
		if (listener == null) {
			throw new NullPointerException("listener is null.");
		}
		this.listenners.remove(listener);
	}

	@Override
	public Queue<Listener> list() {
		return listenners;
	}

}
