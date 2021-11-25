package demo.sink.zeebe;

import org.apache.flink.annotation.PublicEvolving;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.Nullable;
import java.io.Serializable;

@PublicEvolving
public interface ZeebeInstanceSerializationSchema<T, Z> extends Serializable {

    /**
     * Initialization method for the schema. It is called before the actual working methods {@link
     * #serialize(Object, Long)} and thus suitable for one time setup work.
     *
     * <p>The provided {@link SerializationSchema.InitializationContext} can be used to access
     * additional features such as e.g. registering user metrics.
     *
     * @param context Contextual information that can be used during initialization.
     */
    default void open(SerializationSchema.InitializationContext context) throws Exception {}

    /**
     * Serializes given element and returns it as a {@link ProducerRecord}.
     *
     * @param element element to be serialized
     * @param timestamp timestamp (can be null)
     * @return Kafka {@link ZeebeInstance}
     */
    ZeebeInstance<Z> serialize(T element, @Nullable Long timestamp);
}
