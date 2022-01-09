package demo.com.mongodb;

import com.mongodb.MongoClientSettings;
import org.bson.*;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class MongoEventCodec implements CollectibleCodec<MongoEvent> {

    private final Codec<Document> documentCodec;

    public MongoEventCodec() {
        this.documentCodec = MongoClientSettings.getDefaultCodecRegistry().get(Document.class);
    }

    @Override
    public MongoEvent generateIdIfAbsentFromDocument(MongoEvent mongoEvent) {
        if (!documentHasId(mongoEvent)) {
            mongoEvent.setId(UUID.randomUUID().toString());
        }
        return mongoEvent;
    }

    @Override
    public boolean documentHasId(MongoEvent mongoEvent) {
        return false;
    }

    @Override
    public BsonValue getDocumentId(MongoEvent mongoEvent) {
        return new BsonString(mongoEvent.getId());
    }

    @Override
    public MongoEvent decode(BsonReader bsonReader, DecoderContext decoderContext) {
        Document document = documentCodec.decode(bsonReader, decoderContext);
        MongoEvent mongoEvent = new MongoEvent();
        if (document.getString("id") != null) {
            mongoEvent.setId(document.getString("id"));
        }
        mongoEvent.setName(document.getString("name"));
        mongoEvent.setType(document.getString("type"));
        mongoEvent.setDate(document.getString("date"));
        return mongoEvent;
    }

    @Override
    public void encode(BsonWriter bsonWriter, MongoEvent mongoEvent, EncoderContext encoderContext) {
        Document doc = new Document();
        doc.put("id", mongoEvent.getId());
        doc.put("name", mongoEvent.getName());
        doc.put("date", mongoEvent.getDate());
        doc.put("type", mongoEvent.getType());
        documentCodec.encode(bsonWriter, doc, encoderContext);
    }

    @Override
    public Class<MongoEvent> getEncoderClass() {
        return MongoEvent.class;
    }
}
