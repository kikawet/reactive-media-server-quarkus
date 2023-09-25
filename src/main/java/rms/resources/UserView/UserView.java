package rms.resources.UserView;

import java.time.LocalDateTime;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;
import rms.resources.User.User;
import rms.resources.Video.Video;

@Entity
@NoArgsConstructor
public class UserView extends PanacheEntityBase {
    @Id
    LocalDateTime timestamp;
    Float completionPercentage;
    @Enumerated(EnumType.STRING)
    Source source;
    @Id
    @ManyToOne
    User user;
    @Id
    @ManyToOne
    Video video;
}