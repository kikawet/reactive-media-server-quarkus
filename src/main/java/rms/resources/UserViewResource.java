package rms.resources;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import rms.model.UserView;

@Path("view")
public class UserViewResource {

    @POST
    Uni<UserView> create(UserView view) {
        return view.persist();
    }
}
