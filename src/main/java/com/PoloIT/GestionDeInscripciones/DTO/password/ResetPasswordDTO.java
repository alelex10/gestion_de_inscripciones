package com.PoloIT.GestionDeInscripciones.DTO.password;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
//[8/9] DTO de la contraseña y confirmacion de la misma
public record ResetPasswordDTO(
        @NotEmpty(message = "Password required")
        @Length(min = 7, max = 20, message = "Password min 7 and max 20")
        String password,

        @NotEmpty(message = "Confirm password required")
        @Length(min = 7, max = 20, message = "Password min 7 and max 20")
        String confirmPassword
) {
}
