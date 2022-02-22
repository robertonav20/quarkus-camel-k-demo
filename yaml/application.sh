kamel run mosquitto/MosquittoEventProducer.java -d mvn:com.google.code.gson:gson:2.8.9 \
    --trait tracing.enabled=true \
    --trait tracing.auto=true \
    --trait tracing.service-name=mosquitto-events \
    --trait tracing.endpoint=http://jaeger-collector:14268/api/traces

kamel run mosquitto/MosquittoEventProcessor.java \
    --trait tracing.enabled=true \
    --trait tracing.auto=true \
    --trait tracing.service-name=mosquitto-events \
    --trait tracing.endpoint=http://jaeger-collector:14268/api/traces

kamel run knative/KnativeEventProcessor.java -p quarkus.mongodb.connection-string=mongodb://admin:password@mongo:27017/ \
    --trait tracing.enabled=true \
    --trait tracing.auto=true \
    --trait tracing.service-name=mosquitto-events \
    --trait tracing.endpoint=http://jaeger-collector:14268/api/traces

pushd quarkus
./quarkus.sh