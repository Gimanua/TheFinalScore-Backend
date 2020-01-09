/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.thefinalscore.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Adrian Klasson
 */
public class ConnectionFactory {
    
    private static boolean initialized = false;
    
    /**
     * This method does some important initialization to connect to the database. Call this method once before calling the {@link #getConnection() getConnection} method.
     */
    public static void init(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch(Exception ex){
            System.out.println("ConnectionFactory.init: " + ex.getMessage());
        }
    }
    
    /**
     * Gets a connection to the database. Don't forget to call the {@link #init() init} method once before calling this method.
     * @return A connection to the database.
     * @throws SQLException If the database is unavailable.
     */
    public static Connection getConnection() throws SQLException {
        if(!initialized){
            init();
            initialized = true;
        }
        return DriverManager.getConnection("jdbc:mysql://localhost/the_final_score?user=user&password=m25pFs9JqkCUr9w0");
    }
}
