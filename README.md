Download binary files
1 - minikube
2 - kubectl
3 - kn

Install KVM2 driver
1 - sudo apt install qemu-kvm libvirt-daemon-system libvirt-clients bridge-utils
2 - sudo adduser $(whoami) libvirt
3 - sudo adduser $(whoami) kvm

Minikube permission
1 - sudo usermod -a -G libvirt $(whoami)
2 - newgrp libvirt
3 -

minikube start --memory=8192 --cpus=6 \
  --kubernetes-version=v1.16.0 \
  --vm-driver=kvm2 \
  --disk-size=30g \
  --addons registry \
  --extra-config=apiserver.enable-admission-plugins="LimitRanger,NamespaceExists,NamespaceLifecycle,ResourceQuota,ServiceAccount,DefaultStorageClass,MutatingAdmissionWebhook"

Install Istio Namespace
4 - 

kubectl apply --filename https://raw.githubusercontent.com/knative/serving/v0.5.2/third_party/istio-1.0.7/istio-crds.yaml &&
curl -L https://raw.githubusercontent.com/knative/serving/v0.5.2/third_party/istio-1.0.7/istio.yaml \
  | sed 's/LoadBalancer/NodePort/' \
  | kubectl apply --filename -

# Label the default namespace with istio-injection=enabled.
kubectl label namespace default istio-injection=enabled

Install knative services
5 - 

kubectl apply --selector knative.dev/crd-install=true \
   --filename https://github.com/knative/serving/releases/download/v0.7.0/serving.yaml \
   --filename https://github.com/knative/eventing/releases/download/v0.7.0/eventing.yaml \
   --filename https://github.com/knative/serving/releases/download/v0.7.0/monitoring.yaml

6 - minikube dashboard
7 - Download binary kamel-runtime

Get the cluster ip of internal registry of minikube
8 - kubectl get service --namespace kube-system
9 - kamel install --registry cluster-ip-of-registry