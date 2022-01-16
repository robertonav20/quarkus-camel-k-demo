package demo.com;

import demo.com.mongodb.MongoEvent;
import demo.com.mongodb.MongoEventMain;
import demo.com.mongodb.MongoEventOther;
import demo.com.mongodb.MongoEventSecondary;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/events")
public class MongoDBResource {

    @GET
    @Path("/{collection}")
    public Response getEventsByCollection(
            @PathParam("collection") String collection,
            @QueryParam("size") Integer pageSize,
            @QueryParam("page") Integer pageIndex
    ) {
        Integer size = pageSize != null ? pageSize : 5;
        Integer start = pageIndex != null ? pageIndex * size : 0;
        Integer end = start + size;
        Long total = 0L;
        List<MongoEvent> events = new ArrayList<>();

        if (collection.equalsIgnoreCase("event.main")) {
            PanacheQuery<MongoEventMain> mongoEvents = MongoEventMain.findAll();
            mongoEvents.range(start, end);
            events.addAll(mongoEvents.list());
            total = mongoEvents.count();
        } else if (collection.equalsIgnoreCase("event.secondary")) {
            PanacheQuery<MongoEventSecondary> mongoEvents = MongoEventSecondary.findAll();
            mongoEvents.range(start, end);
            events.addAll(mongoEvents.list());
            total = mongoEvents.count();
        } else if (collection.equalsIgnoreCase("event.others")) {
            PanacheQuery<MongoEventOther> mongoEvents = MongoEventOther.findAll();
            mongoEvents.range(start, end);
            events.addAll(mongoEvents.list());
            total = mongoEvents.count();
        }

        return Response.ok(events).status(200)
                .header("access-control-expose-headers", "*")
                .header("total", total)
                .build();
    }
}