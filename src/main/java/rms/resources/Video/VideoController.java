package rms.resources.Video;

import java.net.URI;
import java.util.List;

import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("video")
public class VideoController {

    @GET
    public Uni<List<Video>> getAll() {
        return Video.find("title", Sort.by("title")).firstPage().list();
    }

    @GET
    @Path("{title}")
    public Uni<Video> getVideoByTitle(@PathParam("title") String title) {
        Uni<Video> video = Video.findById(title);

        return video
                .onItem().ifNull().failWith(new NotFoundException())
                .onItem().ifNotNull().transform((Video v) -> {
                    if (v.isPrivate)
                        throw new NotFoundException();
                    return v;
                });
    }

    @POST
    @Transactional
    public Response createVideo(Video video) {
        video.persist();
        return Response.created(URI.create("/video/" + video.getTitle())).build();
    }

    // @PUT // Used to update if a video is private or not or whom is allowed to see
    // it
    // public void updateVideo(Video video) {
    // throw new UnsupportedOperationException("Method not implemented yet");
    // }
}