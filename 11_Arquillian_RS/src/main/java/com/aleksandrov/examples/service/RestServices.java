package com.aleksandrov.examples.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/books")
public class RestServices {

    @GET
    @Path("{name}")
    public String get(@PathParam("name") String bookName) {
        return bookName;
    }
}
