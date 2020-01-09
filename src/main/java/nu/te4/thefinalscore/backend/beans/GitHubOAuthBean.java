/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

@Stateless
public class GitHubOAuthBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitHubOAuthBean.class);
    private final String CLIENT_ID = "686a9cd2fe0be4052344";
    private final String CLIENTSECRET = "79e36f0399cb571410974664fdc96f431a31e305";

    public Response getToken(String code) {
        try {
            String url = String.format("https://github.com/login/oauth/access_token?client_id=%s&client_secret=%s&code=%s", CLIENT_ID, CLIENTSECRET, code);

            Client client = ClientBuilder.newClient();
            String result = client.target(url).request().post(null, String.class);
            //access_token=<TOKEN>&scope=%token_type=bearer
            //det mellan access_token och &.. som vi vill ha ut
            String token = result.substring(13, result.indexOf("&"));
            return Response.ok(token).build();
        } catch (Exception ex) {
            LOGGER.debug("GitHubOAuthBean.getToken: {}", ex.getMessage());
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    public Response githubAuth(String token) {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("https://api.github.com/user?access_token=" + token);
            JsonObject auth = target.request(MediaType.APPLICATION_JSON).get(JsonObject.class);
            return Response.ok(auth).build();
        } catch (Exception ex) {
            LOGGER.debug("GitHubOAuthBean.githubAuth: {}", ex.getMessage());
            for(StackTraceElement element : ex.getStackTrace()){
                LOGGER.debug(element.toString());
            }
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

}
