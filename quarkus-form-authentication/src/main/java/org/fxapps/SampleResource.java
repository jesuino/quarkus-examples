package org.fxapps;

import java.security.Principal;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/sample")
public class SampleResource {

    final static String MANAGER_ROLE = "manager";
    final static String USER_ROLE = "user";

    @GET
    @Path("info")
    @PermitAll
    public String userInfo(@Context SecurityContext securityContext) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        if (userPrincipal != null) {
            return userPrincipal.getName();
        }
        // translates to "no content" response
        return null;

    }

    @GET
    @PermitAll
    @Path("public")
    @Produces(MediaType.TEXT_PLAIN)
    public String freeForAll() {
        return "Everyone can access this!";
    }

    @GET
    @Path("user_managers")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed({ USER_ROLE, MANAGER_ROLE })
    public String contentForUsersAndManagers() {
        return "Only users and managers can see this!";
    }

    @GET
    @Path("managers")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed({ MANAGER_ROLE })
    public String contentManagers() {
        return "Only MANAGERS can see this!";
    }
}