package demo.com.mongodb;

import com.mongodb.MongoClientSettings;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class MongoCodecRegistry {

    private final MongoEventCodec mongoEventCodec;
    private final MongoCodecProvider mongoCodecProvider;

    public MongoCodecRegistry(MongoEventCodec mongoEventCodec, MongoCodecProvider mongoCodecProvider) {
        this.mongoEventCodec = mongoEventCodec;
        this.mongoCodecProvider = mongoCodecProvider;
    }

    @Produces
    private CodecRegistry getCodecRegistry() {
        CodecRegistry defaultRegistry = MongoClientSettings.getDefaultCodecRegistry();
        return CodecRegistries.fromRegistries(
                CodecRegistries.fromCodecs(mongoEventCodec),
                CodecRegistries.fromProviders(mongoCodecProvider),
                defaultRegistry
        );
    }
}
