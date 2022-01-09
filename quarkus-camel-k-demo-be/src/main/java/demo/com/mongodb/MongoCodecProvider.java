package demo.com.mongodb;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import javax.inject.Singleton;

@Singleton
public class MongoCodecProvider implements CodecProvider {

    @Override
    public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
        if (aClass.equals(MongoEvent.class)) {
            return (Codec<T>) new MongoEventCodec();
        }
        return null;
    }
}
