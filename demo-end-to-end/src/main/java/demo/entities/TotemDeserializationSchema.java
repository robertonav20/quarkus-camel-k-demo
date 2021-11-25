package demo.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class TotemDeserializationSchema implements DeserializationSchema<Totem> {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Totem deserialize(byte[] bytes) throws IOException {
        return objectMapper.readValue(bytes, Totem.class);
    }

    @Override
    public boolean isEndOfStream(Totem totem) {
        return false;
    }

    @Override
    public TypeInformation<Totem> getProducedType() {
          return TypeInformation.of(Totem.class);
    }
}
