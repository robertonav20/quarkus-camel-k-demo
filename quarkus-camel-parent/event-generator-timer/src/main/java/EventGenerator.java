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

@ApplicationScoped
public class EventGenerator extends RouteBuilder {
	private final Gson gson = new Gson();

	@ConfigProperty(name = "route.id")
	String name;

	@Override
	public void configure() {
		from("seda:a")
			.log("${body}");

		from("timer:clock?period={{timer.period}}")
			.routeId(name)
			.setHeader("eventType").message(message -> generateType())
			.log("Generated event type ${header.eventType}")
			.choice()
			.when(simple("${header.eventType} < 3"))
			.process(getOpenTracingProcessor())
			.setBody().message(message -> generateEvent((String) message.getHeader("eventType")))
			.log("Send to event.topic.main ${body}")
			.to("seda:a")
			//.to("paho-mqtt5:event.topic.main?brokerUrl=tcp://mosquitto:1883")
			.when(simple("${header.eventType} >= 3 && ${header.eventType} < 7"))
			.process(getOpenTracingProcessor())
			.setBody().message(message -> generateEvent((String) message.getHeader("eventType")))
			.log("Send to event.topic.secondary ${body}")
			.to("seda:a")
			//.to("paho-mqtt5:event.topic.secondary?brokerUrl=tcp://mosquitto:1883")
			.otherwise()
			.process(getOpenTracingProcessor())
			.setBody().message(message -> generateEvent((String) message.getHeader("eventType")))
			.log("Send to event.topic.others ${body}")
			.to("seda:a");
			//.to("paho-mqtt5:event.topic.others?brokerUrl=tcp://mosquitto:1883");

	}

	private String generateType() {
		int random = (int) (Math.random() * 10);
		return String.valueOf(random);
	}

	private String generateEvent(String type) {
		JsonObject event = new JsonObject();
		event.addProperty("id", UUID.randomUUID().toString());
		event.addProperty("date", new Date().toString());
		event.addProperty("name", "EVENT_" + ((int) (Math.random() * 10000)));
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
