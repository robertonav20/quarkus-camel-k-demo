package demo.com.lifecycle;

import lombok.Data;

@Data
public class LifecycleStartupEvent {
    private String type = "START";
    private String component = "backend";
    private String message = "Application is starting!";
}
