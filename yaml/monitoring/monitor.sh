kubectl apply -f https://raw.githubusercontent.com/coreos/prometheus-operator/v0.40.0/bundle.yaml
kubectl apply -f monitoring-prometheus.yaml
kubectl apply -f monitoring-prometheus-monitor.yaml
kubectl apply -f monitoring-prometheus-internal.yaml
kubectl apply -f monitoring-prometheus-external.yaml
kubectl apply -f monitoring-prometheus-ingress.yaml

kubectl apply -f monitoring-grafana-persistence.yaml
kubectl apply -f monitoring-grafana.yaml
kubectl apply -f monitoring-grafana-internal.yaml
kubectl apply -f monitoring-grafana-external.yaml
kubectl apply -f monitoring-grafana-ingress.yaml

kubectl apply -f monitoring-camel-k-service-internal.yaml