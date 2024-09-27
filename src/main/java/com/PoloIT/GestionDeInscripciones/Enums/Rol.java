package com.PoloIT.GestionDeInscripciones.Enums;

import com.PoloIT.GestionDeInscripciones.Config.ExecptionControll.ResponseException;
import org.springframework.http.HttpStatus;

public enum Rol {
    ADMIN, STUDENT, MENTOR;

    // Static method to check if the value is valid
    public static void fromString(String role) {
        try {
            Rol.valueOf(role.toUpperCase());
        } catch (Exception e) {
            throw new ResponseException("404", "Rol not found", HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
