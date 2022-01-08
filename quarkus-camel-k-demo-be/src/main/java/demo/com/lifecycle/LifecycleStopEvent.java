package demo.com.lifecycle;

import lombok.Data;

@Data
public class LifecycleStopEvent {
    private String type = "STOP";
    private String component = "backend";
    private String message = "Application is stopping!";
}
