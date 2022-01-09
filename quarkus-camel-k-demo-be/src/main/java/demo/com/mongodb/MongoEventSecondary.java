package demo.com.mongodb;

import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "event.secondary")
public class MongoEventSecondary extends MongoEvent {
}
