package event.generator.timer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.tracing.ActiveSpanManager;
import org.apache.camel.tracing.SpanAdapter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.util.Date;
import java.util.UUID;
import java.util.Random;

@ApplicationScoped
public class Generator extends RouteBuilder {
	private final Gson gson = new Gson();
	private final Random random = new Random();
	private static final String EVENT_TYPE_KEY = "eventType";
	private static final String LOG_ROUTE = "seda:a";

	@ConfigProperty(name = "route.id")
	String name;

	@Override
	public void configure() {
		from(LOG_ROUTE)
			.log("${body}");

		from("timer:clock?period={{timer.period}}")
			.routeId(name)
			.setHeader(EVENT_TYPE_KEY).message(message -> generateType())
			.log("Generated event type ${header.eventType}")
			.choice()
			.when(simple("${header.eventType} < 3"))
			.process(getOpenTracingProcessor())
			.setBody().message(message -> generateEvent((String) message.getHeader(EVENT_TYPE_KEY)))
			.log("Send to event.topic.main ${body}")
			.to(LOG_ROUTE)
			//.to("paho-mqtt5:event.topic.main?brokerUrl=tcp://mosquitto:1883")
			.when(simple("${header.eventType} >= 3 && ${header.eventType} < 7"))
			.process(getOpenTracingProcessor())
			.setBody().message(message -> generateEvent((String) message.getHeader(EVENT_TYPE_KEY)))
			.log("Send to event.topic.secondary ${body}")
			.to(LOG_ROUTE)
			//.to("paho-mqtt5:event.topic.secondary?brokerUrl=tcp://mosquitto:1883")
			.otherwise()
			.process(getOpenTracingProcessor())
			.setBody().message(message -> generateEvent((String) message.getHeader(EVENT_TYPE_KEY)))
			.log("Send to event.topic.others ${body}")
			.to(LOG_ROUTE);
			//.to("paho-mqtt5:event.topic.others?brokerUrl=tcp://mosquitto:1883");

	}

	private String generateType() {
		return String.valueOf(this.random.nextInt(10));
	}

	private String generateEvent(String type) {
		JsonObject event = new JsonObject();
		event.addProperty("id", UUID.randomUUID().toString());
		event.addProperty("date", new Date().toString());
		event.addProperty("name", "EVENT_" + this.random.nextInt(100000));
		event.addProperty("type", type);

		return gson.toJson(event);
	}

	private Processor getOpenTracingProcessor() {
		return exchange -> {
			SpanAdapter spanAdapter = ActiveSpanManager.getSpan(exchange);
			String traceId = spanAdapter.traceId();
			log.info("traceId {}", traceId);
			exchange.setProperty("traceId", traceId);
			exchange.getMessage().setHeader("traceId", traceId);
		};
	}
}
