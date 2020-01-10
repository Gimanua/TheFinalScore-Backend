package nu.te4.thefinalscore.backend.resources;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nu.te4.thefinalscore.backend.beans.GitHubOAuthBean;

/**
 * Contains web resources for using Githubs OAuth on this site.
 * @author Adrian Klasson
 */
@Path("")
public class GitHubOathResponse {
    
    /**
     * Handles the logic.
     */
    @EJB
    GitHubOAuthBean gitHubOAuthBean;
    
    /**
     * Gets a token using a code.
     * @param code A code retrieved from Github when logging in.
     * @return A response containg the token.
     */
    @GET
    @Path("token")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getToken(@QueryParam("code") String code){
        return gitHubOAuthBean.getToken(code);
    }
    
    /**
     * Gets information about a user by using their token.
     * @param token A token retrieved from Github.
     * @return A response containg information about the user.
     */
    @GET
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfo(@QueryParam("token") String token){
        return gitHubOAuthBean.githubAuth(token);
    }
}
