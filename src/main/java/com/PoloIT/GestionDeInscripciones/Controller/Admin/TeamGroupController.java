package com.PoloIT.GestionDeInscripciones.Controller.Admin;

import com.PoloIT.GestionDeInscripciones.DTO.StudentDTO;
import com.PoloIT.GestionDeInscripciones.DTO.TeamDTO;
import com.PoloIT.GestionDeInscripciones.DTO.TeamGroupFilterDTO;
import com.PoloIT.GestionDeInscripciones.Services.TeamsGroupServiceImpl;
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
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;
import java.util.Map;

@Tag(name = "Equipos", description = "API de Controles para los Equipos")
@RestController
@RequestMapping("/api/v1/admin/event/team/")
@AllArgsConstructor
public class TeamGroupController {
    private final TeamsGroupServiceImpl teamsService;
    @Operation(
            summary = "Crear grupos",
            description = "Este endpoint permite crear grupos basados en un filtro dado, ingresando el id del evento"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Grupos creados exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TeamGroupFilterDTO.class),
                            examples = @ExampleObject(value = "{ \"Grupos\": \"Grupos creados\" }")))
    })
    @PostMapping("create/{id}")
    public ResponseEntity<Map<String, String>> createGroups(
            @Parameter(description = "ID del Evento")
            @PathVariable Long id,
            @RequestBody(description = "Filtros para crear los grupos para `Studen` y `Mentor`")
            TeamGroupFilterDTO teamGroupFilterDTO) {
        teamsService.save(teamGroupFilterDTO, id);
        return new ResponseEntity<>(Map.of("Grupos", "Grupos creados"), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Obtener estudiantes sin equipo",
            description = "Este endpoint retorna una lista de estudiantes que no quedaron en un equipo."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lista de estudiantes obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"Student\": [...] }")))
    })
    @GetMapping("studentNotAccepted/{id}")
    public ResponseEntity<Map<String, List<StudentDTO>>> getStudentDontTeam(
            @Parameter(description = "ID del evento", example = "1")
            @PathVariable Long id) {
        Map<String, List<StudentDTO>> studentNotAccepted = teamsService.getStudentDontTeam(id);
        return new ResponseEntity<>(studentNotAccepted, HttpStatus.CREATED);

    }

    @Operation(
            summary = "Obtener información de un equipo",
            description = "Este endpoint retorna la información de un equipo dado su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Información del equipo obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"Team\": { ... } }")))
    })
    @GetMapping("{id}")
    public ResponseEntity<Map<String, TeamDTO>> getTeam(
            @Parameter(description = "ID del evento", example = "1")
            @PathVariable Long id) {
        Map<String, TeamDTO> teamDTOM = teamsService.getTeam(id);
        return new ResponseEntity<>(teamDTOM, HttpStatus.CREATED);

    }

    @Operation(
            summary = "Actualizar equipo",
            description = "Este endpoint permite actualizar la información de un equipo."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Equipo actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"Grupos\": \"Grupo Actualizado.\" }"))),
    })
    @PatchMapping("")
    public ResponseEntity<Map<String, String>> update(
            @RequestBody(description = "Equipo a modificar")
            TeamDTO teamDTO) {
        teamsService.update(teamDTO);
        return new ResponseEntity<>(Map.of("Grupos", "Grupo Actualizado."), HttpStatus.CREATED);
    }
    @Operation(
            summary = "Eliminar equipo",
            description = "Este endpoint permite eliminar un equipo dado su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Equipo eliminado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"Grupos\": \"Grupo Eliminado\" }"))),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado", content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, String>> delete(
            @Parameter(description = "ID del Equipo")
            @PathVariable Long id) {
        teamsService.delete(id);
        return new ResponseEntity<>(Map.of("Grupos", "Grupo Eliminado"), HttpStatus.CREATED);
    }

}
