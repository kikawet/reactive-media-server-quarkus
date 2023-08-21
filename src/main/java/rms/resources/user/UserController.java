package rms.resources.user;

import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("user")
public class UserController {
    @Inject
    private Logger logger;

    @GET
    @Path(":userId/history")
    public void getHistoryByUser(@PathParam("userId") String user) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @GET
    @Path(":userId/suggestion")
    public void getSuggestionByUser(@PathParam("userId") String user) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }
}
