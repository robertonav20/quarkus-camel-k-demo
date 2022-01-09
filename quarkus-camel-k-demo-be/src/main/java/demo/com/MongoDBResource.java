package demo.com;

import demo.com.mongodb.MongoEventMain;
import demo.com.mongodb.MongoEventOther;
import demo.com.mongodb.MongoEventSecondary;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.util.Collections;
import java.util.List;

@Path("/events")
public class MongoDBResource {

    @GET
    @Path("/{collection}")
    public List getEventsByCollection(
            @PathParam("collection") String collection,
            @QueryParam("size") Integer pageSize,
            @QueryParam("page") Integer pageIndex
    ) {
        Integer size = pageSize != null ? pageSize : 5;
        Integer start = pageIndex != null ? pageIndex * size : 0;
        Integer end = start + size;

        if (collection.equalsIgnoreCase("event.main")) {
            PanacheQuery<MongoEventMain> mongoEvents = MongoEventMain.findAll();
            mongoEvents.page(Page.of(start, end));
            return mongoEvents.list();
        } else if (collection.equalsIgnoreCase("event.secondary")) {
            PanacheQuery<MongoEventSecondary> mongoEvents = MongoEventSecondary.findAll();
            mongoEvents.page(Page.of(start, end));
            return mongoEvents.list();
        } else if (collection.equalsIgnoreCase("event.others")) {
            PanacheQuery<MongoEventOther> mongoEvents = MongoEventOther.findAll();
            mongoEvents.page(Page.of(start, end));
            return mongoEvents.list();
        }
        return Collections.emptyList();
    }
}