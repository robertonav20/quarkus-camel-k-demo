package demo.entities;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.Nullable;

public class TotemSerializationSchema implements KafkaSerializationSchema<Totem> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private String topic;

    public TotemSerializationSchema(){}
    public TotemSerializationSchema(String topic) {
        this.topic = topic;
    }

    @Override
    public ProducerRecord<byte[], byte[]> serialize(Totem totem, @Nullable Long aLong) {
        try {
            return new ProducerRecord<>(topic, objectMapper.writeValueAsBytes(totem));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not serialize record: "+ totem, e);
        }
    }

    public byte[] serializeMQTT(final Totem totem, @Nullable final Long timestamp) {
        try {
            return objectMapper.writeValueAsBytes(totem);
        }catch (JsonProcessingException e)
        {
            throw new IllegalArgumentException("Could not serialize record " + totem, e);
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
