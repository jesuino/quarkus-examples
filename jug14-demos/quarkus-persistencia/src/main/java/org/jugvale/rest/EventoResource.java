package org.jugvale.rest;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jugvale.model.Evento;

/**
 * EventoResource
 */
@Path("evento")
public class EventoResource {


  @GET
  @Produces("application/json")
  public List<Evento> todos() {
    return Evento.listAll();
  }


  @PUT
  @Consumes("application/json")
  @Transactional
  public void cria(Evento evento) {
    Evento.persist(evento);
  }

@DELETE
@Path("{id}")
@Transactional
public void apaga(@PathParam("id") long id) {
  Evento.delete("id", id);
 }
  
}