package demo.com;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
public class KubernetesClientService {

    @Produces
    public KubernetesClient kubernetesClient() {
        return new DefaultKubernetesClient();
    }
}
