package rms.resources.User;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rms.resources.Suggestion.Suggestion;
import rms.resources.UserView.UserView;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
public class User extends PanacheEntityBase {
    @Id
    String login;
    @OneToMany(mappedBy = "user")
    List<UserView> history;
    @OneToMany(mappedBy = "user")
    List<Suggestion> suggestions;

    User(SecurityIdentity identity) {
        this.login = identity.getPrincipal().getName();
    }
}
