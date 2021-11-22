package demo.sink.zeebe;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.command.CreateProcessInstanceCommandStep1;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.io.Serializable;
import java.util.Properties;


public class FlinkZeebeConnector<IN, OUT> extends RichSinkFunction<IN>
        implements Serializable {

    private String defaultInstanceId;
    private ZeebeInstanceSerializationSchema<IN, OUT> zeebeInstanceSerializationSchema;
    private ZeebeClient zeebeClient;

    /** User defined properties for the Zeebe Client. */
    protected final Properties clientProperties;

    public FlinkZeebeConnector(String instanceId,
                               ZeebeInstanceSerializationSchema<IN, OUT> zeebeInstanceSerializationSchema,
                               Properties clientProperties) {
        this.defaultInstanceId = instanceId;
        this.zeebeInstanceSerializationSchema = zeebeInstanceSerializationSchema;
        this.clientProperties = clientProperties;
    }

    /** Initializes the connection to Kafka. */
    @Override
    public void open(Configuration configuration) throws Exception {
        zeebeClient = ZeebeClient.newClientBuilder().gatewayAddress("zeebe:26500").usePlaintext().build();
    }

    @Override
    public void close() throws Exception {
        zeebeClient.close();
        super.close();
    }

    @Override
    public void invoke(IN next, Context context) throws Exception {

        ZeebeInstance<OUT> zeebeInstance =
                zeebeInstanceSerializationSchema.serialize(next, null);

        CreateProcessInstanceCommandStep1.CreateProcessInstanceCommandStep2 createProcessInstanceCommandStep2 =
                zeebeClient.newCreateInstanceCommand().bpmnProcessId(
                zeebeInstance.getInstanceId() != null ? zeebeInstance.getInstanceId() : defaultInstanceId);
        CreateProcessInstanceCommandStep1.CreateProcessInstanceCommandStep3 createProcessInstanceCommandStep3 =
                zeebeInstance.getVersion() != null ?
                        createProcessInstanceCommandStep2.version(zeebeInstance.getVersion())
                        : createProcessInstanceCommandStep2.latestVersion();
        createProcessInstanceCommandStep3.variables(zeebeInstance.getPayload()).send().join();

    }

}