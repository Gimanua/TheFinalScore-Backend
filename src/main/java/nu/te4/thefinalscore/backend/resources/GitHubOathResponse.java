/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.thefinalscore.backend.resources;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nu.te4.thefinalscore.backend.beans.GitHubOAuthBean;

@Path("")
public class GitHubOathResponse {
    
    @EJB
    GitHubOAuthBean gitHubOAuthBean;
    
    
    //Skapa token mha av code
    @GET
    @Path("token")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getToken(@QueryParam("code") String code){
        return Response.ok(gitHubOAuthBean.getToken(code)).build();
    }
    
    //REST-Tjänst för att få alla uppgifter från användaern med token
    @GET
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvents(@QueryParam("token") String token){
        return Response.ok(gitHubOAuthBean.githubAuth(token)).build();
    }
    
}
