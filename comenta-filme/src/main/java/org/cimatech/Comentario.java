package org.cimatech;

import javax.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Comentario extends PanacheEntity {
    
    public String usuario;
    public String comentario;

}
