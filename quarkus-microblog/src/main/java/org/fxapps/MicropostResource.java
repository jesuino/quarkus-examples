package org.fxapps;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.fxapps.model.Micropost;
import io.quarkus.panache.common.Sort;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

@Path("/micropost")
@ApplicationScoped
public class MicropostResource {

    @Inject
    Template postsTemplate;

    @PostConstruct
    @Transactional
    public void start() {
        Micropost.persist(new Micropost[] { Micropost.create("Antonio", "I love playing with my Legos"),
                Micropost.create("Luana", "I like Painting and I should do it more often"),
                Micropost.create("William", "I love Java") });
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getPostsHtml() {
        final List<Micropost> posts = Micropost.findAll(Sort.descending("date")).list();
        return postsTemplate.data("posts", posts);
    }

    @POST
    @Transactional
    public void create(@FormParam("author") String author, @FormParam("content") String content) {
        Micropost.create(author, content).persist();

    }

}