package demo.com.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class MongoEventService {

    private final MongoClient mongoClient;
    private final CodecRegistry codecRegistry;

    public MongoEventService(MongoClient mongoClient, CodecRegistry codecRegistry) {
        this.mongoClient = mongoClient;
        this.codecRegistry = codecRegistry;
    }

    public List<MongoEvent> getEventsByCollection(String collection) {
        List<MongoEvent> list = new ArrayList<>();
        MongoCursor<MongoEvent> cursor = getCollection(collection)
            .withCodecRegistry(codecRegistry)
            .find()
            .iterator();

        try {
            while (cursor.hasNext()) {
                list.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    private MongoCollection<MongoEvent> getCollection(String collection) {
        return this.mongoClient
                .getDatabase("test")
                .getCollection(collection, MongoEvent.class);
    }
}
