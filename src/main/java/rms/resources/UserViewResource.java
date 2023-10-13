package rms.resources;

import java.time.LocalDateTime;

import org.jboss.resteasy.reactive.ResponseStatus;

import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import rms.dto.CreateUserViewDto;
import rms.model.User;
import rms.model.UserView;
import rms.model.UserViewSource;
import rms.model.Video;

@Path("view")
public class UserViewResource {

        @POST
        @ResponseStatus(201)
        public Uni<UserView> create(@Valid CreateUserViewDto userViewDto) {
                Uni<User> userUni = User.findById(userViewDto.userLogin()).map(user -> {
                        if (user == null)
                                throw new NotFoundException(
                                                Response.status(Status.NOT_FOUND)
                                                                .type(MediaType.TEXT_PLAIN)
                                                                .entity(
                                                                                "No user found with login: "
                                                                                                + userViewDto.userLogin())
                                                                .build());
                        return (User) user;
                });

                Uni<Video> videoUni = Video.findById(userViewDto.videoTitle()).map(video -> {
                        if (video == null)
                                throw new NotFoundException(
                                                Response.status(Status.NOT_FOUND)
                                                                .type(MediaType.TEXT_PLAIN)
                                                                .entity(
                                                                                "No video found with title: '"
                                                                                                + userViewDto.videoTitle()
                                                                                                + "'")
                                                                .build());
                        return (Video) video;
                });

                return userUni.flatMap(user -> videoUni.flatMap(video -> {
                        if (video.isPrivate())
                                throw new ForbiddenException(Response
                                                .status(Status.FORBIDDEN)
                                                .type(MediaType.TEXT_PLAIN)
                                                .entity(
                                                                "The video with title: "
                                                                                + userViewDto.videoTitle()
                                                                                + " is private")
                                                .build());

                        return UserView.builder()
                                        .user(user)
                                        .video(video)
                                        .timestamp(userViewDto.timestamp()
                                                        .orElse(LocalDateTime.now()))
                                        .completionPercentage(
                                                        userViewDto.completionPercentage()
                                                                        .floatValue())
                                        .source(userViewDto.source().orElse(UserViewSource.System))
                                        .build()
                                        .persistAndFlush();
                }));
        }
}
