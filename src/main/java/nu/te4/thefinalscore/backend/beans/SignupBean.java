package nu.te4.thefinalscore.backend.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import nu.te4.thefinalscore.backend.ConnectionFactory;
import nu.te4.thefinalscore.backend.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles logic for signing up users.
 * @author Adrian Klasson
 */
@Stateless
public class SignupBean {

    /**
     * Handles all the logging.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SignupBean.class);

    /**
     * Signs up a user.
     * @param user The user to sign up.
     * @return Status code and response message.
     * @author Adrian Klasson
     */
    public Response signUp(User user) {
        if (user.getOAuthID() != null) {
            return signUpOAuthUser(user);
        } else {
            return signUpRegularUser(user);
        }
    }

    /**
     * Signs up a user with help of their OAuth ID.
     * @param user The user to sign up.
     * @return Status code and response message.
     * @author Adrian Klasson
     */
    private Response signUpOAuthUser(User user) {
        Status status;
        Object entity = null;

        try ( Connection connection = ConnectionFactory.getConnection()) {
            String sql = "INSERT INTO users (id, username, hashed_password, oauth_id) VALUES(NULL, ?, NULL, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getUsername());
            stmt.setInt(2, user.getOAuthID());
            
            if (stmt.executeUpdate() != 1) {
                status = Status.BAD_REQUEST;
                LOGGER.warn("Could not insert user. Perhaps username was already taken.");
            } else {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                generatedKeys.next();
                int id = generatedKeys.getInt(1);

                user.setId(id);
                status = Status.CREATED;
                entity = user;
            }
        } catch (SQLException ex) {
            LOGGER.error("Error: {}", ex);
            status = Status.SERVICE_UNAVAILABLE;
        }

        return Response.status(status).entity(entity).build();
    }
    
    /**
     * Signs up a user with help of their Username and Password.
     * @param user The user to sign up.
     * @return Status code and response message.
     * @author Adrian Klasson
     */
    private Response signUpRegularUser(User user){
        Status status;
        Object entity = null;
        
        try ( Connection connection = ConnectionFactory.getConnection()) {
            String sql = "INSERT INTO users (id, username, hashed_password, oauth_id) VALUES(NULL, ?, ?, NULL)";
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            
            if (stmt.executeUpdate() != 1) {
                status = Status.BAD_REQUEST;
                LOGGER.warn("Could not insert user. Perhaps username was already taken.");
            } else {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                int id = generatedKeys.getInt(1);

                user.setId(id);
                status = Status.CREATED;
                entity = user;
            }
            
        } catch (SQLException ex) {
            LOGGER.error("Error: {}", ex);
            status = Status.SERVICE_UNAVAILABLE;
        }
        
        return Response.status(status).entity(entity).build();
    }
}
