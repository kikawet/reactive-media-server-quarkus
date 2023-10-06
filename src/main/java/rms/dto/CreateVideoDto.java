package rms.dto;

import java.net.URL;
import java.time.Duration;
import java.util.Optional;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateVideoDto(
        @NotBlank String title,
        @NotNull URL url,
        @NotNull Duration durationSeconds,
        @NotNull Optional<Boolean> isPrivate) {

}
