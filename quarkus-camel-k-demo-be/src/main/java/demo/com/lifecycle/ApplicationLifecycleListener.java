package demo.com.lifecycle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.websocket.Session;

@ApplicationScoped
public class ApplicationLifecycleListener {

    private static final Logger logger = Logger.getLogger(ApplicationLifecycleListener.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    private Session session;

    void onStart(@Observes StartupEvent event) throws JsonProcessingException {
        logger.info("The application is starting...");
        this.session.getAsyncRemote().sendText(this.objectMapper.writeValueAsString(new LifecycleStartupEvent()));
    }

    void onStop(@Observes ShutdownEvent event) throws JsonProcessingException {
        logger.info("The application is stopping...");
        this.session.getAsyncRemote().sendText(this.objectMapper.writeValueAsString(new LifecycleStopEvent()));
    }
}
