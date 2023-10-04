package rms.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import rms.model.UserViewSource;

@RegisterForReflection
public class CreateUserViewDto {
    @NotBlank
    public String userLogin;
    @NotBlank
    public String videoTitle;
    @NotNull
    public Optional<@PastOrPresent LocalDateTime> timestamp = Optional.empty();
    @PositiveOrZero
    @DecimalMax("100.00")
    @Digits(integer = 3, fraction = 2)
    public BigDecimal completionPercentage;
    @NotNull
    public Optional<UserViewSource> source = Optional.empty();

}
