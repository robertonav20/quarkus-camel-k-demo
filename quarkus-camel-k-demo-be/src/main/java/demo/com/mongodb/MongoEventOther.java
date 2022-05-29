package demo.com.mongodb;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;

@Data
@MongoEntity(collection = "event.others")
public class MongoEventOther extends MongoEvent {
}
