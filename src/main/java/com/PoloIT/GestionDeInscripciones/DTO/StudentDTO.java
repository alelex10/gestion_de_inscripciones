package com.PoloIT.GestionDeInscripciones.DTO;

import com.PoloIT.GestionDeInscripciones.Entity.Student;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record StudentDTO(
        Long id,
        @NotNull(message = "name requerido")
        String name,
        String imgUrl,
        @NotNull(message = "lastname requerido")
        String lastName,
        @NotNull(message = "technologies requerido")
        Set<String> technologies,
        @NotNull(message = "courses requerido")
        Set<String> courses,
        @NotNull(message = "rol requerido")
        Set<String> rol,
        @NotNull(message = "linkedin requerido")
        String linkedin,
        String dni,
        String phone,
        LocalDate birthdate,
        String nationality,
        String graduationYear
) {

    public StudentDTO(Student student) {
        this(
                student.getId(),
                student.getName(),
                student.getImgUrl(),
                student.getLastName(),
                student.getTechnologies(),
                student.getCourses(),
                student.getRol(),
                student.getLinkedin(),
                student.getDni(),
                student.getPhone(),
                student.getBirthdate(),
                student.getNationality(),
                student.getGraduationYear()
        );
    }

    public static Student fromStudent(StudentDTO studentDTO) {
        return Student.builder()
                .id(studentDTO.id)
                .name(studentDTO.name)
                .imgUrl(studentDTO.imgUrl)
                .rol(studentDTO.rol)
                .technologies(studentDTO.technologies)
                .lastName(studentDTO.lastName)
                .courses(studentDTO.courses)
                .linkedin(studentDTO.linkedin)
                .graduationYear(studentDTO.graduationYear)
                .dni(studentDTO.dni)
                .phone(studentDTO.phone)
                .birthdate(studentDTO.birthdate)
                .nationality(studentDTO.nationality)
                .build();
    }

    public Student convertStudent() {
        return Student.builder()
                .id(this.id)
                .name(this.name)
                .imgUrl(this.imgUrl)
                .rol(this.rol)
                .technologies(this.technologies)
                .lastName(this.lastName)
                .courses(this.courses)
                .linkedin(this.linkedin)
                .graduationYear(this.graduationYear)
                .dni(this.dni)
                .phone(this.phone)
                .birthdate(this.birthdate)
                .nationality(this.nationality)
                .build();
    }
}
