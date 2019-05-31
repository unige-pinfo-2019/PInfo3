package api.rest;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.ResteasyProviderFactory;

import domain.model.User;

@Provider
public class UserContextFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        SecurityContext securityContext = context.getSecurityContext();
        String username = securityContext.getUserPrincipal().getName();

        ResteasyProviderFactory.pushContext(User.class, new User(username));
    }  
}