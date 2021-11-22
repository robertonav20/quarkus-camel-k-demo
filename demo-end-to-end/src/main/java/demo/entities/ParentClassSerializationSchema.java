package demo.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema;
import org.apache.kafka.clients.producer.ProducerRecord;
import scala.concurrent.java8.FuturesConvertersImpl;

import javax.annotation.Nullable;

public class ParentClassSerializationSchema implements KafkaSerializationSchema<ParentClass> {
    private String topic;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public ParentClassSerializationSchema(){}

    public ParentClassSerializationSchema(String topic)
    {
        this.topic = topic;
    }
    @Override
    public ProducerRecord<byte[], byte[]> serialize(final ParentClass parentClass, @Nullable final Long aLong) {
        try {
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            return new ProducerRecord<>(topic, objectMapper.writeValueAsBytes(parentClass));
        } catch (JsonProcessingException e) {
           throw new IllegalArgumentException("Could not serialize record " + parentClass, e);
        }
    }
    public byte[] serializeMQTT(final ParentClass parentClass, @Nullable final Long along) {
        try {
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            return objectMapper.writeValueAsBytes(parentClass);
        }catch (JsonProcessingException e)
        {
            throw new IllegalArgumentException("Could not serialize record " + parentClass, e);
        }
    }
}
