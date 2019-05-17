package cn.jeeweb.web.ebp.notice.base.websockt;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import cn.jeeweb.web.ebp.notice.base.MessageServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.FileResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WebSocketServer implements MessageServer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Server server;

	private FlashPolicyServer fpServer;

	private int port = 8087;

	private int flashPolicyPort = 10843;

	public WebSocketServer() {
	}

	public WebSocketServer(int port) {
		this.port = port;
	}

	private HeartbeatThread heartbeatThread = new HeartbeatThread();

	public void start() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				fpServer = new FlashPolicyServer(flashPolicyPort);
				fpServer.start();
				server = new Server(port);
				WebsocketSubscribe myWebSocketHandler = WebsocketSubscribe.getInstance();
				URL url = this.getClass().getClassLoader().getResource("");
				ResourceHandler resourceHandler = new ResourceHandler();
				try {
					resourceHandler.setBaseResource(new FileResource(url));
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				} catch (URISyntaxException e) {
					logger.error(e.getMessage(), e);
				}
				myWebSocketHandler.setHandler(resourceHandler);
				server.setHandler(myWebSocketHandler);
				try {
					server.start();
					server.join();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

			}
		});
		t.start();
		if (logger.isInfoEnabled()) {
			logger.info(Formatter.formatSuccessLog("websocket server start"));
		}
		heartbeatThread.start();
		if (logger.isInfoEnabled()) {
			logger.info(Formatter.formatSuccessLog("heartbeat service start"));
		}
	}

	public void stop() {
		try {
			fpServer.stop();
			if (logger.isInfoEnabled()) {
				logger.info(Formatter.formatSuccessLog("flash policy server stop"));
			}
		} finally {
			heartbeatThread.shutdown();
			if (logger.isInfoEnabled()) {
				logger.info(Formatter.formatSuccessLog("heartbeat service stop"));
			}
			try {
				WebsocketSubscribe.getInstance().shutdown();
			} catch (Exception e2) {
				logger.error(e2.getMessage(), e2);
			} finally {
				try {
					server.stop();
					if (logger.isInfoEnabled()) {
						logger.info("websocket server stop");
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				} finally {

				}
			}
		}
	}

}
