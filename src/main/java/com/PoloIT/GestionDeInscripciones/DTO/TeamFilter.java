package com.PoloIT.GestionDeInscripciones.DTO;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record TeamFilter(
        @NotNull(message = "Rol is Required.")
        String rol,
        @NotNull(message = "Quantity is Required.")
        int quantity,
        //* las tecnologias son opcionales.
        Set<String> technologies

) {

}

