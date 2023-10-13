package rms.resources;

import java.time.LocalDateTime;

import org.jboss.resteasy.reactive.ResponseStatus;

import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import rms.dto.CreateUserViewDto;
import rms.exceptions.ForbiddenResponseException;
import rms.exceptions.NotFoundResponseException;
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
                                throw new NotFoundResponseException(
                                                "No user found with login: " + userViewDto.userLogin());
                        return (User) user;
                });

                Uni<Video> videoUni = Video.findById(userViewDto.videoTitle()).map(video -> {
                        if (video == null)
                                throw new NotFoundResponseException(
                                                "No video found with title: '"
                                                                + userViewDto.videoTitle()
                                                                + "'");
                        return (Video) video;
                });

                return userUni.flatMap(user -> videoUni.flatMap(video -> {
                        if (video.isPrivate())
                                throw new ForbiddenResponseException("The video with title: '"
                                                + userViewDto.videoTitle()
                                                + "' is private");

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
