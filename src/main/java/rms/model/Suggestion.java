package rms.model;

import org.hibernate.annotations.Subselect;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
@Subselect("select * from test_view")
public class Suggestion extends PanacheEntity {
    Float priority; // from -1 to 1
    @ManyToOne
    User user;
    @ManyToOne
    Video video;
}
