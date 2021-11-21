
# Prerequisites
- minikube at https://minikube.sigs.k8s.io/docs/start/
- kubectl https://kubernetes.io/docs/tasks/tools/install-kubectl-linux/

# Minikube VM driver
Execute all follow commands for to enable access :

     sudo apt install qemu-kvm libvirt-daemon-system libvirt-clients bridge-utils
     sudo adduser $(whoami) libvirt
     sudo adduser $(whoami) kvm

  
# Minikube permission

    sudo usermod -a -G libvirt $(whoami)
    newgrp libvirt

# Minikube
Launch minikube with : 

 - 6 CPU
 - 8GB ram
 - 30GB size
 - 1.20 Kubernetes
 - KVM2 driver

        minikube start --memory=8192 --cpus=6 \
          --kubernetes-version=v1.20.0 \
          --vm-driver=kvm2 \
          --disk-size=30g \
          --addons registry \
          --extra-config=apiserver.enable-admission-plugins="LimitRanger,NamespaceExists,NamespaceLifecycle,ResourceQuota,ServiceAccount,DefaultStorageClass,MutatingAdmissionWebhook"

**If minikube has stopped, you must execute again Minikube permission**

# Istio

     kubectl apply --filename https://raw.githubusercontent.com/knative/serving/v0.5.2/third_party/istio-1.0.7/istio-crds.yaml &&
                curl -L https://raw.githubusercontent.com/knative/serving/v0.5.2/third_party/istio-1.0.7/istio.yaml \
                  | sed 's/LoadBalancer/NodePort/' \
                  | kubectl apply --filename -      
      kubectl label namespace default istio-injection=enabled


# Knative
Install knative core component :
1. knative-serving 
	```
	kubectl apply -f https://github.com/knative/serving/releases/download/knative-v1.0.0/serving-crds.yaml
	kubectl apply -f https://github.com/knative/serving/releases/download/knative-v1.0.0/serving-core.yaml
	```
2. knative-eventing
	```
	kubectl apply -f https://github.com/knative/eventing/releases/download/knative-v1.0.0/eventing-crds.yaml
	kubectl apply -f https://github.com/knative/eventing/releases/download/knative-v1.0.0/eventing-core.yaml
	```
# Access Kubernetes Dashboard
Access to kubernetes dashboard from browser

    minikube dashboard

# Install Camel-K
1. Download binary kamel-cli
	  ```
	  curl -L https://github.com/apache/camel-k/releases/download/v1.7.0/camel-k-client-1.7.0-linux-64bit.tar.gz
	tar -xf camel-k-client-1.7.0-linux-64bit.tar.gz
	```
2. Move kamel-cli
`sudo mv kamel /usr/local/bin`
3. Read cluster ip registry of minikube
`kubectl get service --namespace kube-system`
5. Install kamel
   `kamel install --registry cluster-ip-registry`


# Apache Camel-K Documentations
- Install guide : https://camel.apache.org/camel-k/next/installation/installation.html.
- Component : https://camel.apache.org/components/3.13.x/index.html

