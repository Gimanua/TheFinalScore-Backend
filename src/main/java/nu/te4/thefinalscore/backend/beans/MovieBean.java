package nu.te4.thefinalscore.backend.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import nu.te4.thefinalscore.backend.entities.Movie;
import nu.te4.thefinalscore.backend.entities.Rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains logic for everything related to movies.
 * @author Adrian Klasson
 */
@Stateless
public class MovieBean {

    /**
     * Logs information.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieBean.class);
    
    /**
     * The API key to the MovieDB.
     */
    private final String movieDbApiKey = "be96f5a969c038665fdfab7c659f24bf";
    
    /**
     * The API key to the OMDB.
     */
    private final String omdbApiKey = "8574ffd";

    /**
     * Gets a list of movie titles based on a search query.
     * @param query What you search for.
     * @return A response containing a list of movie titles if successful, oterhwise internal server error.
     */
    public Response getMovies(String query) {
        try {
            LOGGER.info("Retrieving movie titles.");
            HttpResponse<JsonNode> response = Unirest.get("https://api.themoviedb.org/3/search/movie?include_adult=false&page=1&query={query}&language=en-US&api_key={apiKey}")
                            .routeParam("query", query)
                            .routeParam("apiKey", movieDbApiKey)
                            .asJson();

            if (!response.isSuccess()) {
                LOGGER.info("Retrieved no results with this query: {}", query);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            JSONObject obj = response.getBody().getObject();
            return Response.ok().entity(obj.toString()).build();
        } catch (Exception ex) {
            LOGGER.error("Retrieving movie titles failed: {}", ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Gets information about a movie.
     * @param title The title of the movie.
     * @return A response containg information about the movie if possible.
     */
    public Response getMovie(String title) {
        try {
            HttpResponse<JsonNode> response
                    = Unirest.get("http://www.omdbapi.com/?apikey={apiKey}&t={title}&plot=full")
                            .routeParam("apiKey", omdbApiKey)
                            .routeParam("title", title)
                            .asJson();

            if (!response.isSuccess()) {
                LOGGER.info("No movie information found for the title: {}", title);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            JSONObject result = response.getBody().getObject();

            String movieTitle = result.getString("Title");
            String movieReleased = result.getString("Released");
            String moviePlot = result.getString("Plot");
            String moviePoster = result.getString("Poster");
            List<Rating> movieRatings = new ArrayList();

            JSONArray ratings = result.getJSONArray("Ratings");
            for (int i = 0; i < ratings.length(); i++) {
                JSONObject rating = ratings.getJSONObject(i);
                movieRatings.add(new Rating(rating.getString("Source"), rating.getString("Value")));
            }

            List<String> movieGenres = new ArrayList();
            String rawGenres = result.getString("Genre");
            movieGenres.addAll(Arrays.asList(rawGenres.split(",")));
            String director = result.getString("Director");

            List<String> movieCast = new ArrayList();
            String rawCast = result.getString("Actors");
            movieCast.addAll(Arrays.asList(rawCast.split(",")));

            Movie movie = new Movie(movieTitle, movieReleased, moviePlot, moviePoster, movieRatings, movieGenres, director, movieCast);
            return Response.status(Response.Status.OK).entity(movie).build();
        } catch (Exception ex) {
            LOGGER.error("Failed to retrieve movie information: {}", ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }
}
