package demo.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema;
import org.apache.kafka.clients.producer.ProducerRecord;
import scala.concurrent.java8.FuturesConvertersImpl;

import javax.annotation.Nullable;

public class TelepassSerializationSchema implements KafkaSerializationSchema<Telepass> {
    private static final ObjectMapper objectMapper =  new ObjectMapper();
    private String topic;

    public TelepassSerializationSchema()
    {

    }
    public TelepassSerializationSchema(String topic)
    {
        this.topic = topic;
    }


    @Override
    public ProducerRecord<byte[], byte[]> serialize(
            final Telepass telepass, @Nullable final Long timestamp) {
        try {
            return new ProducerRecord<>(topic, objectMapper.writeValueAsBytes(telepass));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not serialize record "+ telepass, e);
        }
    }

    public byte[] serializeMQTT(final Telepass telepass, @Nullable final Long timestamp) {
        try {
            return objectMapper.writeValueAsBytes(telepass);
        }catch (JsonProcessingException e)
        {
            throw new IllegalArgumentException("Could not serialize record " + telepass, e);
        }
    }
    public byte[] serializeMQTTParent(final ParentClass telepass, @Nullable final Long timestamp) {
        try {
            return objectMapper.writeValueAsBytes(telepass);
        }catch (JsonProcessingException e)
        {
            throw new IllegalArgumentException("Could not serialize record " + telepass, e);
        }
    }
}
