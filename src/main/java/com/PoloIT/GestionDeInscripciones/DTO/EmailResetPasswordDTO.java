package com.PoloIT.GestionDeInscripciones.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
//[2/9] creo DTO
public record EmailResetPasswordDTO(
        @NotEmpty(message = "Email required")
        @NotNull(message = "Email required")
        @Pattern(regexp = ".*(^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$)", message = "Email not valid")
        @Schema(description = "Email del usuario", example = "usuario@ejemplo.com")
        String email
) {
}
