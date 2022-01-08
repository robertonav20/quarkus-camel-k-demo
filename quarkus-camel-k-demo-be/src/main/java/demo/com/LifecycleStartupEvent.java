package demo.com;

import lombok.Data;

@Data
public class LifecycleStartupEvent {
    private String type = "START";
    private String message = "Application is starting!";
}
