package com.PoloIT.GestionDeInscripciones.DTO.password;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record UpdatePasswordDTO(
        @NotEmpty(message = "Password required")
        String oldPassword,
        @NotEmpty(message = "Confirm password required")
        @Length(min = 7, max = 20, message = "Password min 7 and max 20")
        String newPassword,
        @NotEmpty(message = "Confirm password required")
        @Length(min = 7, max = 20, message = "Password min 7 and max 20")
        String confirmPassword
) {
}
