package org.cimatech;

import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("comentario")
public class ComentarioResource {

    @DELETE
    @Path("{id}")
    @Transactional
    public void apagar(@PathParam("id") long id) {
        Comentario.delete("id", id);
    }
    
}
