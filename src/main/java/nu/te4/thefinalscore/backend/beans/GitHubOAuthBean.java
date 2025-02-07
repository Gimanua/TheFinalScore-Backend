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
 *
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
     * Gets a token from Github with help of the code you get from Github after
     * logging in on their site.
     *
     * @param code The code returned from Github after logging in on their site.
     * @return A response with the token in if everything succeeds, otherwise
     * unauthorized.
     */
    public Response getToken(String code) {
        try {
            LOGGER.info("Formatting URL using CLIENT_ID: {}, CLIENTSECRET: {}, code: {}", CLIENT_ID, CLIENTSECRET, code);
            String url = String.format("https://github.com/login/oauth/access_token?client_id=%s&client_secret=%s&code=%s", CLIENT_ID, CLIENTSECRET, code);
            LOGGER.debug("URL set to: {}", url);
            
            LOGGER.trace("Instantiating a Client.");
            Client client = ClientBuilder.newClient();
            LOGGER.info("Retrieving token from GitHub.");
            String result = client.target(url).request().post(null, String.class);
            LOGGER.debug("Response from GitHub: {}", result);
            
            LOGGER.info("Parsing token.");
            String token = result.substring(13, result.indexOf("&"));
            LOGGER.debug("Token parsed to: {}", token);
            return Response.ok(token).build();
        } catch (Exception ex) {
            LOGGER.error("Failed to retrieve a token from Github: {}", ex.getMessage());
        }
        
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    /**
     * Gets Github information of a Github user with help of their token.
     *
     * @param token The token used by a Github user.
     * @return A response containing the information if successful, otherwise
     * unauthorized.
     */
    public Response githubAuth(String token) {
        try {
            LOGGER.trace("Instantiating a new client.");
            Client client = ClientBuilder.newClient();
            LOGGER.trace("Setting WebTarget using token: {}", token);
            WebTarget target = client.target("https://api.github.com/user?access_token=" + token);
            LOGGER.info("Requesting user information from Github.");
            JsonObject auth = target.request(MediaType.APPLICATION_JSON).get(JsonObject.class);
            LOGGER.debug("Returned JsonObject: {}", auth);
            LOGGER.info("Succeeded retrieving user information.");
            return Response.ok(auth).build();
        } catch (Exception ex) {
            LOGGER.error("Failed to retrieve user information from Github: {}", ex.getMessage());
        }
        
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    /**
     * Verifies that a token is valid.
     * @param token The token to validate.
     * @return A response representing success or not.
     */
    public Response verify(String token) {
        LOGGER.debug("Verifying token..");
        return githubAuth(token);
    }
}
