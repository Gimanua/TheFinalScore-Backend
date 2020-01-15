package nu.te4.thefinalscore.backend.entities;

import java.util.List;

/**
 * Represents a real world movie.
 * @author Adrian Klasson
 */
public class Movie {
    /**
     * The title of the movie.
     */
    private String title;
    
    /**
     * What year the movie was released.
     */
    private String year;
    
    /**
     * The runtime of the movie.
     */
    private String runtime;
    
    /**
     * When the movie was released.
     */
    private String released;
    
    /**
     * The plot of the movie.
     */
    private String plot;
    
    /**
     * The URL to the poster of the movie.
     */
    private String poster;
    
    /**
     * The ratings of the movie.
     */
    private List<Rating> ratings;
    
    /**
     * The genres of the movie.
     */
    private List<String> genres;
    
    /**
     * The director of the movie.
     */
    private String director;
    
    /**
     * The cast of the movie.
     */
    private List<String> cast;
    
    private List<String> languages;
    
    private String type;

    public Movie(String title, String year, String runtime, String released, String plot, String poster, List<Rating> ratings, List<String> genres, String director, List<String> cast, List<String> languages, String type) {
        this.title = title;
        this.year = year;
        this.runtime = runtime;
        this.released = released;
        this.plot = plot;
        this.poster = poster;
        this.ratings = ratings;
        this.genres = genres;
        this.director = director;
        this.cast = cast;
        this.languages = languages;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
