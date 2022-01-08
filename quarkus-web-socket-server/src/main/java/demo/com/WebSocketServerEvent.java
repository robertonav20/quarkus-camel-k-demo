package demo.com;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WebSocketServerEvent {
    private String type;
    private String component;
    private String message;
}
