kubectl apply -f mosquitto-config-map.yaml
kubectl apply -f mosquitto-service.yaml
kubectl apply -f mosquitto.yaml

kamel run MosquittoEventProducer.java -d mvn:com.google.code.gson:gson:2.8.9
kamel run MosquittoEventProcessor.java