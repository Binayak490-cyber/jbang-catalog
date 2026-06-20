/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.platform.destination.api;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import io.debezium.jbang.core.platform.destination.dto.DestinationRequest;
import io.debezium.jbang.core.platform.destination.dto.DestinationResponse;

@Path("/api/destinations")
public interface DestinationAPI {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<DestinationResponse> listDestinations();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    DestinationResponse getDestination(@PathParam("id") Long id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    DestinationResponse createDestination(DestinationRequest request);

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    DestinationResponse updateDestination(@PathParam("id") Long id, DestinationRequest request);

    @DELETE
    @Path("/{id}")
    void deleteDestination(@PathParam("id") Long id);
}
