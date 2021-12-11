package demo.com;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/hello")
public class GreetingResource {

    private final KubernetesClient kubernetesClient;

    public GreetingResource(KubernetesClient kubernetesClient) {
            this.kubernetesClient = kubernetesClient;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

    @GET
    @Path("/{namespace}")
    public List<Pod> pods(@PathParam("namespace") String namespace) {
        return kubernetesClient.pods().inNamespace(namespace).list().getItems();
    }
}