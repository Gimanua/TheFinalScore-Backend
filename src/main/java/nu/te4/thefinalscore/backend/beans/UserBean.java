/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.thefinalscore.backend.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ejb.Stateless;
import nu.te4.thefinalscore.backend.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Adrian Klasson
 */
@Stateless
public class UserBean {

    /**
     * Logs information.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserBean.class);
    
    public Integer getUserId(String username) {
        try ( Connection connection = ConnectionFactory.getConnection()) {
            String sql = "SELECT id FROM users WHERE username=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet data = stmt.executeQuery();
            data.first();
            return data.getInt("id");
        } catch (SQLException ex) {
            LOGGER.error("Failed to retrieve user id from username: {}", ex.getMessage());
            return null;
        }
    }
}
