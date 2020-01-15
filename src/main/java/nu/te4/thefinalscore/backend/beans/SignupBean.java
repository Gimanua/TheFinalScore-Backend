package nu.te4.thefinalscore.backend.beans;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
 * Contaings logic for signing up users.
 * @author Adrian Klasson
 */
@Stateless
public class SignupBean {

    /**
     * Logs information.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SignupBean.class);

    /**
     * Signs up a user with help of their OAuth ID.
     * @param basicAuth The authorization string containing the username and token.
     * @return Status code and response message.
     * @author Adrian Klasson
     */
    public Response signUpOAuthUser(String basicAuth) {
        Credentials credentials = new Credentials(basicAuth);
        
        Integer oAuthID = getOAuthID(credentials.getVerifier());
        if(oAuthID == null){
            return Response.status(Status.BAD_REQUEST).build();
        }
        
        Status status;
        User entity = null;

        try ( Connection connection = ConnectionFactory.getConnection()) {
            String sql = "INSERT INTO users (id, username, hashed_password, oauth_id) VALUES(NULL, ?, NULL, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, credentials.getUsername());
            stmt.setInt(2, oAuthID);
            
            if (stmt.executeUpdate() != 1) {
                status = Status.BAD_REQUEST;
                LOGGER.warn("Could not insert user. Perhaps username was already taken.");
            } else {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                generatedKeys.next();
                int id = generatedKeys.getInt(1);

                User user = new User(credentials.getUsername(), oAuthID);
                user.setId(id);
                status = Status.CREATED;
                entity = user;
            }
        } catch (SQLException ex) {
            LOGGER.error("Error signing up OAuth user: {}", ex);
            status = Status.SERVICE_UNAVAILABLE;
        }

        return Response.status(status).entity(entity).build();
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
    
    /**
     * Signs up a user with help of their Username and Password.
     * @param basicAuth The authorization string containing the username and OAuth token.
     * @return Status code and response message.
     * @author Adrian Klasson
     */
    public Response signUpRegularUser(String basicAuth){
        
        Credentials credentials = new Credentials(basicAuth);
        Status status;
        Object entity = null;
        
        try ( Connection connection = ConnectionFactory.getConnection()) {
            String sql = "INSERT INTO users (id, username, hashed_password, oauth_id) VALUES(NULL, ?, ?, NULL)";
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, credentials.getUsername());
            String hashedPassword = BCrypt.withDefaults().hashToString(12, credentials.getVerifier().toCharArray());
            stmt.setString(2, hashedPassword);
            
            if (stmt.executeUpdate() != 1) {
                status = Status.BAD_REQUEST;
                LOGGER.info("Could not insert user. Perhaps username was already taken.");
            } else {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                generatedKeys.next();
                int id = generatedKeys.getInt(1);

                User user = new User(credentials.getUsername());
                user.setId(id);
                status = Status.CREATED;
                entity = user;
            }
            
        } catch (SQLException ex) {
            LOGGER.error("Error signing up regular user: {}", ex);
            status = Status.SERVICE_UNAVAILABLE;
        }
        
        return Response.status(status).entity(entity).build();
    }
}
