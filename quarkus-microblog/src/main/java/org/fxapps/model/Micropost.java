package org.fxapps.model;

import java.util.Date;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * Micropost
 */
@Entity
public class Micropost extends PanacheEntity {

    public String author;
    public String content;
    public Date date;

    public static Micropost create(String author, String content) {
        Micropost post = new Micropost();
        post.author = author;
        post.content = content;
        post.date = new Date();
        return post;
    }

}