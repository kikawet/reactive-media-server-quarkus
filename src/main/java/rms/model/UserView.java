package rms.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
}