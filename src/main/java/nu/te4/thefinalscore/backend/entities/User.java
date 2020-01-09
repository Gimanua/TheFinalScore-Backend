/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.thefinalscore.backend.entities;

/**
 *
 * @author Adrian Klasson
 */
public class User {
    private int id;
    private String username;
    private String password;
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
