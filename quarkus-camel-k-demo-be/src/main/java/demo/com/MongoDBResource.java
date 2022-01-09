package demo.com;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/mongo")
public class MongoDBResource {

    private final MongoClient mongoClient;

    public MongoDBResource(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @GET
    @Path("/{collection}/")
    public MongoCollection getEventsByCollection(
            @PathParam("collection") String collection
    ) {
        return mongoClient.getDatabase("test").getCollection(collection);
    }
}