package nu.te4.thefinalscore.backend.entities;

import java.util.Base64;

/**
 * Represents a user in the projects database.
 * @author Adrian Klasson
 */
public class User {
    /**
     * The identifier for the user.
     */
    private int id;
    
    /**
     * The username of the user.
     */
    private String username;
    
    /**
     * The password of the user.
     */
    private String password;
    
    /**
     * The OAuth identifier for the user.
     */
    private Integer OAuthID;

    public User() {
    }
    
    public User(String username, Integer OAuthID) {
        this.username = username;
        this.OAuthID = OAuthID;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public User(String username){
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getOAuthID() {
        return OAuthID;
    }
    
    public void setOAuthID(Integer OAuthID) {
        this.OAuthID = OAuthID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
