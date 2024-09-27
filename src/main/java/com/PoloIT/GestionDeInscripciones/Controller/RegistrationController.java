package com.PoloIT.GestionDeInscripciones.Controller;

import com.PoloIT.GestionDeInscripciones.DTO.EventDTO;
import com.PoloIT.GestionDeInscripciones.Services.EventServiceImpl;
import com.PoloIT.GestionDeInscripciones.Services.RegistrationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(name = "Registros a Eventos", description = "API de control de Registro a Eventos")
@RestController
@RequestMapping("/api/v1/event/")
@AllArgsConstructor
public class RegistrationController {

    private final EventServiceImpl eventService;
    private final RegistrationServiceImpl registrationService;

    @Operation(
            summary = "Registrar usuario en un evento",
            description = "Este endpoint permite registrar al usuario identificado por su ID en un evento."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro en el evento exitoso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"Event\": \"Successfully registered for the event\" }")))
    })
    @GetMapping("register/{id}")
    public ResponseEntity<Map<String, String>> register(
            @Parameter(description = "ID del Evento", example = "1")
            @PathVariable Long id) {
        registrationService.register(id);
        return new ResponseEntity<>(Map.of("Event", "Successfully registered for the event"), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Obtener eventos del usuario",
            description = "Este endpoint permite obtener todos los eventos en los que el usuario identificado por su ID está registrado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Eventos obtenidos exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class),
                            examples = @ExampleObject(value = "{ \"Mis eventos\": [...] }")))
    })
    @GetMapping("myEvent/{id}")
    public ResponseEntity<Map<String, List<EventDTO>>> myEvents(
            @Parameter(description = "ID del Estudiante", example = "1")
            @PathVariable Long id) {
        List<EventDTO> body = eventService.myEvents(id);
        return new ResponseEntity<>(Map.of("Mis eventos", body), HttpStatus.CREATED);
    }


}
