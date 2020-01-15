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
import javax.ws.rs.core.Response;
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
    
    public Response signInRegularUser(String basicAuth){
        
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
                return Response.status(Response.Status.OK).entity(user).build();
            }
                
            else
                return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch(Exception ex){
            LOGGER.error("Failed to sign in regular user: {}", ex.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
