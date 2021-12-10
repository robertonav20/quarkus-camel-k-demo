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

	@Override
	public void configure() throws Exception {
		from("paho-mqtt5:event.topic.main?brokerUrl=tcp://mosquitto:1883")
			.log("${body}")
			.to("kafka:event.topic.main?brokers=strimzi-cluster-kafka-brokers:9092");
		
		from("paho-mqtt5:event.topic.secondary?brokerUrl=tcp://mosquitto:1883")
			.log("${body}")
			.to("kafka:event.topic.secondary?brokers=strimzi-cluster-kafka-brokers:9092");
			
		from("paho-mqtt5:event.topic.others?brokerUrl=tcp://mosquitto:1883")
			.log("${body}")
			.to("kafka:event.topic.others?brokers=strimzi-cluster-kafka-brokers:9092");
	}
}