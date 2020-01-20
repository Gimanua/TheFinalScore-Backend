package nu.te4.thefinalscore.backend.resources;

import nu.te4.thefinalscore.backend.beans.MovieBean;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nu.te4.thefinalscore.backend.entities.Movie;

/**
 * Contains web resources for real world Movies.
 * @author Adrian Klasson
 */
@Path("movie")
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource {

    /**
     * Handles all the logic.
     */
    @EJB
    MovieBean movieBean;

    /**
     * Gets movies based on a search query.
     * @param query What to seaerch for.
     * @return A response containg a list of movie titles.
     */
    @Path("search/{query}")
    @GET
    public Response getMovies(@PathParam("query") String query){
        return movieBean.getMovies(query);
    }

    /**
     * Gets information about a movie.
     * @param title The title of the movie.
     * @return A response containg information of the movie.
     */
    @Path("info/{title}")
    @GET
    public Response getMovie(@PathParam("title") String title){
        return movieBean.getMovie(title);
    }
    
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response saveMovie(Movie movie, @HeaderParam("Authorization") String basicAuth){
        return movieBean.saveMovie(movie, basicAuth);
    }
    
    @Path("saved-movies")
    @GET
    public Response getSavedMovies(@HeaderParam("Authorization") String basicAuth){
        return movieBean.getSavedMovies(basicAuth);
    }
}
