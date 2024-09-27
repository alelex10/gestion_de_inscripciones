package com.PoloIT.GestionDeInscripciones.Controller.Admin;

import com.PoloIT.GestionDeInscripciones.DTO.EventDTO;
import com.PoloIT.GestionDeInscripciones.Services.EventServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Tag(name = "Eventos", description = "API de Controles para Eventos")
@RestController
@RequestMapping("/api/v1/admin/event/")
@RequiredArgsConstructor
public class EventController {

    private final EventServiceImpl eventServiceImpl;
    private final HttpServletRequest request;
    @Operation(
            summary = "Registrar un nuevo evento",
            description = "Este endpoint permite registrar un nuevo evento con datos JSON y una imagen asociada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento registrado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"Event\": \"Save Event\" }"))),
    })
    @PostMapping("save")
    public ResponseEntity<Map<String, String>> registerEvent(
            @RequestBody(description = "Datos del evento en formato JSON", required = true) String data,
            @RequestBody(description = "Archivo del evento (imagen)", required = true) MultipartFile file)
            {
//data es el Json que contioene los datos de la entidad que se maneja en el controlador
        eventServiceImpl.save(data, file, request);


        return new ResponseEntity<>(Map.of("Event", "Save Event"), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Actualizar los datos de un evento",
            description = "Este endpoint permite actualizar los datos de un evento utilizando un objeto EventDTO."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Evento actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"Event\": \"updated Event\" }")))
    })
    @PatchMapping("update")
    @Transactional
    public ResponseEntity<Map<String, String>> patchEvent(
            @RequestBody(description = "Datos del evento a actualizar", required = true)
            @Valid EventDTO dataUpdateEvent) {
        eventServiceImpl.update(dataUpdateEvent);
        return new ResponseEntity<>(Map.of("Event", "updated Event"), HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Actualizar la imagen de un evento",
            description = "Este endpoint permite actualizar la imagen asociada a un evento mediante su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Imagen del evento actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"Event\": \"updated Event\" }"))),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado",
                    content = @Content),
    })
    @PatchMapping("update/{id}")
    @Transactional
    public ResponseEntity<Map<String, String>> patchEventIMG(
            @RequestBody(description = "Archivo de imagen actualizado", required = true) MultipartFile file,
            @Parameter(description = "ID del evento", required = true) Long id) {
        eventServiceImpl.updateImg(id, file);
        return new ResponseEntity<>(Map.of("Event", "updated Event"), HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Listar todos los eventos",
            description = "Este endpoint retorna una lista de todos los eventos registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos listados exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "No se encontraron eventos", content = @Content)
    })
    @GetMapping("all")
    public ResponseEntity<Map<String, List<EventDTO>>> listEvents() {
        Map<String, List<EventDTO>> body = eventServiceImpl.all();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @Operation(
            summary = "Listar todos los eventos inactivos",
            description = "Este endpoint retorna una lista de todos los eventos que no están activos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos inactivos listados exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "No se encontraron eventos inactivos", content = @Content)
    })
    @GetMapping("all/notActive")
    public ResponseEntity<Map<String, List<EventDTO>>> listEventsNotActive() {
        Map<String, List<EventDTO>> body = eventServiceImpl.AllInactiveEvent();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @Operation(
            summary = "Listar todos los eventos activos",
            description = "Este endpoint retorna una lista de todos los eventos activos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos activos listados exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "No se encontraron eventos activos", content = @Content)
    })
    @GetMapping("all/active")
    public ResponseEntity<Map<String, List<EventDTO>>> listEventsActive() {
        Map<String, List<EventDTO>> body = eventServiceImpl.AllActiveEvent();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
    @Operation(
            summary = "Obtener detalles de un evento",
            description = "Este endpoint obtiene los detalles de un evento por su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento obtenido exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"Event\": { \"id\": 1, \"name\": \"Evento 1\" } }"))),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado", content = @Content)
    })

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, EventDTO>> getEvent(
            @Parameter(description = "ID del evento a buscar", required = true)
            @PathVariable Long id) {
        EventDTO body = eventServiceImpl.getEvent(id);
        return new ResponseEntity<>(Map.of("Event", body), HttpStatus.OK);
    }

    @Operation(
            summary = "Eliminar un evento",
            description = "Este endpoint elimina un evento por su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Evento eliminado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"Event\": \"Event eliminated\" }"))),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteEvent(
            @Parameter(description = "ID del evento a eliminar", required = true)
            @PathVariable Long id) {
        eventServiceImpl.delete(id);
        return new ResponseEntity<>(Map.of("Event", "Event eliminated"), HttpStatus.ACCEPTED);
    }


}

