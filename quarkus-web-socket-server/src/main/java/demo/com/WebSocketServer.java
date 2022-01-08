package demo.com;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/sessions/{component}")
@ApplicationScoped
public class WebSocketServer {

    private static final Logger logger = Logger.getLogger(WebSocketServer.class);

    Session frontedSession;
    Session backendSession;

    @OnOpen
    public void onOpen(Session session, @PathParam("component") String component) {
        if (component.equalsIgnoreCase("frontend")) {
            frontedSession = session;
        } else if (component.equalsIgnoreCase("backend")) {
            backendSession = session;
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("component") String component) {
        if (component.equalsIgnoreCase("frontend")) {
            frontedSession = null;
            session = backendSession;
        } else if (component.equalsIgnoreCase("backend")) {
            backendSession = null;
            session = frontedSession;
        }
        broadcast("User " + component + " left", session);
    }

    @OnError
    public void onError(Session session, @PathParam("component") String component, Throwable throwable) {
        if (component.equalsIgnoreCase("frontend")) {
            frontedSession = null;
            session = backendSession;
        } else if (component.equalsIgnoreCase("backend")) {
            backendSession = null;
            session = frontedSession;
        }
        broadcast("User " + component + " left on error: " + throwable, session);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("component") String component) {
        logger.info("Receiver a message!");
        Session session = null;
        if (component.equalsIgnoreCase("frontend")) {
            session = backendSession;
        } else if (component.equalsIgnoreCase("backend")) {
            session = frontedSession;
        }
        if (message.equalsIgnoreCase("_ready_")) {
            broadcast("User " + component + " joined", session);
        } else {
            broadcast(">> " + component + ": " + message, session);
        }
    }

    private void broadcast(String message, Session session) {
        if (session != null) {
            session.getAsyncRemote().sendObject(message, result -> {
                if (result.getException() != null) {
                    logger.info("Unable to send message: " + result.getException());
                }
            });
        }
    }
}
