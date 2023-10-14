package rms.resources;

import java.util.List;

import org.jboss.resteasy.reactive.RestResponse;

import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response.Status;
import rms.dto.CreateUserDto;
import rms.model.Suggestion;
import rms.model.User;
import rms.model.UserView;

@Path("user")
@Authenticated
public class UserResource {
        @Inject
        AuthorizationResource authorization;

        @POST
        public Uni<RestResponse<Void>> createUser(@Valid CreateUserDto userDto) {
                return authorization.getAuthenticatedUser(userDto.login())
                                .map((u) -> {
                                        if (u == null) {
                                                User user = new User();
                                                user.setLogin(userDto.login());
                                                user.persistAndFlush();
                                                return RestResponse.status(Status.CREATED);
                                        } else {
                                                return RestResponse.status(Status.CONFLICT);
                                        }
                                });
        }

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
