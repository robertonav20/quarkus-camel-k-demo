package demo.com;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class Mapper {

    @Produces
    private ObjectMapper getMapper() {
        return new ObjectMapper();
    }
}
