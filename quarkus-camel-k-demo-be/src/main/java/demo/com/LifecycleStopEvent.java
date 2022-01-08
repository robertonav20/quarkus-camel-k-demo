package demo.com;

import lombok.Data;

@Data
public class LifecycleStopEvent {
    private String type = "STOP";
    private String message = "Application is stopping!";
}
