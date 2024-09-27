package com.PoloIT.GestionDeInscripciones.DTO;

import com.PoloIT.GestionDeInscripciones.Entity.Registration;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record RegistrationDTO(
        @NotNull(message = "Required enrollment start date ")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt,
        @NotNull(message = "Required enrollment end date")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime finishAt,
        Set<StudentDTO> Students,
        Set<MentorDTO> Mentors,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime updatedAt) {

    public RegistrationDTO(Registration registration) {
        this(registration.getCreatedAt(), registration.getFinishAt(), registration.getStudents().stream().map(StudentDTO::new).collect(Collectors.toSet()),
                registration.getMentors().stream().map(MentorDTO::new).collect(Collectors.toSet()), registration.getUpdatedAt());
    }

    public static RegistrationDTO convertRegistrationDTO(Registration registration) {
        return new RegistrationDTO(registration);
    }
}
