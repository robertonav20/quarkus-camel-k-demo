package com.example;

import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Processor extends RouteBuilder {
    @ConfigProperty(name = "route.id")
    String name;

    private static final String LOG_ROUTE = "jetty:http://localhost:{{route.http.port}}/test";

    @Override
    public void configure() {
        from(LOG_ROUTE)
            .routeId(name)
            .log("${body}");
    }
}