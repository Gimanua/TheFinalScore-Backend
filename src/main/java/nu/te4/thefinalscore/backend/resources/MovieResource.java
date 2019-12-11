package nu.te4.thefinalscore.backend.resources;

import nu.te4.thefinalscore.backend.beans.MovieBean;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource {

    @EJB
    MovieBean movieBean;

    @Path("search/{query}")
    @GET
    public Response getMovies(@PathParam("query") String query){
        return movieBean.getMovies(query);
    }

}
