package demo.com;

import io.fabric8.kubernetes.api.model.LabelSelector;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.HashMap;
import java.util.List;

@Path("/")
public class KubernetesResource {

    private final KubernetesClient kubernetesClient;

    public KubernetesResource(KubernetesClient kubernetesClient) {
        this.kubernetesClient = kubernetesClient;
    }

    @GET
    @Path("/namespaces")
    public List<Namespace> getNamespaces() {
        return kubernetesClient.namespaces().list().getItems();
    }

    @GET
    @Path("/pods/{namespace}/")
    public List<Pod> podsByNamespace(
        @PathParam("namespace") String namespace
    ) {
        return kubernetesClient.pods().inNamespace(namespace).list().getItems();
    }

    @GET
    @Path("/pods/{namespace}/{label}")
    public List<Pod> podsByLabel(
        @PathParam("namespace") String namespace,
        @PathParam("label") String label
    ) {
        return kubernetesClient.pods().inNamespace(namespace).withLabel(label).list().getItems();
    }

    @GET
    @Path("/pods/{namespace}/{label}/{value}")
    public List<Pod> podsByLabelValue(
        @PathParam("namespace") String namespace,
        @PathParam("label") String label,
        @PathParam("value") String value
    ) {
        HashMap<String, String> labelsMap = new HashMap<>();
        labelsMap.put(label, value);

        LabelSelector labelSelector = new LabelSelector();
        labelSelector.setMatchLabels(labelsMap);

        return kubernetesClient.pods().inNamespace(namespace).withLabelSelector(labelSelector).list().getItems();
    }
}