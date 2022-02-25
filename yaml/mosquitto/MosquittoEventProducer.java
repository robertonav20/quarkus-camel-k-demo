/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Read the documentation at https://camel.apache.org/components/next/paho-mqtt5-component.html#_samples
 *
 * kamel run --dev MosquittoEventProducer.java -d mvn:com.google.code.gson:gson:2.8.9
 */

import com.google.gson.Gson;
import com.google.gson.JsonObject;
 import io.opentracing.Span;
import org.apache.camel.Processor;
import org.apache.camel.PropertyInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.opentracing.OpenTracingSpanAdapter;
import org.apache.camel.tracing.ActiveSpanManager;

import java.util.Date;
import java.util.UUID;

public class MosquittoEventProducer extends RouteBuilder {

	private final Gson gson = new Gson();

	@PropertyInject("env:CAMEL_K_INTEGRATION") String name;

	@Override
	public void configure() throws Exception {
		//bindTracer();
		getContext().setUseMDCLogging(true);

		from("timer:clock?period=5000")
			.routeId(name)
			.setHeader("eventType").message(message -> generateType())
			.log("Generated event type ${header.eventType}")
			.choice()
				.when(simple("${header.eventType} < 3"))
					.process(getOpenTracingProcessor())
					.setBody().message(message -> generateEvent((String) message.getHeader("eventType")))
					.log("Send to event.topic.main ${body}")
					.to("paho-mqtt5:event.topic.main?brokerUrl=tcp://mosquitto:1883")
				.when(simple("${header.eventType} >= 3 && ${header.eventType} < 7"))
					.process(getOpenTracingProcessor())
					.setBody().message(message -> generateEvent((String) message.getHeader("eventType")))
					.log("Send to event.topic.secondary ${body}")
					.to("paho-mqtt5:event.topic.secondary?brokerUrl=tcp://mosquitto:1883")
				.otherwise()
					.process(getOpenTracingProcessor())
					.setBody().message(message -> generateEvent((String) message.getHeader("eventType")))
					.log("Send to event.topic.others ${body}")
					.to("paho-mqtt5:event.topic.others?brokerUrl=tcp://mosquitto:1883");
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
			OpenTracingSpanAdapter spanAdapter = (OpenTracingSpanAdapter) ActiveSpanManager.getSpan(exchange);
			Span span = spanAdapter.getOpenTracingSpan();
			String traceId = span.context().toTraceId();
			log.info("traceId {}", traceId);
			exchange.setProperty("traceId", traceId);
			exchange.getMessage().setHeader("traceId", traceId);
		};
	}
}