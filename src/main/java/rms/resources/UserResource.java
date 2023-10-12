package rms.resources;

import java.util.List;

import org.apache.http.HttpStatus;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rms.model.Suggestion;
import rms.model.User;
import rms.model.UserView;

@Path("user")
@Authenticated
public class UserResource {
        @Inject
        SecurityIdentity identity;

        @GET
        @Path("{userId}/history")
        public Uni<List<UserView>> getHistoryByUser(@PathParam("userId") String userId) {
                if (!identity.hasRole("admin") && !identity.getPrincipal().getName().equals(userId))
                        throw new ForbiddenException(Response
                                        .status(HttpStatus.SC_FORBIDDEN)
                                        .type(MediaType.TEXT_PLAIN)
                                        .entity(
                                                        "The path userId: '" + userId
                                                                        + "' does not match the authentication userId: '"
                                                                        + identity.getPrincipal().getName() + "'")
                                        .build());

                return User.findById(userId)
                                .onItem().ifNull().failWith(new NotFoundException())
                                .flatMap(x -> UserView
                                                .getByUserLogin(userId)
                                                .page(0, 5)
                                                .firstPage()
                                                .list());
        }

        @GET
        @Path("{userId}/suggestion")
        public Uni<List<Suggestion>> getSuggestionsByUser(@PathParam("userId") String userId) {
                return User.findById(userId)
                                .onItem().ifNull().failWith(new NotFoundException())
                                .flatMap(x -> Suggestion
                                                .getByUserLogin(userId)
                                                .page(0, 5)
                                                .firstPage()
                                                .list());
        }
}
