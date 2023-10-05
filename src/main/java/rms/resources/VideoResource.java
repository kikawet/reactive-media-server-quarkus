package rms.resources;

import java.net.URI;
import java.util.List;

import org.jboss.resteasy.reactive.RestResponse;

import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import rms.model.Video;

@Path("video")
public class VideoResource {

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
                    if (v.getIsPrivate())
                        throw new NotFoundException();
                    return v;
                });
    }

    @POST
    public Uni<RestResponse<Void>> createVideo(Video video) {
        Uni<Video> uniVideo = video.persist();
        return uniVideo
                .onItem()
                .ifNotNull()
                .transform(v -> RestResponse.created(URI.create("/video/" + v.getTitle())));
    }

    // @PUT // Used to update if a video is private or not or whom is allowed to see
    // it
    // public void updateVideo(Video video) {
    // throw new UnsupportedOperationException("Method not implemented yet");
    // }
}