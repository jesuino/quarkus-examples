package org.cimatech;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Filme extends PanacheEntity {

    public String nome;
    
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name="filme_id")
    public List<Comentario> comentarios;
    
}