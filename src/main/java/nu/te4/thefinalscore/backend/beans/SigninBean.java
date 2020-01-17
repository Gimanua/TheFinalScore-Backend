/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.thefinalscore.backend.beans;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import nu.te4.thefinalscore.backend.ConnectionFactory;
import nu.te4.thefinalscore.backend.entities.Credentials;
import nu.te4.thefinalscore.backend.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Adrian Klasson
 */
@Stateless
public class SigninBean {
    
    /**
     * Logs information.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SigninBean.class);
    
    public Status signInRegularUser(String basicAuth){
        
        try (Connection connection = ConnectionFactory.getConnection()){
            
            Credentials credentials = new Credentials(basicAuth);
            
            String sql = "SELECT id, hashed_password FROM users WHERE username=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, credentials.getUsername());
            ResultSet data = stmt.executeQuery();
            data.next();
            String hashedPassword = data.getString("hashed_password");
            int id = data.getInt("id");
            BCrypt.Result result = BCrypt.verifyer().verify(credentials.getVerifier().toCharArray(), hashedPassword);
            if(result.verified){
                User user = new User(credentials.getUsername());
                user.setId(id);
                return Response.Status.OK;
            }
            else
                return Response.Status.UNAUTHORIZED;
        } catch(Exception ex){
            LOGGER.error("Failed to sign in regular user: {}", ex.getMessage());
            return Response.Status.UNAUTHORIZED;
        }
    }
    
    public Status signInOAuthUser(String basicAuth){
        try (Connection connection = ConnectionFactory.getConnection()){
            Credentials credentials = new Credentials(basicAuth);
            
            Integer credentialsOauthID = getOAuthID(credentials.getVerifier());
            if(credentialsOauthID == null)
                return Response.Status.UNAUTHORIZED;
            
            String sql = "SELECT oauth_id FROM users WHERE username=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, credentials.getUsername());
            ResultSet data = stmt.executeQuery();
            data.next();
            int oauthID = data.getInt("oauth_id");
            
            return credentialsOauthID.equals(oauthID) ? Response.Status.OK : Response.Status.UNAUTHORIZED;
        } catch (Exception ex) {
            LOGGER.error("Failed to sign in oauth user: {}", ex.getMessage());
            return Response.Status.UNAUTHORIZED;
        }
    }
    
    //Temporary
    /**
     * Gets Github information of a Github user with help of their token.
     * @param token The token used by a Github user.
     * @return A json object containing the information if successful, otherwise null.
     */
    public Integer getOAuthID(String token) {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("https://api.github.com/user?access_token=" + token);
            LOGGER.info("Requesting user information from Github.");
            JsonObject auth = target.request(MediaType.APPLICATION_JSON).get(JsonObject.class);
            LOGGER.info("Succeeded retrieving user information.");
            Integer oAuthID = auth.getInt("id");
            return oAuthID;
        } catch (Exception ex) {
            LOGGER.error("Failed to retrieve user information from Github: {}", ex.getMessage());
        }

        return null;
    }
}
