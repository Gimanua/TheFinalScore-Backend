package nu.te4.thefinalscore.backend.beans;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

@Stateless
public class MovieBean {

    private String apiKey = "be96f5a969c038665fdfab7c659f24bf";

    public Response getMovies(String query){

        Response.Status status = Response.Status.OK;
        HttpResponse<JsonNode> response =
                Unirest.get("https://api.themoviedb.org/3/search/movie?include_adult=false&page=1&query={query}&language=en-US&api_key={apiKey}")
                .routeParam("query", query)
                .routeParam("apiKey", apiKey)
                .asJson();

        if(!response.isSuccess()){
            System.out.println("Something went wrong");
            status = Response.Status.BAD_REQUEST;
        }
        else
            System.out.println(response.getBody().getObject());

        return Response.status(status).entity(response.getBody().getObject()).build();
    }
}
