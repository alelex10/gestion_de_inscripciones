package com.PoloIT.GestionDeInscripciones.Controller.Mentor;


import com.PoloIT.GestionDeInscripciones.DTO.MentorDTO;
import com.PoloIT.GestionDeInscripciones.Entity.Mentor;
import com.PoloIT.GestionDeInscripciones.Services.MentorServiceImpl;
import com.PoloIT.GestionDeInscripciones.Services.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/mentor/")
@RequiredArgsConstructor
public class MentorController {

    private final MentorServiceImpl mentorService;
    private final UserServiceImpl userService;
    private final HttpServletRequest request;

    @Operation(
            summary = "Actualizar datos del mentor",
            description = "Este endpoint permite actualizar los datos de un mentor."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Datos actualizados exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"update\": \"Datos actualizados\" }")))
    })
    @PatchMapping("update")
    public ResponseEntity<Map<String, String>> update(@Valid @RequestBody MentorDTO mentorDTO) {
        mentorService.update(mentorDTO);
        return new ResponseEntity<>(Map.of("update", "Datos actualizados"), HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener datos del mentor",
            description = "Este endpoint permite obtener los datos del mentor a partir del contexto del usuario."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mentor encontrado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MentorDTO.class),
                            examples = @ExampleObject(value = "{ \"Event\": {...} }")))
    })
    @GetMapping("get")
    public ResponseEntity<Map<String, MentorDTO>> getMentorById() {
        MentorDTO body = new MentorDTO(userService.getUserRolContext(Mentor.class));
        return new ResponseEntity<>(Map.of("Event", body), HttpStatus.OK);
    }
    @Operation(
            summary = "Actualizar imagen del mentor",
            description = "Este endpoint permite actualizar la imagen de perfil de un mentor."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imagen actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"Img\": \"Imagen cargada\" }")))
    })
    @PostMapping("img")
    public ResponseEntity<Map<String, String>> updateFile(@RequestPart("file") MultipartFile file) {
        mentorService.changeImg(file, request);
        return new ResponseEntity<>(Map.of("Img", "Imagen cargada"), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Eliminar mentor",
            description = "Este endpoint permite eliminar a un mentor."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mentor eliminado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"update\": \"Mentor eliminado\" }")))
    })
    @Transactional
    @DeleteMapping("delete")
    public ResponseEntity<Map<String, String>> deleteStudent() {
        mentorService.delete();
        return new ResponseEntity<>(Map.of("update", "Mentor eliminado"), HttpStatus.OK);
    }
}
