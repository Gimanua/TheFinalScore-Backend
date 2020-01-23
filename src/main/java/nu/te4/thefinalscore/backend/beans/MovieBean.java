package nu.te4.thefinalscore.backend.beans;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import nu.te4.thefinalscore.backend.ConnectionFactory;
import nu.te4.thefinalscore.backend.entities.Credentials;
import nu.te4.thefinalscore.backend.entities.Movie;
import nu.te4.thefinalscore.backend.entities.Score;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains logic for everything related to movies.
 *
 * @author Adrian Klasson
 */
@Stateless
public class MovieBean {

    /**
     * Logs information.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieBean.class);

    /**
     * The API key to the MovieDB.
     */
    private final String movieDbApiKey = "be96f5a969c038665fdfab7c659f24bf";

    /**
     * The API key to the OMDB.
     */
    private final String omdbApiKey = "8574ffd";

    @EJB
    private UserBean userBean;

    /**
     * Gets a list of movie titles based on a search query.
     *
     * @param query What you search for.
     * @return A response containing a list of movie titles if successful,
     * oterhwise internal server error.
     */
    public Response getMovies(String query) {
        try {
            LOGGER.info("Retrieving movie titles.");
            HttpResponse<JsonNode> response = Unirest.get("https://api.themoviedb.org/3/search/movie?include_adult=false&page=1&query={query}&language=en-US&api_key={apiKey}")
                    .routeParam("query", query)
                    .routeParam("apiKey", movieDbApiKey)
                    .asJson();

            if (!response.isSuccess()) {
                LOGGER.info("Retrieved no results with this query: {}", query);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            JSONObject obj = response.getBody().getObject();
            return Response.ok().entity(obj.toString()).build();
        } catch (Exception ex) {
            LOGGER.error("Retrieving movie titles failed: {}", ex.getMessage());
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        }
    }

    /**
     * Gets information about a movie.
     *
     * @param title The title of the movie.
     * @return A response containg information about the movie if possible.
     */
    public Response getMovie(String title) {
        try {
            HttpResponse<JsonNode> response
                    = Unirest.get("http://www.omdbapi.com/?apikey={apiKey}&t={title}&plot=full")
                            .routeParam("apiKey", omdbApiKey)
                            .routeParam("title", title)
                            .asJson();

            if (!response.isSuccess()) {
                LOGGER.info("No movie information found for the title: {}", title);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            JSONObject result = response.getBody().getObject();

            String movieTitle = result.getString("Title");
            String movieYear = result.getString("Year");
            String movieReleased = result.getString("Released");
            String movieRuntime = result.getString("Runtime");

            List<String> movieGenres = new ArrayList();
            String rawGenres = result.getString("Genre");
            movieGenres.addAll(Arrays.asList(rawGenres.split(",")));

            String movieDirector = result.getString("Director");

            List<String> movieCast = new ArrayList();
            String rawCast = result.getString("Actors");
            movieCast.addAll(Arrays.asList(rawCast.split(",")));

            String moviePlot = result.getString("Plot");

            List<String> movieLanguages = new ArrayList();
            String rawLanguages = result.getString("Language");
            movieLanguages.addAll(Arrays.asList(rawLanguages.split(",")));

            String moviePoster = result.getString("Poster");
            List<Score> movieRatings = new ArrayList();

            JSONArray ratings = result.getJSONArray("Ratings");
            for (int i = 0; i < ratings.length(); i++) {
                JSONObject rating = ratings.getJSONObject(i);
                movieRatings.add(new Score(rating.getString("Source"), rating.getString("Value"), null));
            }

            String movieType = result.getString("Type");

            Movie movie = new Movie(null, movieTitle, movieYear, movieRuntime, movieReleased, moviePlot, moviePoster,
                    movieRatings, movieGenres, movieDirector, movieCast, movieLanguages, movieType, null);
            return Response.status(Response.Status.OK).entity(movie).build();
        } catch (Exception ex) {
            LOGGER.error("Failed to retrieve movie information: {}", ex.getMessage());
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        }

    }

    public Response saveMovie(Movie movie, String basicAuth) {
        try ( Connection connection = ConnectionFactory.getConnection()) {

            //Save the movie
            String sql = "INSERT INTO movies (title, synopsis, logo, director, year, runtime, released, type, final_score) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getSynopsis());
            stmt.setString(3, movie.getLogo());
            stmt.setString(4, movie.getDirector());
            stmt.setString(5, movie.getYear());
            stmt.setString(6, movie.getRuntime());
            stmt.setString(7, movie.getReleased());
            stmt.setString(8, movie.getType());
            stmt.setString(9, movie.getFinalScore());

            if (stmt.executeUpdate() != 1) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            generatedKeys.next();
            int movieId = generatedKeys.getInt(1);
            movie.setId(movieId);
            
            saveCast(connection, movie);
            saveGenres(connection, movie);
            saveLanguages(connection, movie);
            saveScores(connection, movie);
            
            //Connect the movie with a user
            Credentials credentials = new Credentials(basicAuth);
            Integer userID = userBean.getUserId(credentials.getUsername());

            sql = "INSERT INTO saved_movies (user_id, movie_id) VALUES(?,?)";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userID);
            stmt.setInt(2, movieId);
            if (stmt.executeUpdate() != 1) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            return Response.status(Response.Status.CREATED).entity(movie).build();

        } catch (SQLException ex) {
            LOGGER.error("Failed to save movie: {}", ex.getMessage());
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        }
    }

    private void saveCast(Connection connection, Movie movie) throws SQLException {
        for (String name : movie.getCast()) {
            String sql = "INSERT INTO casts (movie_id, name) VALUES(?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, movie.getId());
            stmt.setString(2, name);
            stmt.executeUpdate();
        }
    }
    
    private void saveGenres(Connection connection, Movie movie) throws SQLException{
        for(String name : movie.getGenres()){
            String sql = "INSERT INTO genres (movie_id, name) VALUES(?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, movie.getId());
            stmt.setString(2, name);
            stmt.executeUpdate();
        }
    }
    
    private void saveLanguages(Connection connection, Movie movie) throws SQLException{
        for(String name : movie.getGenres()){
            String sql = "INSERT INTO languages (movie_id, name) VALUES(?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, movie.getId());
            stmt.setString(2, name);
            stmt.executeUpdate();
        }
    }
    
    private void saveScores(Connection connection, Movie movie) throws SQLException{
        for(Score score : movie.getScores()){
            String sql = "INSERT INTO scores (movie_id, value, source, source_logo) VALUES(?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, movie.getId());
            stmt.setString(2, score.getValue());
            stmt.setString(3, score.getSource());
            stmt.setString(4, score.getSourceLogo());
            stmt.executeUpdate();
        }
    }

    public Response getSavedMovies(String basicAuth) {
        try ( Connection connection = ConnectionFactory.getConnection()) {
            LOGGER.debug("Parsing credentials from string basicAuth: {}", basicAuth);
            Credentials credentials = new Credentials(basicAuth);
            LOGGER.debug("Credentials parsed to: {}", credentials);

            LOGGER.debug("Retrieving userId with username: {}", credentials.getUsername());
            Integer userId = userBean.getUserId(credentials.getUsername());
            LOGGER.debug("Id retrieved: {}", userId);

            String sql = "SELECT movie_id FROM saved_movies WHERE user_id=?";
            LOGGER.debug("Preparing statement with sql: {}", sql);
            PreparedStatement stmt = connection.prepareStatement(sql);
            LOGGER.debug("Sets the user_id to: {}", userId);
            stmt.setInt(1, userId);
            LOGGER.debug("Executing query");
            ResultSet data = stmt.executeQuery();
            List<Integer> savedMovieIds = new ArrayList();
            LOGGER.debug("Iterating over the data.");
            while (data.next()) {
                LOGGER.debug("Retrieving the movie_id from the data.");
                int movieId = data.getInt("movie_id");
                LOGGER.debug("Inserting the movie_id: {}", movieId);
                savedMovieIds.add(movieId);
            }

            return Response.status(Response.Status.OK).entity(getSavedMovies(connection, savedMovieIds)).build();
        } catch (SQLException ex) {
            LOGGER.debug("Failed to retrieve saved movies: {}", ex.getMessage());
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        }
    }

    private List<Movie> getSavedMovies(Connection connection, List<Integer> savedMovieIds) throws SQLException {

        List<Movie> savedMovies = new ArrayList();
        for (int i : savedMovieIds) {
            String sql = "SELECT * FROM movies WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, i);
            ResultSet data = stmt.executeQuery();
            data.next();

            Movie movie = new Movie(
                    data.getInt("id"),
                    data.getString("title"),
                    data.getString("year"),
                    data.getString("runtime"),
                    data.getString("released"),
                    data.getString("synopsis"),
                    data.getString("logo"),
                    getMovieScores(connection, i),
                    getMovieData(connection, "genres", "name", i),
                    data.getString("director"),
                    getMovieData(connection, "casts", "name", i),
                    getMovieData(connection, "languages", "name", i),
                    data.getString("type"),
                    data.getString("final_score"));

            savedMovies.add(movie);
        }
        return savedMovies;
    }

    private List<String> getMovieData(Connection connection, String table, String select, int movieId) throws SQLException {
        List<String> movieData = new ArrayList();
        String sql = "SELECT ? FROM " + table + " WHERE movie_id=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, select);
        stmt.setInt(2, movieId);
        ResultSet data = stmt.executeQuery();
        while (data.next()) {
            movieData.add(data.getString(select));
        }
        return movieData;
    }

    private List<Score> getMovieScores(Connection connection, int movieId) throws SQLException {
        List<Score> scores = new ArrayList();
        String sql = "SELECT * FROM scores WHERE movie_id=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, movieId);
        ResultSet data = stmt.executeQuery();
        while (data.next()) {
            scores.add(new Score(data.getString("source"), data.getString("value"), data.getString("source_logo")));
        }
        return scores;
    }
}
