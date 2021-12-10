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
 * kamel run --dev MosquittoEventProcessor.java -d mvn:com.google.code.gson:gson:2.8.9
 */

import org.apache.camel.builder.RouteBuilder;

public class MosquittoEventProcessor extends RouteBuilder {

	private final String QUEUE_MAIN = "event.topic.main";
	private final String QUEUE_SECONDARY = "event.topic.secondary";
	private final String QUEUE_OTHERS = "event.topic.others";

	@Override
	public void configure() throws Exception {
		from("paho-mqtt5:" + QUEUE_MAIN + "?brokerUrl=tcp://mosquitto:1883")
			.log("${body}")
			.to("kafka:" + QUEUE_MAIN + "?brokers=strimzi-cluster-kafka-brokers:9092");
		
		from("paho-mqtt5:" + QUEUE_SECONDARY + "?brokerUrl=tcp://mosquitto:1883")
			.log("${body}")
			.to("kafka:" + QUEUE_SECONDARY + "?brokers=strimzi-cluster-kafka-brokers:9092");
			
		from("paho-mqtt5:" + QUEUE_OTHERS + "?brokerUrl=tcp://mosquitto:1883")
			.log("${body}")
			.to("kafka:" + QUEUE_OTHERS + "?brokers=strimzi-cluster-kafka-brokers:9092");
	}
}