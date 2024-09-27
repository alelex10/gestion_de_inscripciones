package com.PoloIT.GestionDeInscripciones.Controller.Public;


import com.PoloIT.GestionDeInscripciones.DTO.EmailResetPasswordDTO;
import com.PoloIT.GestionDeInscripciones.DTO.UserDto;
import com.PoloIT.GestionDeInscripciones.DTO.password.ResetPasswordDTO;
import com.PoloIT.GestionDeInscripciones.Services.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "Autenticacion", description = "API de Autenticacion de usuario")
@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl userService;
    @Operation(summary = "Iniciar secion ", description = "Inicio de sesion para autenticar que tipo de usuario esta inisiando secion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "login exitoso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "404", description = "Incorrect password",  content = @Content),
            @ApiResponse(responseCode = "404", description = "Email not Found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Error Credentials", content = @Content)})
    @PostMapping("authentication")
    public ResponseEntity<Map<String, String>> authentication(@Parameter(description = "email y contraseña") @Valid @RequestBody UserDto userDto) {
        Map<String, String> body = userService.authenticate(userDto);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }


    @Operation(summary = "Registro de nuevo usuario", description = "Permite registrar un nuevo usuario proporcionando los datos necesarios.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro exitoso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "406", description = "El email ya está en uso", content = @Content),
            @ApiResponse(responseCode = "404", description = "PASSWORD REQUIRED", content = @Content),
            @ApiResponse(responseCode = "404", description = "EMAIL REQUIRED", content = @Content)
    })
    @PostMapping("register")
    public ResponseEntity<Map<String, String>> register(@Parameter(description = "datos para registrase")@Valid @RequestBody UserDto userDto) {
        Map<String, String> body = userService.register(userDto);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @Operation(summary = "Enviar correo de restablecimiento de contraseña",
            description = "Envía un correo electrónico, al correo ingresado, con un enlace para restablecer la contraseña, junto a token que contioene el correo del usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Correo enviado exitosamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "404", description = "email encontrado", content = @Content)
    })
    @PostMapping("sendResetPassword")
    public ResponseEntity<Map<String, String>> sendResetPassword(@Valid() @RequestBody EmailResetPasswordDTO emailResetPasswordDTO) {
        userService.sendPasswordResetLink(emailResetPasswordDTO);
        return new ResponseEntity<>(Map.of("msg", "The email was sent to change the password"), HttpStatus.ACCEPTED);
    }
    @Operation(summary = "Restablecer contraseña",
            description = "Permite cambiar la contraseña de un usuario a través del token enviado por email, se pasa el token" +
                    " que contiene el correo del usuario por el Header que contiene el correo del usuario que desea restableser su contraseña")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Contraseña cambiada exitosamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "las contraseñas no coinsiden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Token no válido o expirado", content = @Content)
    })
    @Transactional
    @PostMapping("resetPassword")
    public ResponseEntity<Map<String, String>> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        userService.applyNewPassword(resetPasswordDTO);
        return new ResponseEntity<>(Map.of("msg", "The password was changed successfully"), HttpStatus.ACCEPTED);
    }
}
