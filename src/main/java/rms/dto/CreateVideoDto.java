package rms.dto;

import java.time.Duration;
import java.util.Optional;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import rms.validation.constraints.DurationPositive;

public record CreateVideoDto(
                @NotBlank String title,
                @NotBlank String slug,
                @NotNull @DurationPositive Duration duration, // Use ISO-8601 format.
                // https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/Duration.html#parse(java.lang.CharSequence)
                @NotNull Optional<Boolean> isPrivate) {

}
