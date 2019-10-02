package org.jugvale.model;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Evento extends PanacheEntity {


  public String nome;

  public String local;
  
}