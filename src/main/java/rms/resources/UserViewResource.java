package rms.resources;

import java.time.LocalDateTime;

import org.apache.http.HttpStatus;
import org.jboss.resteasy.reactive.ResponseStatus;

import io.smallrye.mutiny.CompositeException;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import rms.dto.CreateUserViewDto;
import rms.model.User;
import rms.model.UserView;
import rms.model.UserViewSource;
import rms.model.Video;

@Path("view")
public class UserViewResource {

        @POST
        @ResponseStatus(HttpStatus.SC_CREATED)
        public Uni<UserView> create(@Valid CreateUserViewDto userViewDto) {
                Uni<User> userUni = User.findById(userViewDto.userLogin);
                Uni<Video> videoUni = Video.findById(userViewDto.videoTitle);

                userUni = userUni.onItemOrFailure().transform((user, exception) -> {
                        if (user == null || exception != null)
                                throw new NotFoundException(
                                                Response.status(HttpStatus.SC_NOT_FOUND).entity(
                                                                "No user found with login: "
                                                                                + userViewDto.userLogin)
                                                                .build());
                        return user;
                });
                videoUni = videoUni.onItemOrFailure().transform((video, exception) -> {
                        if (video == null || exception != null)
                                throw new NotFoundException(
                                                Response.status(HttpStatus.SC_NOT_FOUND).entity(
                                                                "No video found with title: "
                                                                                + userViewDto.videoTitle)
                                                                .build());
                        return video;
                });

                return Uni.combine()
                                .all()
                                .unis(userUni, videoUni)
                                .collectFailures()
                                .asTuple()
                                .onFailure(CompositeException.class)
                                .transform(fail -> fail.getSuppressed()[0].getSuppressed()[0])
                                .onItem()
                                .ifNotNull()
                                .transform(tuple -> {
                                        if (tuple.getItem2().getIsPrivate()) {
                                                throw new ForbiddenException(Response
                                                                .status(HttpStatus.SC_FORBIDDEN).entity(
                                                                                "The video with title: "
                                                                                                + userViewDto.videoTitle
                                                                                                + " is private")
                                                                .build());
                                        }
                                        return tuple;
                                })
                                .onItem()
                                .ifNotNull()
                                .transform((tuple) -> UserView.builder()
                                                .user(tuple.getItem1())
                                                .video(tuple.getItem2())
                                                .timestamp(userViewDto.timestamp
                                                                .orElse(LocalDateTime.now()))
                                                .completionPercentage(
                                                                userViewDto.completionPercentage
                                                                                .floatValue())
                                                .source(userViewDto.source.orElse(UserViewSource.System))
                                                .build())
                                .onItem()
                                .ifNotNull()
                                .transformToUni(uv -> {
                                        return uv.persist();
                                });
        }
}
