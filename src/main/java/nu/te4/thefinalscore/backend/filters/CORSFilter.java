package nu.te4.thefinalscore.backend.filters;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * Contains one filter for allowing all traffic to this site.
 * @author Adrian Klasson
 */
@Provider
public class CORSFilter implements ContainerResponseFilter {

    /**
     * Filters traffic, allowing all traffic to this site.
     * @param requestContext The request context.
     * @param cres The response context.
     * @throws IOException If Output fails.
     */
   @Override
   public void filter(final ContainerRequestContext requestContext,
                      final ContainerResponseContext cres) throws IOException {
      cres.getHeaders().add("Access-Control-Allow-Origin", "*");
      cres.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
      cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
      cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE");
      cres.getHeaders().add("Access-Control-Max-Age", "1209600");
   }

}