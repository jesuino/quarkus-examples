package org.acme;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("developers")
@ApplicationScoped
public class DeveloperResource {

    List<Developer> developers;

    @PostConstruct
    public void init() {
        developers = new ArrayList<>();
        developers.add(new Developer("Duke", "JAVA", 12));
        developers.add(new Developer("John", "PYTHON", 4));
        developers.add(new Developer("Mark", "JAVASCRIPT", 10));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Developer> list() {
        return developers;
    }

    @POST
    public void store(Developer dev) {
        dev.language = dev.language.toUpperCase();        
        developers.add(dev);
    }

}