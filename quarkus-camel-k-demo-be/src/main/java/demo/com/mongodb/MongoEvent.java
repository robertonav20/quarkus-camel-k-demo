package demo.com.mongodb;

import lombok.Data;

@Data
public class MongoEvent {
    private String id;
    private String date;
    private String name;
    private String type;
}
