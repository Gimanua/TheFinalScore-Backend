package nu.te4.thefinalscore.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class contains methods to get a connection to the projects database.
 *
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
     * This method does some important initialization to connect to the
     * database. Call this method once before calling the
     * {@link #getConnection() getConnection} method.
     */
    private static void init() throws Exception {
        LOGGER.trace("Trying to create new instance of: {}.","com.mysql.cj.jdbc.Driver");
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
    }

    /**
     * Gets a connection to the database.
     *
     * @return A connection to the database.
     * @throws SQLException If the database is unavailable.
     */
    public static Connection getConnection() throws SQLException {
        LOGGER.trace("Checking if connection is not initialized.");
        if (!initialized) {
            LOGGER.info("Database connection not initialized. Initializing now.");
            try {
                LOGGER.info("Initializing JDBC driver.");
                init();
                LOGGER.info("Initializing JDBC driver success.");
                LOGGER.trace("Setting initialized to true.");
                initialized = true;
            } catch (Exception ex) {
                LOGGER.error("Failed to initialize JDBC driver: {}", ex.getMessage());
            }
        }
        LOGGER.trace("Retrieving connection from DriverManager using string: {}", "jdbc:mysql://localhost/the_final_score?user=user&password=m25pFs9JqkCUr9w0");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/the_final_score?user=user&password=m25pFs9JqkCUr9w0");
        LOGGER.info("Connection retrieved.");
        return connection;
    }
}
