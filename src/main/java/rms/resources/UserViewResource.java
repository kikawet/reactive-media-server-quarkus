package rms.resources;

import java.time.LocalDateTime;

import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import rms.dto.CreateUserViewDto;
import rms.model.User;
import rms.model.UserView;
import rms.model.UserViewSource;
import rms.model.Video;

@Path("view")
public class UserViewResource {

    @POST
    Uni<UserView> create(@Valid CreateUserViewDto userViewDto) {
        Uni<User> userUni = User.findById(userViewDto.userLogin());
        Uni<Video> videoUni = Video.findById(userViewDto.videoTitle());

        userUni = userUni.onItem().ifNull()
                .failWith(new NotFoundException("No user found with login: " + userViewDto.userLogin()));
        videoUni = videoUni.onItem().ifNull()
                .failWith(new NotFoundException("No video found with title: " + userViewDto.videoTitle()));

        return Uni.combine()
                .all()
                .unis(userUni, videoUni)
                .combinedWith((user, video) -> {
                    return UserView.builder()
                            .user(user)
                            .video(video)
                            .timestamp(userViewDto.timestamp().orElse(LocalDateTime.now()))
                            .completionPercentage(userViewDto.completionPercentage().floatValue())
                            .source(userViewDto.source().orElse(UserViewSource.System))
                            .build();
                })
                .flatMap(userView -> userView.persist());
    }
}
