package cn.jeeweb.web.ebp.notice.base.websockt;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import cn.jeeweb.web.ebp.notice.base.ThreadUtil;
import org.eclipse.jetty.websocket.WebSocket.Connection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HeartbeatThread extends Thread {

	private Log logger = LogFactory.getLog(this.getClass());

	boolean run = true;

	public void shutdown() {
		this.run = false;
	}

	@Override
	public void run() {
		while (run) {
			ThreadUtil.sleep(1000 * 30);
			Message message = new Message(Message.TYPE_HEARTBEAT, "hi");
			Iterator<Entry<String, Map<String, Queue<Connection>>>> connectionsIt = WebsocketSubscribe.getInstance().getConnections().entrySet().iterator();
			while (connectionsIt.hasNext()) {
				Entry<String, Map<String, Queue<Connection>>> connectionsEntry = connectionsIt.next();
				Map<String, Queue<Connection>> userIdConnectionMap = connectionsEntry.getValue();
				if (userIdConnectionMap == null || userIdConnectionMap.isEmpty()) {
					continue;
				}
				Iterator<Entry<String, Queue<Connection>>> userIdConnectionIt = userIdConnectionMap.entrySet().iterator();
				while (userIdConnectionIt.hasNext()) {
					Entry<String, Queue<Connection>> userIdEntry = userIdConnectionIt.next();
					Queue<Connection> connectionList = userIdEntry.getValue();
					if (connectionList == null || connectionList.isEmpty()) {
						userIdConnectionIt.remove();
						continue;
					}
					Iterator<Connection> connectionIt = connectionList.iterator();
					while (connectionIt.hasNext()) {
						Connection connection = connectionIt.next();
						try {
							if (connection == null || !connection.isOpen()) {
								connectionIt.remove();
								continue;
							}
							connection.sendMessage(message.toString());
						} catch (IOException e) {
							logger.error(e.getMessage(), e);
						}
					}
				}

			}
		}
	}
}
