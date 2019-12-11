package nu.te4.thefinalscore.backend.beans;

import nu.te4.thefinalscore.backend.entities.Movie;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class MovieBean {

    public Response getMovies(String query){
        Response.Status status = Response.Status.OK;
        List<Movie> movies = new ArrayList();

        return Response.status(status).entity(movies).build();
    }
}
