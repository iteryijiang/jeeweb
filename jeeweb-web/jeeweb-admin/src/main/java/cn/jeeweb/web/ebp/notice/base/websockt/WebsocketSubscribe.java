package cn.jeeweb.web.ebp.notice.base.websockt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.servlet.http.HttpServletRequest;
import cn.jeeweb.web.ebp.notice.base.ListenerManagerFactory;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocket.Connection;
import org.eclipse.jetty.websocket.WebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebsocketSubscribe extends WebSocketHandler {

	private static Logger logger = LoggerFactory.getLogger(WebsocketSubscribe.class);

	private Map<String, Connection> connectionTempMap = new ConcurrentHashMap<String, Connection>();

	private Map<String, Long> connectionIdTimestamp = new ConcurrentHashMap<String, Long>();

	private Map<String, Map<String, Queue<Connection>>> connections = new ConcurrentHashMap<String, Map<String, Queue<Connection>>>();

	private Timer timer = new Timer();

	private WebsocketSubscribe() {
		timer.schedule(new ConnectionIdTimeoutTimer(), 1000 * 60 * 10, 1000 * 60 * 5);
		timer.schedule(new ClosedConnectionCheckTimer(), 1000 * 60 * 10, 1000 * 60 * 5);
	}

	private static WebsocketSubscribe instance = new WebsocketSubscribe();

	public static WebsocketSubscribe getInstance() {
		return instance;
	}

	public Map<String, Map<String, Queue<Connection>>> getConnections() {
		return connections;
	}

	public void shutdown() {
		timer.cancel();
		connectionTempMap.clear();
		connectionIdTimestamp.clear();
		connections.clear();
	}

	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
		if (logger.isDebugEnabled()) {
			logger.debug("url:" + request.getRequestURL() + ",protocol:" + protocol);
		}
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
		}
		return new MyWebSocket();
	}

	public class MyWebSocket implements WebSocket.OnTextMessage {

		private Logger logger = LoggerFactory.getLogger(this.getClass());

		@Override
		public void onMessage(String data) {
			if (logger.isDebugEnabled()) {
				logger.debug("receive messages for the client, message:" + data);
			}
			if (StringUtils.isBlank(data)) {
				logger.warn("message is null");
				return;
			}
			Message message = JSONObject.parseObject(data, Message.class);
			short messageType = message.getType();
			switch (messageType) {
			case Message.TYPE_HANDSHAKE:
				handleSystemMessage(message.getData());
				break;
			case Message.TYPE_USER:
				handleUserMessage(message.getData());
				break;
			default:
				logger.warn("message type Error : " + message);
				break;
			}

		}

		private void handleSystemMessage(Object data) {
			WebSocketConnectionId connectionId = JSONObject.parseObject(data.toString(), WebSocketConnectionId.class);
			addConnections(connectionId);
		}

		private void handleUserMessage(Object data) {
			executeListener(data.toString());
		}

		private void addConnections(WebSocketConnectionId connectionId) {
			try {
				Connection connection = connectionTempMap.remove(connectionId.getId());
				connectionIdTimestamp.remove(connectionId.getId());
				if (connection != null) {
					if (connection.isOpen()) {
						Map<String, Queue<Connection>> userIdConnectionMap = connections.get(connectionId.getShop_id());
						if (userIdConnectionMap == null) {
							userIdConnectionMap = new ConcurrentHashMap<String, Queue<Connection>>();
							connections.put(connectionId.getShop_id(), userIdConnectionMap);
							Queue<Connection> connectionList = new ConcurrentLinkedQueue<Connection>();
							userIdConnectionMap.put(connectionId.getUser_id(), connectionList);
							connectionList.add(connection);
						} else {
							Queue<Connection> connectionList = userIdConnectionMap.get(connectionId.getUser_id());
							if (connectionList == null) {
								connectionList = new ConcurrentLinkedQueue<Connection>();
								userIdConnectionMap.put(connectionId.getUser_id(), connectionList);
							}
							connectionList.add(connection);
						}
					} else {
						logger.error("connection is not open. shop_id:" + connectionId.getShop_id() + " user_id:" + connectionId.getUser_id()
								+ " connection_id:" + connectionId.getId());
					}
				} else {
					logger.error("connection is not found. shop_id:" + connectionId.getShop_id() + " user_id:" + connectionId.getUser_id() + " connection_id:"
							+ connectionId.getId());
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		private void executeListener(String data) {
			Queue<cn.jeeweb.web.ebp.notice.base.Listener> listeners = ListenerManagerFactory.getInstance().list();
			for (cn.jeeweb.web.ebp.notice.base.Listener listener : listeners) {
				listener.onMessage(data);
			}
		}

		@Override
		public void onOpen(Connection connection) {
			String connectionId = UUID.randomUUID().toString();
			if (logger.isDebugEnabled()) {
				logger.debug("websocket connection open, connectionId : " + connectionId);
			}
			connectionTempMap.put(connectionId, connection);
			connectionIdTimestamp.put(connectionId, System.currentTimeMillis());
			try {
				Message message = new Message(UUID.randomUUID().toString(), Message.TYPE_HANDSHAKE, connectionId);
				connection.sendMessage(message.toString());
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}

		@Override
		public void onClose(int closeCode, String message) {
			if (logger.isDebugEnabled()) {
				logger.debug("websocket connection close, closeCode : " + closeCode + " message:" + message);
			}
		}
	}

	private class ConnectionIdTimeoutTimer extends TimerTask {

		private static final int CONNECTION_ID_TIMEOUT = 1000 * 60 * 5;

		@Override
		public void run() {
			Iterator<Entry<String, Long>> it = connectionIdTimestamp.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Long> entry = it.next();
				if (System.currentTimeMillis() - entry.getValue() > CONNECTION_ID_TIMEOUT) {
					String key = entry.getKey();
					connectionTempMap.remove(key);
					connectionIdTimestamp.remove(key);
				}
			}
		}

	}

	private class ClosedConnectionCheckTimer extends TimerTask {

		@Override
		public void run() {
			Iterator<Entry<String, Map<String, Queue<Connection>>>> connectionsIt = connections.entrySet().iterator();
			while (connectionsIt.hasNext()) {
				Entry<String, Map<String, Queue<Connection>>> connectionsEntry = connectionsIt.next();
				Map<String, Queue<Connection>> userIdConnectionMap = connectionsEntry.getValue();
				if (userIdConnectionMap == null || userIdConnectionMap.isEmpty()) {
					connectionsIt.remove();
					if (logger.isDebugEnabled()) {
						logger.debug("connections remove");
					}
					continue;
				}
				Iterator<Entry<String, Queue<Connection>>> userIdConnectionIt = userIdConnectionMap.entrySet().iterator();
				while (userIdConnectionIt.hasNext()) {
					Entry<String, Queue<Connection>> userIdEntry = userIdConnectionIt.next();
					Queue<Connection> connectionList = userIdEntry.getValue();
					if (connectionList == null || connectionList.isEmpty()) {
						userIdConnectionIt.remove();
						if (logger.isDebugEnabled()) {
							logger.debug("connections remove userId:" + userIdEntry.getKey());
						}
						continue;
					}
					Iterator<Connection> connectionIt = connectionList.iterator();
					while (connectionIt.hasNext()) {
						Connection connection = connectionIt.next();
						if (connection == null || !connection.isOpen()) {
							connectionIt.remove();
							if (logger.isDebugEnabled()) {
								logger.debug("connection remove userId:" + userIdEntry.getKey());
							}
							continue;
						}
					}
				}
			}
		}
	}
}