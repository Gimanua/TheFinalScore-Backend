/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.thefinalscore.backend.resources;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nu.te4.thefinalscore.backend.beans.SigninBean;

/**
 *
 * @author Adrian Klasson
 */
@Path("signin")
@Produces(MediaType.APPLICATION_JSON)
public class SigninResource {
    
    @EJB
    SigninBean signinBean;
    
    @Path("regular")
    @GET
    public Response signInRegularUser(@HeaderParam("Authorization") String basicAuth){
        return signinBean.signInRegularUser(basicAuth);
    }
    
    @Path("oauth")
    @GET
    public Response signInOAuthUser(@HeaderParam("Authorization") String basicAuth){
        return signinBean.signInOAuthUser(basicAuth);
    }
}
