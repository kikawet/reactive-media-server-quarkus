package rms.resources.User;

import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import rms.resources.UserView.UserView;

@Path("user")
public class UserController {
    @Inject
    private Logger logger;

    @GET
    @Path("{userId}/history")
    public Uni<List<UserView>> getHistoryByUser(@PathParam("userId") String userId) {
        Optional<User> user = User.findByIdOptional(userId);
        return Uni.createFrom().optional(user)
                .onItem().ifNull().failWith(new NotFoundException())
                .onItem().ifNotNull().transform(User::getHistory);
    }

    @GET
    @Path("{userId}/suggestion")
    public void getSuggestionByUser(@PathParam("userId") String user) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }
}
