package rms.resources.Video;

import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rms.resources.Suggestion.Suggestion;
import rms.resources.UserView.UserView;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Video extends PanacheEntityBase {
    @Id
    String title;
    URL url;
    Duration duration;
    @JsonIgnore
    Boolean isPrivate = Boolean.FALSE;
    @OneToMany(mappedBy = "video")
    Set<UserView> views = Collections.emptySet();
    @OneToMany(mappedBy = "video")
    Set<Suggestion> suggestions = Collections.emptySet();
}