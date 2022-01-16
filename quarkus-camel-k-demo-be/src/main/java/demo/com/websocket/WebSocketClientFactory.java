package demo.com.websocket;

import demo.com.lifecycle.ApplicationLifecycleListener;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ApplicationScoped
public class WebSocketClientFactory {

    private static final Logger logger = Logger.getLogger(ApplicationLifecycleListener.class);

    @Inject
    private WebSocketClientConfig config;

    @Produces
    private Session getSession() throws URISyntaxException, DeploymentException, IOException {
        logger.info("Connecting to web socket server " + this.config.hostname() + ":" + this.config.port());
        URI uri = new URI("ws://" + this.config.hostname() + ":" + this.config.port() + "/sessions/backend");
        return ContainerProvider.getWebSocketContainer().connectToServer(WebSocketClient.class, uri);
    }
}
