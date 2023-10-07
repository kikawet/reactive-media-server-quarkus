package rms.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@NamedQueries({
        @NamedQuery(name = "UserView.getByUserLogin", query = "select uv from UserView uv join fetch uv.user join fetch uv.video where uv.user.login = ?1")
})
public class UserView extends PanacheEntityBase {
    @Id
    LocalDateTime timestamp;
    @Id
    @ManyToOne
    @JsonProperty("userLogin")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(property = "login", generator = ObjectIdGenerators.PropertyGenerator.class)
    User user;
    @Id
    @ManyToOne
    @JsonProperty("videoTitle")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(property = "title", generator = ObjectIdGenerators.PropertyGenerator.class)
    Video video;
    Float completionPercentage;
    @Enumerated(EnumType.STRING)
    UserViewSource source;

    public static PanacheQuery<UserView> getByUserLogin(String userLogin) {
        return find("#UserView.getByUserLogin", userLogin);
    }
}