kubectl apply -f kubernetes-dashboard-ingress.yamlkube

pushd mosquitto
./mosquitto.sh

pushd ../mongo
./mongo.sh

pushd ../monitoring
./monitor.sh

pushd ../registry
./registry.sh

pushd ../knative
./knative.sh

kubectl apply -f kubernetes-dashboard-ingress.yaml