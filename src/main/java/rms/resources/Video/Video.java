package rms.resources.Video;

import java.net.URL;
import java.time.Duration;
import java.util.Set;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;
import rms.resources.Suggestion.Suggestion;
import rms.resources.UserView.UserView;

@Entity
@NoArgsConstructor
public class Video extends PanacheEntityBase {
    @Id
    String title;
    URL url;
    Duration duration;
    Boolean isPrivate;
    @OneToMany(mappedBy = "video")
    Set<UserView> views;
    @OneToMany(mappedBy = "video")
    Set<Suggestion> suggestions;
}