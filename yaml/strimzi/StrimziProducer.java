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
 */

import org.apache.camel.builder.RouteBuilder;

public class Producer extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		from("timer:clock?period=5000")
            .setBody()
				.simple(getRandomEvent())
			.log("${body}")
			.to("kafka:test.topic?brokers=strimzi-cluster-kafka-brokers:9092");

		// Test if kafka receive message
		from("kafka:test.topic?brokers=strimzi-cluster-kafka-brokers:9092")
			.log("${body}");
	}

    private String getRandomEvent() {
        return "New random event!";
    }
}