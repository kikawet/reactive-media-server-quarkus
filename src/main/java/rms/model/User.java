package rms.model;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
public class User extends PanacheEntityBase {
    @Id
    String login;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    List<UserView> history = Collections.emptyList();
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    List<Suggestion> suggestions = Collections.emptyList();

    User(SecurityIdentity identity) {
        this.login = identity.getPrincipal().getName();
    }

}
