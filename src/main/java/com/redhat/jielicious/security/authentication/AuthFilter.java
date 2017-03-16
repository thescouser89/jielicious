package com.redhat.jielicious.security.authentication;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import com.redhat.jielicious.security.authentication.none.NoAuthFilter;
import com.redhat.jielicious.security.authentication.proxy.ProxyAuthFilter;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {
    private final ContainerRequestFilter activated;

    public AuthFilter() {
        // decide which one gets activated based on some configuration
        if (true) {
            activated = new NoAuthFilter();
        } else {
            activated = new ProxyAuthFilter();
        }

    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException, NotAuthorizedException {
        activated.filter(requestContext);
    }
}
