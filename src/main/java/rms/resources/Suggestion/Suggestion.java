package rms.resources.Suggestion;

import org.hibernate.annotations.Subselect;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import rms.resources.User.User;
import rms.resources.Video.Video;

@Entity
@Subselect("select * from test_view")
public class Suggestion extends PanacheEntity {
    Float priority; // from -1 to 1
    @ManyToOne
    User user;
    @ManyToOne
    Video video;
}
