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
 * Read the documentation at https://camel.apache.org/components/2.x/kafka-component.html
 * 
 * kamel run --dev MosquittoEventProcessor.java
 */

import io.opentracing.Span;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.opentracing.OpenTracingSpanAdapter;
import org.apache.camel.tracing.ActiveSpanManager;

public class MosquittoEventProcessor extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("paho-mqtt5:event.topic.main?brokerUrl=tcp://mosquitto:1883")
			.log("${body}")
			.process(getOpenTracingProcessor())
			.to("knative:event/event.topic.main");
		
		from("paho-mqtt5:event.topic.secondary?brokerUrl=tcp://mosquitto:1883")
			.log("${body}")
			.process(getOpenTracingProcessor())
			.to("knative:event/event.topic.secondary");
			
		from("paho-mqtt5:event.topic.others?brokerUrl=tcp://mosquitto:1883")
			.log("${body}")
			.process(getOpenTracingProcessor())
			.to("knative:event/event.topic.others");
	}

	private Processor getOpenTracingProcessor() {
		return exchange -> {
			OpenTracingSpanAdapter spanAdapter = (OpenTracingSpanAdapter) ActiveSpanManager.getSpan(exchange);
			Span span = spanAdapter.getOpenTracingSpan();
			log.info("traceId " + span.context().toTraceId());
			exchange.setProperty("traceId", span.context().toTraceId());
		};
	}
}