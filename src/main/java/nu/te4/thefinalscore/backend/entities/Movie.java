package nu.te4.thefinalscore.backend.entities;

import java.util.List;

public class Movie {
    private String title;
    private String released;
    private String plot;
    private String poster;
    private List<Rating> ratings;
    private List<String> genres;
    private String director;
    private List<String> cast;

    public Movie(String title, String released, String plot, String poster, List<Rating> ratings, List<String> genres, String director, List<String> cast) {
        this.title = title;
        this.released = released;
        this.plot = plot;
        this.poster = poster;
        this.ratings = ratings;
        this.genres = genres;
        this.director = director;
        this.cast = cast;
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
}
