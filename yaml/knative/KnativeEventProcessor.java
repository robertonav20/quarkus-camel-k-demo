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
 * kamel run --dev KnativeEventProcessor.java -p quarkus.mongodb.connection-string=mongodb://admin:password@mongo:27017/ -t knative-service.min-scale=0
 */

import com.mongodb.DBObject;
import org.apache.camel.builder.RouteBuilder;

public class KnativeEventProcessor extends RouteBuilder {

	@Override
	public void configure() throws Exception {
        from("knative:event/event.topic.main")
            .log("${body}")
			.convertBodyTo(String.class)
        .to("mongodb:camelMongoClient?database=test&collection=event.main&operation=insert")
            .removeHeaders("*")
            .setBody()
                .constant("");

        from("knative:event/event.topic.secondary")
            .log("${body}")
			.convertBodyTo(String.class)
        .to("mongodb:camelMongoClient?database=test&collection=event.secondary&operation=insert")
            .removeHeaders("*")
            .setBody()
                .constant("");
            
        from("knative:event/event.topic.others")
            .log("${body}")
            .convertBodyTo(String.class)
        .to("mongodb:camelMongoClient?database=test&collection=event.others&operation=insert")
            .removeHeaders("*")
            .setBody()
                .constant("");
    }
}
