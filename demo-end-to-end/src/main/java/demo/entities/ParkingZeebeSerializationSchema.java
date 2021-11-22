package demo.entities;

import demo.sink.zeebe.ZeebeInstance;
import demo.sink.zeebe.ZeebeInstanceSerializationSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.Nullable;

public class ParkingZeebeSerializationSchema implements ZeebeInstanceSerializationSchema<Totem, Totem> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private String topic;

    public ParkingZeebeSerializationSchema(String topic)
    {
        this.topic = topic;
    }
    @Override
    public ZeebeInstance<Totem> serialize(final Totem element, @Nullable final Long timestamp) {
        return new ZeebeInstance<>(element);
    }
}
