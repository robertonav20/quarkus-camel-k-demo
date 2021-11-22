package demo.entities;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ParentClass {
    private String type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //private Object payload;
private Map<String,Object> payload = new LinkedHashMap<>();

    @JsonAnySetter
    public void setPayload(String key,Object value) {
       payload.put(key, value);
    }

    public Map<String, Object> getPayload() {
        return payload;
    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(type,payload);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if(this == o)
//        {
//            return true;
//        }
//        if( o == null ||getClass() != o.getClass())
//        {
//            return true;
//        }
//        final ParentClass that = (ParentClass) o;
//
//        return Objects.equals(type, that.type) && Objects.equals(payload, that.payload);
//    }

}
