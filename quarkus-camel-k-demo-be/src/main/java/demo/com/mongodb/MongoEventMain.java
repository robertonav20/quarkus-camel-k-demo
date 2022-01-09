package demo.com.mongodb;

import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "event.main")
public class MongoEventMain extends MongoEvent {
}
