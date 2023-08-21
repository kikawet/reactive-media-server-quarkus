package rms.resources.user;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_table")
@Getter
@Setter
@NoArgsConstructor
public class User extends PanacheEntity {
    public String userName;

    User(SecurityIdentity identity) {
        this.userName = identity.getPrincipal().getName();
    }
}
