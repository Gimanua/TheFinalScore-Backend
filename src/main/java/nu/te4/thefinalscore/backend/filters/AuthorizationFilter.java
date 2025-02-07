/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.te4.thefinalscore.backend.filters;

import java.io.IOException;
import javax.ejb.EJB;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import nu.te4.thefinalscore.backend.beans.MovieBean;
import nu.te4.thefinalscore.backend.beans.SigninBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filters out all unauthorized traffic.
 *
 * @author Adrian Klasson
 */
@Provider
public class AuthorizationFilter implements ContainerRequestFilter {

    /**
     * Logs information.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationFilter.class);

    @EJB
    SigninBean signinBean;

    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        try {
            if(crc.getUriInfo().getPath().endsWith("signup/regular") || crc.getUriInfo().getPath().endsWith("signup/oauth")){
                return;
            }
            
            if (!crc.getMethod().equals("GET") || crc.getUriInfo().getPath().endsWith("saved-movies")) {
                MultivaluedMap<String, String> headers = crc.getHeaders();
                if (!headers.containsKey("Authorization")) {
                    crc.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                    return;
                } else if (!headers.containsKey("AuthVerifier")) {
                    crc.abortWith(Response.status(Response.Status.BAD_REQUEST).build());
                    return;
                }

                String auth = crc.getHeaderString("Authorization");
                String authVerifier = crc.getHeaderString("AuthVerifier");

                if (authVerifier.equals("token")) {
                    if (signinBean.signInOAuthUser(auth) != Status.OK) {
                        crc.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                    }
                } else {
                    if (signinBean.signInRegularUser(auth) != Status.OK) {
                        crc.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                    }
                }

            }
        } catch (Exception ex) {
            LOGGER.error("Failed to verify user: {}.", ex.getMessage());
            crc.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

}
