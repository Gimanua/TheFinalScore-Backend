package nu.te4.thefinalscore.backend.entities;

/**
 * Represents a rating for a Movie.
 * @author Adrian Klasson
 */
public class Rating {
    /**
     * The source of the rating.
     */
    private String source;
    
    /**
     * The actual rating.
     */
    private String value;

    public Rating(String source, String value) {
        this.source = source;
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
