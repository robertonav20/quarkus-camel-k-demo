package demo.com.websocket;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Singleton
public class WebSocketClientFactory {

    @Inject
    private WebSocketClientConfig config;

    @Produces
    private Session getSession() throws URISyntaxException, DeploymentException, IOException {
        URI uri = new URI("ws://" + this.config.hostname() + ":" + this.config.port() + "/sessions/backend");
        return ContainerProvider.getWebSocketContainer().connectToServer(WebSocketClient.class, uri);
    }
}
