kubectl apply -f mosquitto-config-map.yaml
kubectl apply -f mosquitto-service.yaml
kubectl apply -f mosquitto.yaml

kamel run --trait knative.enabled=true \
    --trait knative.auto=true \
    --trait knative-service.enabled=true \
    --trait knative-service.auto=true \
    MosquittoEventProducer.java \
    -d mvn:com.google.code.gson:gson:2.8.9

kamel run --trait knative.enabled=true \
    --trait knative.auto=true \
    --trait knative-service.enabled=true \
    --trait knative-service.auto=true \
    MosquittoEventProcessor.java \
    -d mvn:com.google.code.gson:gson:2.8.9