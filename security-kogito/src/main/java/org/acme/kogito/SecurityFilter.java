package org.acme.kogito;

import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

/**
 * SecurityFilter
 */
@Provider
public class SecurityFilter implements ContainerRequestFilter {

  final String ADM_ROLE = "admin";

  @Context
  SecurityContext securityContext;

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    if ("POST".equals(requestContext.getMethod())  && ! securityContext.isUserInRole(ADM_ROLE)) {
      throw new WebApplicationException(Response.status(403).entity("User not autorized to start processes").build());
    }
  }
  
}