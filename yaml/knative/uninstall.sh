kubectl delete -f https://github.com/knative/serving/releases/download/knative-v1.0.0/serving-core.yaml
kubectl delete -f https://github.com/knative/serving/releases/download/knative-v1.0.0/serving-default-domain.yaml
kubectl delete -f https://github.com/knative/serving/releases/download/knative-v1.0.0/serving-hpa.yaml

kubectl delete -f knative-istio.yaml
kubectl delete -f knative-net-istio.yaml

kubectl delete -f https://github.com/knative/eventing/releases/download/knative-v1.0.0/eventing-crds.yaml
kubectl delete -f https://github.com/knative/eventing/releases/download/knative-v1.0.0/eventing-core.yaml
kubectl delete -f https://github.com/knative/eventing/releases/download/knative-v1.0.0/in-memory-channel.yaml
kubectl delete -f https://github.com/knative/eventing/releases/download/knative-v1.0.0/mt-channel-broker.yaml
kubectl delete -f https://github.com/knative/eventing/releases/download/knative-v1.0.0/mt-channel-broker.yaml

kubectl delete -f https://github.com/jetstack/cert-manager/releases/download/v1.6.1/cert-manager.yaml
kubectl delete -f knative-config-domain.yaml

kn broker create default
