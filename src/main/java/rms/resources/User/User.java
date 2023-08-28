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
import rms.resources.UserView.UserView;

@Entity
@Table(name = "user_table")
@Getter
@Setter
@NoArgsConstructor
public class User extends PanacheEntityBase {
    @Id
    String userName;
    @OneToMany(mappedBy = "user")
    List<UserView> history;

    User(SecurityIdentity identity) {
        this.userName = identity.getPrincipal().getName();
    }
}
