package nu.te4.thefinalscore.backend.entities;

/**
 * Represents a rating for a Movie.
 * @author Adrian Klasson
 */
public class Score {
    /**
     * The source of the rating.
     */
    private String source;
    
    /**
     * The actual rating.
     */
    private String value;
    
    private String sourceLogo;

    public Score(){}
    
    public Score(String source, String value, String sourceLogo) {
        this.source = source;
        this.value = value;
        this.sourceLogo = sourceLogo;
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

    public String getSourceLogo() {
        return sourceLogo;
    }

    public void setSourceLogo(String sourceLogo) {
        this.sourceLogo = sourceLogo;
    }
}
