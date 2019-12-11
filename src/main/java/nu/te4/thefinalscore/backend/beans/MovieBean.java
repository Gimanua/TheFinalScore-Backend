package nu.te4.thefinalscore.backend.beans;

import java.util.ArrayList;
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

@Stateless
public class MovieBean {

    private final String movieDbApiKey = "be96f5a969c038665fdfab7c659f24bf";
    private final String omdbApiKey = "8574ffd";

    public Response getMovies(String query){

        HttpResponse<JsonNode> response =
                Unirest.get("https://api.themoviedb.org/3/search/movie?include_adult=false&page=1&query={query}&language=en-US&api_key={apiKey}")
                .routeParam("query", query)
                .routeParam("apiKey", movieDbApiKey)
                .asJson();

        if(!response.isSuccess()){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        JSONObject obj = response.getBody().getObject();
        System.out.println(obj);
        return Response.status(Response.Status.OK).entity(obj.toString()).build();
    }
    
    public Response getMovie(String title){
        HttpResponse<JsonNode> response =
                Unirest.get("http://www.omdbapi.com/?apikey={apiKey}&t={title}&plot=full")
                .routeParam("apiKey", omdbApiKey)
                .routeParam("title", title)
                .asJson();
        
        if(!response.isSuccess()){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        JSONObject result = response.getBody().getObject();
        System.out.println(result);
        
        String movieTitle = result.getString("Title");
        String movieReleased = result.getString("Released");
        String moviePlot = result.getString("Plot");
        List<Rating> movieRatings = new ArrayList();
        
        JSONArray ratings = result.getJSONArray("Ratings");
        for(int i = 0; i < ratings.length(); i++){
            JSONObject rating = ratings.getJSONObject(i);
            movieRatings.add(new Rating(rating.getString("Source"), rating.getString("Value")));
        }
        
        Movie movie = new Movie(movieTitle, movieReleased, moviePlot, movieRatings);
        return Response.status(Response.Status.OK).entity(movie).build();
    }
}
