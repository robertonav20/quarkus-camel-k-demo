package demo.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class ParentClassDeserializer implements DeserializationSchema<ParentClass> {
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final long serialVersionUID = 1L;
    @Override
    public ParentClass deserialize(byte[] bytes) throws IOException {
        return objectMapper.readValue(bytes, ParentClass.class);
    }

    @Override
    public boolean isEndOfStream(ParentClass parentClass) {
        return false;
    }

    @Override
    public TypeInformation<ParentClass> getProducedType() {
        return TypeInformation.of(ParentClass.class);
    }
}
