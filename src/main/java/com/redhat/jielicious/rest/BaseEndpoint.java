package com.redhat.jielicious.rest;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import com.redhat.jielicious.storage.StorageHandler;
import io.swagger.annotations.Api;

@Path("/base")
@Api(value = "base")
public class BaseEndpoint {

    @Inject
    private StorageHandler handler;

    private static final String OFFSET = "0";
    private static final String LIMIT = "50";

    @GET
    @Path("/api/v100")
    @Produces(MediaType.APPLICATION_JSON)
    public void getNamespaces(@Context SecurityContext context,
                              @Suspended final AsyncResponse asyncResponse,
                              @QueryParam("offset") @DefaultValue(OFFSET) String offset,
                              @QueryParam("limit") @DefaultValue(LIMIT) String limit) {
        handler.getNamespaces(context, asyncResponse, offset, limit);
    }
}
