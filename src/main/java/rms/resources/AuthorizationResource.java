package rms.resources;

import org.apache.http.HttpStatus;

import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rms.model.User;

@ApplicationScoped
public class AuthorizationResource {
    @Inject
    SecurityIdentity identity;

    public Uni<User> getAuthenticatedUser(String userLogin) {
        if (!identity.hasRole("admin") && !identity.getPrincipal().getName().equals(userLogin))
            throw new ForbiddenException(Response
                    .status(HttpStatus.SC_FORBIDDEN)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(
                            "The path userLogin: '" + userLogin
                                    + "' does not match the authentication userLogin: '"
                                    + identity.getPrincipal().getName() + "'")
                    .build());

        Uni<User> uniUser = User.findById(userLogin);

        return uniUser
                .onItem().ifNull().failWith(new NotFoundException("No user found with login: " + userLogin));
    }
}
