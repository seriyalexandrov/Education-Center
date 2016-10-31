package com.aleksandrov.web.services;

import com.aleksandrov.web.services.model.Request;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


@Path("resources")
public class WebService {

    @GET
    @Path("resource")
    @Produces("application/json")
    public Response getResource() {
        return Response.ok("{\"param\" : \"value\"}").build();
    }

    @POST
    @Path("resource")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getRequestData(Request request) {
        request.name = "new" + request.name;
        request.surname = "new" + request.surname;

        return Response.ok(request).build();
    }
}
