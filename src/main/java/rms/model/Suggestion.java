package rms.model;

import org.hibernate.annotations.Subselect;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Subselect("select * from \"Suggestion\"")
@NamedQueries({
        @NamedQuery(name = "Suggestion.getByUserLogin", query = "select s from Suggestion s join fetch s.user join fetch s.video where s.user.login = ?1")
})
public class Suggestion extends PanacheEntityBase {
    Float priority; // from -1 to 1
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

    public static PanacheQuery<Suggestion> getByUserLogin(String userLogin) {
        return find("#Suggestion.getByUserLogin", userLogin);
    }
}
