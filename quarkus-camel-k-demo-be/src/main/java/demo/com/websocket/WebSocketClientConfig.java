package demo.com.websocket;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "web-socket-server")
interface WebSocketClientConfig {
    String hostname();
    String port();
}
