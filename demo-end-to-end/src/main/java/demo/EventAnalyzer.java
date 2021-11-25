package demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.entities.*;
import demo.sink.zeebe.FlinkZeebeConnector;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternFlatSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class EventAnalyzer {

    public static final String CHECKPOINTING_OPTION = "checkpointing";
    public static final String EVENT_TIME_OPTION = "event-time";
    public static final String BACKPRESSURE_OPTION = "backpressure";
    public static final String OPERATOR_CHAINING_OPTION = "chaining";
    public static final Time WINDOW_SIZE = Time.of(10, TimeUnit.SECONDS);
    private static final Logger LOG = LoggerFactory.getLogger(EventAnalyzer.class);
    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        configureEnvironment(params, env);

        boolean inflictBackpressure = params.has(BACKPRESSURE_OPTION);

        String inputTopic = params.get("input-topic", "input");
        String outputTopic = params.get("output-topic", "output");
        String brokers = params.get("bootstrap.servers", "localhost:9092");
        Properties kafkaProps = new Properties();
        String telepassTopic = "telepass";
        String totemTopic = "totem";

        kafkaProps.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        kafkaProps.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "click-event-count");

        KafkaSource<ParentClass> sourceTelepass = KafkaSource.<ParentClass>builder()
                .setTopics(inputTopic)
                .setValueOnlyDeserializer(new ParentClassDeserializer())
                .setProperties(kafkaProps)
                .build();


        DataStream<ParentClass> parentStream = env.fromSource(sourceTelepass, WatermarkStrategy.noWatermarks(), "Parent Source");

        Pattern<ParentClass, ?> parentPatter = Pattern.<ParentClass>begin("first")
                .where(new SimpleCondition<ParentClass>() {
                    @Override
                    public boolean filter(ParentClass parentClass) throws Exception {
                        return parentClass.getType().equalsIgnoreCase("telepass");
                    }
                }).followedBy("second")
                .where(new IterativeCondition<ParentClass>() {
                    @Override
                    public boolean filter(ParentClass parentClass, Context<ParentClass> context) throws Exception {
                        if(!parentClass.getType().equalsIgnoreCase("totem"))
                        {
                            return false;
                        }
                        for(ParentClass firstMatch : context.getEventsForPattern("first")){
                            if(firstMatch.getPayload().get("userId").equals(firstMatch.getPayload().get("userId"))) {
                                return true;
                            }
                        }
                        return false;
                    }
                });


        Pattern<ParentClass, ?> telepassPattern = Pattern.<ParentClass>begin("first")
                .where(new SimpleCondition<ParentClass>() {
                    @Override
                    public boolean filter(ParentClass parentClass) throws Exception {
                     return parentClass.getType().equalsIgnoreCase("telepass");
                    }
                });

        PatternStream<ParentClass> patternStream = CEP.pattern(parentStream,parentPatter).inProcessingTime();

        DataStream<Totem> jobInstance = patternStream.flatSelect(new PatternFlatSelectFunction<ParentClass, Totem>() {
            @Override
            public void flatSelect(Map<String, List<ParentClass>> map, Collector<Totem> collector) throws Exception {
                    ParentClass parent = map.get("second").get(0);
                    ObjectMapper mapper = new ObjectMapper();
                    Totem tot = mapper.convertValue(parent.getPayload(),Totem.class);
                    Telepass tl = tot.getTelepass();
                    collector.collect(new Totem(tl, tot.getTotemId(), tot.getAccessTime()));
            }
        });

        Properties zeebeProperties = new Properties();

        jobInstance.addSink(new FlinkZeebeConnector<>(
                "timerparking",
                new ParkingZeebeSerializationSchema("timerparking"),
                zeebeProperties
        )).name("zeebe sink");

        env.execute();
    }
    private static void configureEnvironment(
            final ParameterTool params,
            final StreamExecutionEnvironment env) {

       // boolean checkpointingEnabled = params.has(CHECKPOINTING_OPTION);
        boolean enableChaining = params.has(OPERATOR_CHAINING_OPTION);

//        if (checkpointingEnabled) {
//            env.enableCheckpointing(1000);
//        }

        if(!enableChaining){
            //disabling Operator chaining to make it easier to follow the Job in the WebUI
            env.disableOperatorChaining();
        }
    }
}
