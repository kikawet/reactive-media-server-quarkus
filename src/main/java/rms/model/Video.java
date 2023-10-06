package rms.model;

import java.net.URL;
import java.time.Duration;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Video extends PanacheEntityBase {
    @Id
    String title;
    URL url;
    Duration duration;
    @JsonIgnore
    boolean isPrivate;
    @JsonIgnore
    @OneToMany(mappedBy = "video")
    Set<UserView> views;
    @JsonIgnore
    @OneToMany(mappedBy = "video")
    Set<Suggestion> suggestions;
}