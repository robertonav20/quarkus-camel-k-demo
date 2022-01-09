package demo.com;

import demo.com.mongodb.MongoEvent;
import demo.com.mongodb.MongoEventService;
import org.bson.codecs.configuration.CodecRegistry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/mongo")
public class MongoDBResource {

    private final MongoEventService mongoEventService;

    public MongoDBResource(MongoEventService mongoEventService, CodecRegistry codecRegistry) {
        this.mongoEventService = mongoEventService;
    }

    @GET
    @Path("/events/{collection}/")
    public List<MongoEvent> getEventsByCollection(
            @PathParam("collection") String collection
    ) {
        return mongoEventService.getEventsByCollection(collection);
    }
}