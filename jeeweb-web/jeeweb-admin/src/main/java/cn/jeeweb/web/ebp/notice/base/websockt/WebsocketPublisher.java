package cn.jeeweb.web.ebp.notice.base.websockt;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import cn.jeeweb.web.ebp.notice.base.Publisher;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.websocket.WebSocket.Connection;


public class WebsocketPublisher implements Publisher {

	private Log logger = LogFactory.getLog(this.getClass());

	@Override
	public void publish(String userId, String message) {
		Message messageObject = new Message(message);
		Map<String, Queue<Connection>> userIdConnectionMap = WebsocketSubscribe.getInstance().getConnections().get(userId);
		if (userIdConnectionMap == null || userIdConnectionMap.isEmpty()) {
			return;
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
					connection.sendMessage(messageObject.toString());
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	public void publish(String shopId, String userId, String message) {
		Message messageObject = new Message(message);
		Map<String, Queue<Connection>> userIdConnectionMap = WebsocketSubscribe.getInstance().getConnections().get(shopId);
		if (userIdConnectionMap == null || userIdConnectionMap.isEmpty()) {
			return;
		}
		Queue<Connection> connections = userIdConnectionMap.get(userId);
		if(connections == null || connections.isEmpty()){
				return ;
		}
		Iterator<Connection> connectionIt = connections.iterator();
		while (connectionIt.hasNext()) {
			Connection connection = connectionIt.next();
			try {
				if (connection == null || !connection.isOpen()) {
					connectionIt.remove();
					continue;
				}
				connection.sendMessage(messageObject.toString());
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void publish(String message) {
		Message messageObject = new Message(message);
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
						connection.sendMessage(messageObject.toString());
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}

		}

	}

}
