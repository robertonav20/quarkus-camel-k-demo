package demo.entities;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;

public class TelepassDeserializationSchema implements DeserializationSchema<Telepass> {
    private static final long serialVersionUID = 1L;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Telepass deserialize(byte[] message) throws IOException {
        return objectMapper.readValue(message, Telepass.class);
    }
    public Telepass deserializeMQTT(MqttMessage message) throws IOException {
        return objectMapper.readValue(message.getPayload(), Telepass.class);
    }
    @Override
    public boolean isEndOfStream(Telepass telepass) {
        return false;
    }

    @Override
    public TypeInformation<Telepass> getProducedType() {
        return TypeInformation.of(Telepass.class);
    }
}
