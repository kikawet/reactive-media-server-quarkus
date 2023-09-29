package rms.model;

import java.time.LocalDateTime;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserView extends PanacheEntityBase {
    @Id
    LocalDateTime timestamp;
    @Id
    @ManyToOne
    User user;
    @Id
    @ManyToOne
    Video video;
    Float completionPercentage;
    @Enumerated(EnumType.STRING)
    UserViewSource source;
}