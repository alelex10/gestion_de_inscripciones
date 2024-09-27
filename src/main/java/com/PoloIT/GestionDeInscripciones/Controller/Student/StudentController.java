package com.PoloIT.GestionDeInscripciones.Controller.Student;


import com.PoloIT.GestionDeInscripciones.DTO.StudentDTO;
import com.PoloIT.GestionDeInscripciones.Entity.Student;
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
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Tag(name = "Estudiante", description = "API de control de Estudiantes")
@RestController
@RequestMapping("/api/v1/student/")
@RequiredArgsConstructor
public class StudentController {

    private final StudentServiceImpl studentService;
    private final UserServiceImpl userService;
    private final HttpServletRequest request;

    @Operation(
            summary = "Actualizar los datos de un estudiante",
            description = "Este endpoint permite actualizar los datos del estudiante autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Datos actualizados exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"update\": \"Datos actualizados\" }"))),
    })
    @PatchMapping("update")
    public ResponseEntity<Map<String, String>> update(@Valid @RequestBody StudentDTO studentDTO) {
        studentService.update(studentDTO);
        return new ResponseEntity<>(Map.of("update", "Datos actualizados"), HttpStatus.OK);
    }
    @Operation(
            summary = "Obtener los datos del estudiante autenticado",
            description = "Este endpoint permite obtener los datos del estudiante que está autenticado en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Datos obtenidos exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class),
                            examples = @ExampleObject(value = "{ \"Event\": { ... } }"))),
            @ApiResponse(responseCode = "401", description = "Usuario no autorizado", content = @Content)
    })
    @GetMapping("get")
    public ResponseEntity<Map<String, StudentDTO>> getStudentById() {
        StudentDTO body = new StudentDTO(userService.getUserRolContext(Student.class));
        return new ResponseEntity<>(Map.of("Event", body), HttpStatus.OK);
    }

    @Operation(
            summary = "Actualizar la imagen del estudiante",
            description = "Este endpoint permite actualizar la imagen del estudiante autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imagen cargada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"Img\": \"Imagen cargada\" }"))),
    })
    @PostMapping("img")
    public ResponseEntity<Map<String, String>> updateImgStudent(
            @Parameter(description = "Imagen del estudiante", required = true)
            @RequestPart("file") MultipartFile file) {
        studentService.changeImg(file, request);
        return new ResponseEntity<>(Map.of("Img", "Imagen cargada"), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Eliminar al estudiante autenticado",
            description = "Este endpoint permite eliminar los datos del estudiante autenticado en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante eliminado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"update\": \"Estudiante eliminado\" }")))
    })
    @Transactional
    @DeleteMapping("delete")
    public ResponseEntity<Map<String, String>> deleteStudent() {
        studentService.delete();
        return new ResponseEntity<>(Map.of("update", "Estudiante eliminado"), HttpStatus.OK);
    }
}
