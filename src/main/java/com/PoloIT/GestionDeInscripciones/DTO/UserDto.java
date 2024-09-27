package com.PoloIT.GestionDeInscripciones.DTO;

import com.PoloIT.GestionDeInscripciones.Entity.User;
import com.PoloIT.GestionDeInscripciones.Enums.Rol;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record UserDto(
        Long id,
        @NotEmpty(message = "Email required")
        @NotNull(message = "Email required")
        @Pattern(regexp = ".*(^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$)", message = "Email not valid")
        @Schema(description = "Email del usuario", example = "usuario@ejemplo.com")
        String email,
        String name,

        @NotEmpty(message = "Password required")
        @Length(min = 7, max = 20, message = "Password min 8 and max 20")
        String password,
        String rol
) {

    public UserDto(User user) {
        this(user.getId(), user.getEmail(), null, user.getPassword(), user.getRol().name());
    }

    public static User fromUser(UserDto userDto) {
        checkRol(userDto);
        return User.builder()
                .id(userDto.id)
                .email(userDto.email)
                .password(userDto.password)
                .rol(Rol.valueOf(userDto.rol.toUpperCase()))
                .build();
    }


    private static void checkRol(UserDto userDto) {
        Rol.fromString(userDto.rol);
    }
}
