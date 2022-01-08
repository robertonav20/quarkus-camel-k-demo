package demo.com;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/events/{component}")
@ApplicationScoped
public class WebSocketServer {

    private static final Logger logger = Logger.getLogger(WebSocketServer.class);

    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("component") String component) {
        sessions.put(component, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("component") String component) {
        sessions.remove(component);
        broadcast("User " + component + " left");
    }

    @OnError
    public void onError(Session session, @PathParam("component") String component, Throwable throwable) {
        sessions.remove(component);
        broadcast("User " + component + " left on error: " + throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("component") String component) {
        logger.info("Receiver a message!");
        if (message.equalsIgnoreCase("_ready_")) {
            broadcast("User " + component + " joined");
        } else {
            broadcast(">> " + component + ": " + message);
        }
    }

    private void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result -> {
                if (result.getException() != null) {
                    logger.info("Unable to send message: " + result.getException());
                }
            });
        });
    }
}