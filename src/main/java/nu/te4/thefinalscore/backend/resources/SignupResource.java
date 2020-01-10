package nu.te4.thefinalscore.backend.resources;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
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
@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SignupResource {
    
    /**
     * Handles all the logic.
     */
    @EJB
    SignupBean signupBean;
    
    /**
     * Signs up a user.
     * @param user The user to sign up.
     * @return A response with the status indicating success or not.
     */
    @Path("signup")
    @POST
    public Response signupUser(User user){
        return signupBean.signUp(user);
    }
}
