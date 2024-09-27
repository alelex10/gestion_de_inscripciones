package com.PoloIT.GestionDeInscripciones.Controller.Admin;

import com.PoloIT.GestionDeInscripciones.DTO.MentorDTO;
import com.PoloIT.GestionDeInscripciones.DTO.StudentDTO;
import com.PoloIT.GestionDeInscripciones.DTO.admin.AdminUpdateDTO;
import com.PoloIT.GestionDeInscripciones.Services.AdminServiceImpl;
import com.PoloIT.GestionDeInscripciones.Services.MentorServiceImpl;
import com.PoloIT.GestionDeInscripciones.Services.StudentServiceImpl;
import com.PoloIT.GestionDeInscripciones.Services.UserServiceImpl;
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
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.Map;
@Tag(name = "Administrador", description = "API de Controles para el Administrador")
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserServiceImpl userService;
    private final StudentServiceImpl studentService;
    private final MentorServiceImpl mentorServiceImpl;
    private final AdminServiceImpl adminService;
    private final HttpServletRequest request;

    @Operation(summary = "Eliminar un usuario por ID",
            description = "Elimina un usuario del sistema a partir de su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
            })
    @Transactional
    @DeleteMapping("deleteUser/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(
            @Parameter(description = "ID del usuario a eliminar", example = "1")
            @PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(Map.of("delete", "user delete"), HttpStatus.OK);
    }
    @Operation(
            summary = "Obtener un estudiante por ID",
            description = "Este endpoint devuelve la información de un estudiante basado en su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalles del estudiante encontrados",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("getStudent/{id}")
    public ResponseEntity<Map<String, StudentDTO>> getStudentByID(
            @Parameter(description = "ID del estudiante", example = "1")
            @PathVariable Long id) {
        StudentDTO body = studentService.getById(id);
        return new ResponseEntity<>(Map.of("Event", body), HttpStatus.OK);
    }
    @Operation(
            summary = "Obtener un mentor por ID",
            description = "Este endpoint devuelve la información de un mentor basado en su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalles del mentor encontrados",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MentorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Mentor no encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("getMentor/{id}")
    public ResponseEntity<Map<String, MentorDTO>> getMentorByID(
            @Parameter(description = "ID del mentor", example = "1")
            @PathVariable Long id) {
        MentorDTO body = mentorServiceImpl.getById(id);
        return new ResponseEntity<>(Map.of("Event", body), HttpStatus.OK);
    }

    @Operation(
            summary = "Actualizar datos del administrador",
            description = "Este endpoint permite actualizar los datos de un administrador mediante un objeto `AdminUpdateDTO`."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Datos del administrador actualizados exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"update\": \"Datos actualizados\" }"))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida - datos incorrectos o faltantes",
                    content = @Content)
    })
    @Transactional
    @PatchMapping("update")
    public ResponseEntity<Map<String, String>> update(
            @Valid @RequestBody AdminUpdateDTO adminUpdateDTO) {
        adminService.update(adminUpdateDTO);
        return new ResponseEntity<>(Map.of("update", "Datos actualizados"), HttpStatus.OK);
    }

    @Operation(
            summary = "Subir imagen de perfil de administrador",
            description = "Este endpoint permite subir una imagen para el perfil del administrador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imagen cargada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"Img\": \"Imagen cargada\" }"))),

    })
    @PostMapping("img")
    public ResponseEntity<Map<String, String>> updateFile(@RequestPart("file") MultipartFile file) {
        adminService.changeImg(file, request);
        return new ResponseEntity<>(Map.of("Img", "Imagen cargada"), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Actualizar los datos de un estudiante",
            description = "Este endpoint permite actualizar los datos de un estudiante utilizando su información proporcionada en el cuerpo de la solicitud."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Datos del estudiante actualizados exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"update\": \"Datos actualizados\" }"))),
            @ApiResponse(responseCode = "401", description = "ID es requerido",
                    content = @Content),
    })
    @PatchMapping("updateStudent")
    public ResponseEntity<Map<String, String>> update(
            @RequestBody(description = "Datos del estudiante para actualizar, el id es requerido", required = true,
                    content = @Content(schema = @Schema(implementation = StudentDTO.class)))
            @Valid StudentDTO studentDTO) {
        studentService.update(studentDTO);
        return new ResponseEntity<>(Map.of("update", "Datos actualizados"), HttpStatus.OK);
    }
    @Operation(
            summary = "Actualizar los datos de un mentor",
            description = "Este endpoint permite actualizar los datos de un mentor utilizando su información proporcionada en el cuerpo de la solicitud."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Datos del mentor actualizados exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"update\": \"Datos actualizados\" }"))),
            @ApiResponse(responseCode = "401", description = "ID es requerido",
                    content = @Content),
    })
    @PatchMapping("updateMentor")
    public ResponseEntity<Map<String, String>> update(
            @RequestBody(description = "Datos del mentor para actualizar, el id es requerido", required = true)
            @Valid MentorDTO mentorDTO) {
        mentorServiceImpl.update(mentorDTO);
        return new ResponseEntity<>(Map.of("update", "Datos actualizados"), HttpStatus.OK);
    }

}
