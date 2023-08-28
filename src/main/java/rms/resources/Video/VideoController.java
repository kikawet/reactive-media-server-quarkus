package rms.resources.Video;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("video")
public class VideoController {

    @GET
    public void getAll() {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @GET
    @Path(":title")
    public void getAllByTitle(@PathParam("title") String title) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @POST
    public void createVideo(Video video) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    // @PUT
    // public void updateVideo(Video video) {
    // throw new UnsupportedOperationException("Method not implemented yet");
    // }
}