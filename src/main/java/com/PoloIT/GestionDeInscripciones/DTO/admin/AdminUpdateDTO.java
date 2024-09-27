package com.PoloIT.GestionDeInscripciones.DTO.admin;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record AdminUpdateDTO(
        Long id,
        @NotNull(message = "name requerido")
        String name,
        String email,
//la URL de la img no debería actualizarse nunca, a no ser que se cree la img por 1ra vez
        @NotNull(message = "lastname requerido")
        String lastName,
        @NotNull(message = "linkedin requerido")
        String linkedin

) {
}
