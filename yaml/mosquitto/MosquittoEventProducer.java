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

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.lang.Integer;
import java.util.Date;
import java.util.UUID;

public class MosquittoEventProducer extends RouteBuilder {
	
	private Gson gson = new Gson();

	@Override
	public void configure() throws Exception {
		from("timer:clock?period=5000")
			.setBody().message(message -> getRandomEvent())
			.log("${body}")
			.to("paho-mqtt5:test.topic?brokerUrl=tcp://mosquitto:1883");
	}

    private String getRandomEvent() {
		JsonObject obj = new JsonObject();
		obj.addProperty("id", UUID.randomUUID().toString());
		obj.addProperty("date", new Date().toString());
		obj.addProperty("name", "EVENT_" + ((int)(Math.random() * 10000)));
		obj.addProperty("type", "EVENT");
		obj.addProperty("type", "EVENT");

        return gson.toJson(obj);
    }
}