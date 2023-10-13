package rms.resources;

import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import rms.exceptions.UnauthorizedResponseException;
import rms.model.User;

@ApplicationScoped
public class AuthorizationResource {
    @Inject
    SecurityIdentity identity;

    public Uni<User> getAuthenticatedUser(String userLogin) {
        if (!identity.hasRole("admin") && !identity.getPrincipal().getName().equals(userLogin))
            throw new UnauthorizedResponseException(userLogin, identity.getPrincipal().getName());

        Uni<User> uniUser = User.findById(userLogin);

        return uniUser
                .onItem().ifNull().failWith(new NotFoundException("No user found with login: " + userLogin));
    }
}
