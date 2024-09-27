package com.PoloIT.GestionDeInscripciones.Controller.Admin;

import com.PoloIT.GestionDeInscripciones.DTO.MentorDTO;
import com.PoloIT.GestionDeInscripciones.DTO.StudentDTO;
import com.PoloIT.GestionDeInscripciones.DTO.password.UpdatePasswordDTO;
import com.PoloIT.GestionDeInscripciones.Services.MentorServiceImpl;
import com.PoloIT.GestionDeInscripciones.Services.StudentServiceImpl;
import com.PoloIT.GestionDeInscripciones.Services.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

import java.util.List;
import java.util.Map;

@Tag(name = "Usuarios", description = "API de Controles para los Usuarios")
@RestController
@RequestMapping("/api/v1/admin/user/")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private final StudentServiceImpl studentService;
    private final HttpServletRequest request;
    private final MentorServiceImpl mentorService;

    @Operation(
            summary = "Registrar un estudiante",
            description = "Este endpoint permite registrar un estudiante proporcionando los datos en formato JSON y una imagen de perfil."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"Usuario\": \"Se creo el usuario.\" }")))})
//algunos controladores no deverian estar en el AdminController? como save/mentor save/student
//tengo el controlador para cambiar la contraseña de los usuarios aqui XD
    @PostMapping("save/student")
    public ResponseEntity<Map<String, String>> registerStudent(@RequestPart("data") String data, @RequestPart("file") MultipartFile file) {
        userService.registerStudent(data, file, request);
        return new ResponseEntity<>(Map.of("Usuario", "Se creo el usuario."), HttpStatus.OK);
    }
    @Operation(
            summary = "Registrar un mentor",
            description = "Este endpoint permite registrar un mentor proporcionando los datos en formato JSON y una imagen de perfil."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"Usuario\": \"Se creo el usuario.\" }")))
    })
    @PostMapping("save/mentor")
    public ResponseEntity<Map<String, String>> registerMentor(@RequestPart("data") String data, @RequestPart("file") MultipartFile file) {
        userService.registerMentor(data, file, request);
        return new ResponseEntity<>(Map.of("Usuario", "Se creo el usuario."), HttpStatus.OK);
    }

    @Operation(
            summary = "Eliminar un estudiante",
            description = "Este endpoint permite eliminar un estudiante utilizando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @Transactional
    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, String>> deleteStudent(
            @Parameter(description = "ID del estudiante a eliminar", required = true) @PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(Map.of("update", "user eliminado"), HttpStatus.OK);
    }
    @Operation(
            summary = "Obtener un estudiante por ID",
            description = "Este endpoint retorna la información de un estudiante utilizando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante obtenido exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado", content = @Content)
    })
    @GetMapping("student/{id}")
    public ResponseEntity<Map<String, StudentDTO>> getStudentById(@PathVariable Long id) {
        StudentDTO body = studentService.getById(id);
        return new ResponseEntity<>(Map.of("User", body), HttpStatus.OK);
    }
    @Operation(
            summary = "Obtener un mentor por ID",
            description = "Este endpoint retorna la información de un mentor utilizando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mentor obtenido exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MentorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Mentor no encontrado", content = @Content)
    })
    @GetMapping("mentor/{id}")
    public ResponseEntity<Map<String, MentorDTO>> getMentorById(@PathVariable Long id) {
        MentorDTO body = mentorService.getById(id);
        return new ResponseEntity<>(Map.of("User", body), HttpStatus.OK);
    }


    @GetMapping("")
    public ResponseEntity<Map<String, Object>> allUser(@PathVariable Long id) {
        Map<String, List<MentorDTO>> allMentor = mentorService.allMentor();
        Map<String, List<StudentDTO>> allStudent = studentService.allStudent();
        return new ResponseEntity<>(Map.of("Student", allStudent, "Mentors", allMentor), HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener todos los estudiantes",
            description = "Este endpoint retorna una lista de todos los estudiantes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiantes obtenidos exitosamente",
                    content = @Content)
    })
    @GetMapping("students")
    public ResponseEntity<Map<String, List<StudentDTO>>> allStudent() {
        Map<String, List<StudentDTO>> body = studentService.allStudent();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener todos los mentores",
            description = "Este endpoint retorna una lista de todos los mentores."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mentores obtenidos exitosamente",
                    content = @Content)
    })
    @GetMapping("mentors")
    public ResponseEntity<Map<String, List<MentorDTO>>> allMentors() {
        Map<String, List<MentorDTO>> body = mentorService.allMentor();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @Operation(
            summary = "Actualizar contraseña",
            description = "Este endpoint permite actualizar la contraseña de un usuario, solo si esta Autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Contraseña actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{ \"msg\": \"The password was updated successfully\" }")))
    })
    @Transactional
    @PatchMapping("updatePassword")
    public ResponseEntity<Map<String, String>> updatePassword(
            @RequestBody(description = "DTO para actualizar la contraseña del usuario", required = true,
                    content = @Content(schema = @Schema(implementation = UpdatePasswordDTO.class)))
            @Valid UpdatePasswordDTO updatePasswordDTO) {
        userService.updatePassword(updatePasswordDTO);
        return new ResponseEntity<>(Map.of("msg", "The password was updated successfully"), HttpStatus.ACCEPTED);
    }

}
