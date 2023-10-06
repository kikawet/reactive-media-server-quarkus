package rms.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import rms.model.UserViewSource;

public record CreateUserViewDto(
                @NotBlank String userLogin,
                @NotBlank String videoTitle,
                @NotNull Optional<@PastOrPresent LocalDateTime> timestamp,
                @NotNull @PositiveOrZero @DecimalMax("100.00") @Digits(integer = 3, fraction = 2) BigDecimal completionPercentage,
                @NotNull Optional<UserViewSource> source) {

}
