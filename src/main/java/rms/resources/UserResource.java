package rms.resources;

import java.util.List;

import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import rms.model.Suggestion;
import rms.model.UserView;

@Path("user")
@Authenticated
public class UserResource {
        @Inject
        AuthorizationResource authorization;

        @GET
        @Path("{userId}/history")
        public Uni<List<UserView>> getHistoryByUser(@PathParam("userId") String userId) {
                return authorization.getAuthenticatedUser(userId)
                                .flatMap(x -> UserView
                                                .getByUserLogin(userId)
                                                .page(0, 5)
                                                .firstPage()
                                                .list());
        }

        @GET
        @Path("{userId}/suggestion")
        public Uni<List<Suggestion>> getSuggestionsByUser(@PathParam("userId") String userId) {
                return authorization.getAuthenticatedUser(userId)
                                .flatMap(x -> Suggestion
                                                .getByUserLogin(userId)
                                                .page(0, 5)
                                                .firstPage()
                                                .list());
        }
}
