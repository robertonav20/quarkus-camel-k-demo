kamel run mosquitto/MosquittoEventProducer.java -d mvn:com.google.code.gson:gson:2.8.9
kamel run mosquitto/MosquittoEventProcessor.java
kamel run knative/KnativeEventProcessor.java -p quarkus.mongodb.connection-string=mongodb://admin:password@mongo:27017/ -t knative-service.min-scale=0