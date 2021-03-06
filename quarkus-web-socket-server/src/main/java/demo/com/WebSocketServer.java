package demo.com;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/sessions/{component}")
@ApplicationScoped
public class WebSocketServer {

    private static final Logger logger = Logger.getLogger(WebSocketServer.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Session frontedSession;
    private Session backendSession;

    @OnOpen
    public void onOpen(Session session, @PathParam("component") String component) throws JsonProcessingException {
        logger.info("Received an open message from component " + component + "!");
        if (component.equalsIgnoreCase("frontend")) {
            frontedSession = session;
        } else if (component.equalsIgnoreCase("backend")) {
            backendSession = session;
        }

        if (frontedSession != null && backendSession != null) {
            WebSocketServerEvent event = WebSocketServerEvent.builder()
                .type("START")
                .component("backend")
                .message("The backend join!")
                .build();
            broadcast("frontend", this.objectMapper.writeValueAsString(event), session);
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("component") String component) throws JsonProcessingException {
        logger.info("Received a close message from component " + component + "!");
        String destination = null;
        if (component.equalsIgnoreCase("frontend")) {
            frontedSession = null;
            session = backendSession;
            destination = "backend";
        } else if (component.equalsIgnoreCase("backend")) {
            backendSession = null;
            session = frontedSession;
            destination = "frontend";
        }
        WebSocketServerEvent event = WebSocketServerEvent.builder()
            .type("STOP")
            .component(component)
            .message("The " + component + " left!")
            .build();
        broadcast(destination, this.objectMapper.writeValueAsString(event), session);
    }

    @OnError
    public void onError(Session session, @PathParam("component") String component, Throwable throwable) throws JsonProcessingException {
        logger.info("Received an error message from component " + component + "!");
        String destination = null;
        if (component.equalsIgnoreCase("frontend")) {
            frontedSession = null;
            session = backendSession;
            destination = "backend";
        } else if (component.equalsIgnoreCase("backend")) {
            backendSession = null;
            session = frontedSession;
            destination = "frontend";
        }
        WebSocketServerEvent event = WebSocketServerEvent.builder()
                .type("STOP")
                .component(component)
                .message("The " + component + " left with error!")
                .build();
        broadcast(destination, this.objectMapper.writeValueAsString(event), session);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("component") String component) {
        logger.info("Received a message : " + message);
        Session session = null;
        String destination = null;
        if (component.equalsIgnoreCase("frontend")) {
            session = backendSession;
            destination = "backend";
        } else if (component.equalsIgnoreCase("backend")) {
            session = frontedSession;
            destination = "frontend";
        }
        broadcast(destination, message, session);
    }

    private void broadcast(String component, String message, Session session) {
        if (session != null) {
            logger.info("Sending message to " + component);
            session.getAsyncRemote().sendObject(message, result -> {
                if (result.getException() != null) {
                    logger.info("Unable to send message: " + result.getException());
                }
            });
        }
    }
}
