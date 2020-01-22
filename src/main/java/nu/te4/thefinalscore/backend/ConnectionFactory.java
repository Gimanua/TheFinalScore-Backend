package nu.te4.thefinalscore.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class contains methods to get a connection to the projects database.
 * @author Adrian Klasson
 */
public class ConnectionFactory {
    
    /**
     * Logs information.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionFactory.class);
    
    /**
     * Represents whether or not the JDBC driver has been initialized.
     */
    private static boolean initialized = false;
    
    /**
     * This method does some important initialization to connect to the database. Call this method once before calling the {@link #getConnection() getConnection} method.
     */
    private static void init(){
        LOGGER.info("Initializing JDBC driver.");
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            LOGGER.info("Initializing JDBC driver success.");
        } catch(Exception ex){
            LOGGER.error("ConnectionFactory.init: {}", ex.getMessage());
        }
    }
    
    /**
     * Gets a connection to the database.
     * @return A connection to the database.
     * @throws SQLException If the database is unavailable.
     */
    public static Connection getConnection() throws SQLException {
        if(!initialized){
            LOGGER.info("Database connection not initialized. Initializing now.");
            init();
            initialized = true;
            LOGGER.info("Database connection initializing complete.");
        }
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/the_final_score?user=user&password=m25pFs9JqkCUr9w0");
        LOGGER.info("Connection retrieved.");
        return connection;
    }
}
