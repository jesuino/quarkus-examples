package org.giu.resources;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.giu.model.Band;
import org.giu.storage.BandStorage;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

/**
 *  REST Service for Bands
 */
@Path("band")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class BandResource {

    @Inject
    private BandStorage storage;

    @GET
    public Collection<Band> all() {
        return storage.all();
    }

    @PUT
    public Response store(Band band) {
        storage.store(band);
        return Response.status(CREATED).build();
    }

    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") int id) {
        Response response = Response.status(NOT_FOUND).build();
        boolean wasRemoved = storage.remove(id);
        if (wasRemoved) {
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("search/name/{name}")
    public List<Band> searchByName(@QueryParam("name") String name) {
        return storage.searchByName(name);
    }
    
    @GET
    @Path("search/year/{year}")
    public List<Band> searchByYear(@QueryParam("year") int year) {
        return storage.searchByYear(year);
    }

}