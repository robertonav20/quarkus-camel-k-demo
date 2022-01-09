package demo.com.mongodb;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
public class MongoEvent extends PanacheMongoEntity {
    @BsonProperty("id")
    private String id;
    private String date;
    private String name;
    private String type;
}
