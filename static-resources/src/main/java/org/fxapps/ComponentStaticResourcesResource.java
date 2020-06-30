package org.fxapps;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/dashbuilder-external-server/")
public class ComponentStaticResourcesResource {

    @Inject
    ComponentStaticFileService componentStaticFileService;

    @GET
    @Path("component/{componentId}/{fileName: .*}")
    public Response hello(@PathParam("componentId") String componentId,
                          @PathParam("fileName") String fileName) {

        // TODO: Add cache to never expires in prod
        try {
            InputStream fileStream = componentStaticFileService.getComponentFile(componentId,
                                                                                  fileName);
            return Response.ok(fileStream).build();
        } catch (FileNotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
}