package com.PoloIT.GestionDeInscripciones.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
        servers = {
        @Server(url = "https://mi-server.com/", description = "Production server"),
        @Server(url = "http://localhost:8080/", description = "Development server")},
        info = @Info(
                title = "API GESTION DE INSCRIPCIONES",
                description = "Esta API de Inscripciones te permite autentificarte y registrarte a un evento",
                version = "1.0.0"
        ),
        security = @SecurityRequirement(
                name = "Security Token"
        )
)

@SecurityScheme(
        name="Security Token",
        description = "Access Token For My API",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme= "Bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
}
