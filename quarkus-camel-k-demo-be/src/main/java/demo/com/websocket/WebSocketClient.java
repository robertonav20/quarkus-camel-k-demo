package demo.com.websocket;

import org.jboss.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint
public class WebSocketClient {

    private static final Logger logger = Logger.getLogger(WebSocketClient.class);

    @OnOpen
    public void open(Session session) {
        logger.info("Open a session");
    }

    @OnMessage
    void message(String message) {
        logger.info("Received a message " + message);
    }
}
