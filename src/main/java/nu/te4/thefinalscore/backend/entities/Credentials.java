/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.thefinalscore.backend.entities;

import java.util.Base64;

/**
 *
 * @author Adrian Klasson
 */
public class Credentials {
    private final String username;
    private final String verifier;

    public Credentials(String basicAuth) {
        String[] args = getBasicAuthArguments(basicAuth);
        this.username = args[0];
        this.verifier = args[1];
    }
    
    private String[] getBasicAuthArguments(String basicAuth){
        basicAuth = basicAuth.substring(6).trim();
        byte[] bytes = Base64.getDecoder().decode(basicAuth);
        basicAuth = new String(bytes);
        int colon = basicAuth.indexOf(":");
        String argument1 = basicAuth.substring(0, colon);
        String argument2 = basicAuth.substring(colon+1);
        return new String[]{argument1, argument2};
    }

    public String getUsername() {
        return username;
    }

    public String getVerifier() {
        return verifier;
    }

    @Override
    public String toString() {
        return "Credentials{" + "username=" + username + ", verifier=" + verifier + '}';
    }
}
