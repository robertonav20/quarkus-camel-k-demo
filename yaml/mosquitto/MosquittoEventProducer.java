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
 */

import org.apache.camel.builder.RouteBuilder;

public class MosquittoEventProducer extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		from("timer:clock?period=5000")
            .setBody()
				.simple(new TelepassIterator().next())
			.log("${body}")
			.to("paho-mqtt5:test.topic?brokerUrl=tcp://mosquitto:1883");
	}

    private String getRandomEvent() {
        return "New random event!";
    }
	static class TelepassIterator{
		Telepass next(){
			String userID = nextUserId();
			return new Telepass(userID);
		}
		private String nextUserId{
			return "User"+ new Random().nextInt(50);
		}
	}
}
public class Telepass {
	private String userID;
	public Telepass(String userID){
		this.userID=userID;
	}
	public String getUserID{
		return userID;
	}
	public void setUserID(String userID){
		this.userID = userID;
	}
}
