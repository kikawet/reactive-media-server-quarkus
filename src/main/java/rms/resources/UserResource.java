package rms.resources;

import java.util.List;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import rms.model.Suggestion;
import rms.model.User;
import rms.model.UserView;

@Path("user")
public class UserResource {
        @GET
        @Path("{userId}/history")
        public Uni<List<UserView>> getHistoryByUser(@PathParam("userId") String userId) {
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
