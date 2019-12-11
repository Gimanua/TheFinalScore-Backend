package nu.te4.thefinalscore.backend.entities;

import java.util.List;

public class Movie {
    private String title;
    private String released;
    private String plot;
    private List<Rating> ratings;

    public Movie(String title, String released, String plot, List<Rating> ratings) {
        this.title = title;
        this.released = released;
        this.plot = plot;
        this.ratings = ratings;
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
}
