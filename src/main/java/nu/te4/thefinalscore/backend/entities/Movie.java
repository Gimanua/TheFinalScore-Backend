package nu.te4.thefinalscore.backend.entities;

import java.util.List;

/**
 * Represents a real world movie.
 * @author Adrian Klasson
 */
public class Movie {
    
    private Integer id;
    
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
    private String synopsis;
    
    /**
     * The URL to the poster of the movie.
     */
    private String logo;
    
    /**
     * The ratings of the movie.
     */
    private List<Score> scores;
    
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
    
    private String finalScore;

    public Movie(){}
    
    public Movie(Integer id, String title, String year, String runtime, String released, String synopsis, String logo, List<Score> scores, List<String> genres, String director, List<String> cast, List<String> languages, String type, String finalScore) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.runtime = runtime;
        this.released = released;
        this.synopsis = synopsis;
        this.logo = logo;
        this.scores = scores;
        this.genres = genres;
        this.director = director;
        this.cast = cast;
        this.languages = languages;
        this.type = type;
        this.finalScore = finalScore;
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

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(String finalScore) {
        this.finalScore = finalScore;
    }
}
