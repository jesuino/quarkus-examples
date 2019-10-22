package org.cimatech;

import java.util.List;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("filmes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FilmeResource {

    @GET
    public List<Filme> filmes() {
        return Filme.listAll();
    }
    
    @POST
    @Transactional
    public Filme novo(Filme filme) {
        Filme.persist(filme);
        return filme;
    }
    
    @DELETE
    @Transactional
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
        Filme.delete("id", id);
    }
    
    @POST
    @Transactional
    @Path("{id}/comentario")
    public Response comentar(@PathParam("id") long id, 
                             Comentario comentario) {
        Filme filme = Filme.findById(id);
        if (filme == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        comentario.persist();
        filme.comentarios.add(comentario);
        filme.persist();
        return Response.ok(filme).build();
    }
    
}