package nu.te4.thefinalscore.backend.resources;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nu.te4.thefinalscore.backend.beans.SignupBean;
import nu.te4.thefinalscore.backend.entities.User;

/**
 * Contains web resources for using signing up on this site.
 * @author Adrian Klasson
 */
@Path("signup")
@Produces(MediaType.APPLICATION_JSON)
public class SignupResource {
    
    /**
     * Handles all the logic.
     */
    @EJB
    SignupBean signupBean;
    
    /**
     * Signs up a user.
     * @param basicAuth The basic authorization string containing username and password.
     * @return A response with the status indicating success or not.
     */
    @Path("regular")
    @POST
    public Response signupRegularUser(@HeaderParam("Authorization") String basicAuth){
        return signupBean.signUpRegularUser(basicAuth);
    }
    
    /**
     * Signs up a OAuth user.
     * @param basicAuth The basic authorization string containing username and token.
     * @return A response with the status indicating success or not.
     */
    @Path("oauth")
    @POST
    public Response signupOauthUser(@HeaderParam("Authorization") String basicAuth){
        return signupBean.signUpOAuthUser(basicAuth);
    }
}
