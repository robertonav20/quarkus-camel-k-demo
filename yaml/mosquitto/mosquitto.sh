kubectl apply -f mosquitto-config-map.yaml
kubectl apply -f mosquitto-service.yaml
kubectl apply -f mosquitto.yaml
kamel run --trait knative.enabled=true \
    --trait knative.auto=true \
    --trait knative-service.enabled=true \
    --trait knative-service.auto=true \
    Producer.java