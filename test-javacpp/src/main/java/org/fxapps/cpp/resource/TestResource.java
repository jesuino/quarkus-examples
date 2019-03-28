package org.fxapps.cpp.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;;

@Path("/test")
public class TestResource {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        Loader.load();
        IntPointer intPointer = new IntPointer(123);
        int v = intPointer.get();
        intPointer.close();
        return "Native Value is " + v;
        
    }
}