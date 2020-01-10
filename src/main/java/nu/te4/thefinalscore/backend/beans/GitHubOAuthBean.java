package nu.te4.thefinalscore.backend.beans;

import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains logic for using Githubs OAuth.
 * @author Adrian Klasson
 */
@Stateless
public class GitHubOAuthBean {

    /**
     * Logs information.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GitHubOAuthBean.class);
    
    /**
     * The CLIENT ID used by Githubs OAuth for this application.
     */
    private final String CLIENT_ID = "686a9cd2fe0be4052344";
    
    /**
     * The CLIENT SECRET used by Githubs OAuth for this application.
     */
    private final String CLIENTSECRET = "79e36f0399cb571410974664fdc96f431a31e305";

    /**
     * Gets a token from Github with help of the code you get from Github after logging in on their site.
     * @param code The code returned from Github after logging in on their site.
     * @return A response with the token in if everything succeeds, otherwise unauthorized.
     */
    public Response getToken(String code) {
        try {
            LOGGER.info("Formatting URL");
            String url = String.format("https://github.com/login/oauth/access_token?client_id=%s&client_secret=%s&code=%s", CLIENT_ID, CLIENTSECRET, code);

            Client client = ClientBuilder.newClient();
            LOGGER.info("Retrieving token from Github.");
            String result = client.target(url).request().post(null, String.class);
            //access_token=<TOKEN>&scope=%token_type=bearer
            //det mellan access_token och &.. som vi vill ha ut
            LOGGER.info("Parsing token.");
            String token = result.substring(13, result.indexOf("&"));
            return Response.ok(token).build();
        } catch (Exception ex) {
            LOGGER.error("Failed to retrieve a token from Github: {}", ex.getMessage());
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    /**
     * Gets Github information of a Github user with help of their token.
     * @param token The token used by a Github user.
     * @return A response containing the information if successful, otherwise unauthorized.
     */
    public Response githubAuth(String token) {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("https://api.github.com/user?access_token=" + token);
            LOGGER.info("Requesting user information from Github.");
            JsonObject auth = target.request(MediaType.APPLICATION_JSON).get(JsonObject.class);
            LOGGER.info("Succeeded retrieving user information.");
            return Response.ok(auth).build();
        } catch (Exception ex) {
            LOGGER.error("Failed to retrieve user information from Github: {}", ex.getMessage());
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    
    public Response verify(String token){
        try {
            Client client = ClientBuilder.newClient();
            String tokenvalue = token;
            WebTarget target = client.target("http://api.github.com/user?access_token="+tokenvalue);
            JsonObject auth = target.request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        } catch (Exception e) {
            System.out.println("Failed to verify user: " + e.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok().build();
        
    }

}
