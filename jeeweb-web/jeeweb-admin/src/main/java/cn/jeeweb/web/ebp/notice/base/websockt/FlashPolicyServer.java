package cn.jeeweb.web.ebp.notice.base.websockt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author ytj
 * @version v1.0.1
 * @Date 2019/5/13
 */
public class FlashPolicyServer {
  private ServerSocket serverSocket;

  private static Thread serverThread;

  private int port;

  private static boolean listening = true;

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  public FlashPolicyServer() {
    this(843);
  }

  public FlashPolicyServer(int port) {
    this.port = port;
  }

  public void start() {
    try {
      serverThread = new Thread(new Runnable() {
        public void run() {
          try {
            serverSocket = new ServerSocket(port);
            while (listening) {
              Socket socket = serverSocket.accept();
              OutputStream out = null;
              InputStream in = null;
              try {
                socket.setSoTimeout(10000);
                in = socket.getInputStream();
                byte[] buffer = new byte[23];
                if (in.read(buffer) != -1 && (new String(buffer, "ISO-8859-1")).startsWith("<policy-file-request/>")) {
                  if (logger.isDebugEnabled()) {
                    logger.debug("PolicyServerServlet: Serving Policy File...");
                  }
                  out = socket.getOutputStream();
                  byte[] bytes = ("<?xml version=\"1.0\"?>\n"
                      + "<!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\">\n" + "<cross-domain-policy> \n"
                      + "   <site-control permitted-cross-domain-policies=\"master-only\"/>\n"
                      + "   <allow-access-from domain=\"*\" to-ports=\"*\" />\n" + "</cross-domain-policy>").getBytes("ISO-8859-1");
                  out.write(bytes);
                  out.write(0x00);
                } else {
                  logger.warn("FlashPolicyServer: Ignoring Invalid Request");
                }
              } catch (SocketException e) {
                logger.error(e.getMessage(), e);
              } catch (IOException e) {
                logger.error(e.getMessage(), e);
              } finally {
                releaseResource(socket, out, in);
              }
            }
          } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
          }
        }

        private void releaseResource(Socket socket, OutputStream out, InputStream in) {
          if (out != null) {
            try {
              out.flush();
            } catch (IOException e) {
            } finally {
              try {
                out.close();
              } catch (IOException e) {
              }

            }
          }
          if (in != null) {
            try {
              in.close();
            } catch (IOException e) {
            }
          }
          try {
            socket.close();
          } catch (Exception ex2) {
          }
        }

      });
      serverThread.start();
    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    }

  }

  public void stop() {
    if (listening) {
      listening = false;
    }
    if (!serverSocket.isClosed()) {
      try {
        serverSocket.close();
      } catch (Exception ex) {
      }
    }
  }
}
