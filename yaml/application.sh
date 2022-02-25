kamel run mosquitto/MosquittoEventProducer.java --name mosquitto-event-producer \
    -d mvn:com.google.code.gson:gson:2.8.9 \
    -d camel-opentracing \
    --trait tracing.enabled=true \
    --trait tracing.auto=true \
    --trait tracing.endpoint=http://jaeger-collector:14268/api/traces
    
kamel run mosquitto/MosquittoEventProcessor.java --name mosquitto-event-processor \
    -d camel-opentracing \
    --trait tracing.enabled=true \
    --trait tracing.auto=true \
    --trait tracing.endpoint=http://jaeger-collector:14268/api/traces
    
kamel run knative/KnativeEventProcessor.java --name knative-event-processor \
    -p quarkus.mongodb.connection-string=mongodb://admin:password@mongo:27017/ \
    -d camel-opentracing \
    --trait tracing.enabled=true \
    --trait tracing.auto=true \
    --trait tracing.endpoint=http://jaeger-collector:14268/api/traces
    
pushd quarkus
./quarkus.sh