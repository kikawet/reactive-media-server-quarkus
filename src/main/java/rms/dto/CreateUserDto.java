package rms.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(@NotBlank String login) {

}
