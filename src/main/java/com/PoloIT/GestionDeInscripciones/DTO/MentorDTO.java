package com.PoloIT.GestionDeInscripciones.DTO;

import com.PoloIT.GestionDeInscripciones.Entity.Mentor;

import java.time.LocalDate;
import java.util.Set;

public record MentorDTO(
        Long id,
        String name,
        String lastName,
        String company,
        Set<String> skills,
        Set<String> profiles,
        String linkedin,
        String phone,
        LocalDate birthdate, // Corregido a minúscula
        String nationality,
        String dni,
        String imgUrl
) {

    //* Constructor que convierte una entidad Mentor a MentorDTO.
    public MentorDTO(Mentor mentor) {
        this(
                mentor.getId(),
                mentor.getName(),
                mentor.getLastName(),
                mentor.getCompany(),
                mentor.getTechnologies(),
                mentor.getRol(),
                mentor.getLinkedin(),
                mentor.getPhone(),
                mentor.getBirthdate(),
                mentor.getNationality(),
                mentor.getDni(),
                mentor.getImgUrl()
        );
    }

    //* Convierte una entidad Mentor a MentorDTO utilizando el DTO actual.
    public static MentorDTO fromMentorDTO(Mentor mentor) {
        return new MentorDTO(
                mentor.getId(),
                mentor.getName(),
                mentor.getLastName(),
                mentor.getCompany(),
                mentor.getTechnologies(),
                mentor.getRol(),
                mentor.getLinkedin(),
                mentor.getPhone(),
                mentor.getBirthdate(),
                mentor.getNationality(),
                mentor.getDni(),
                mentor.getImgUrl()
        );
    }

    //* Convierte un MentorDTO a la entidad Mentor.
    public static Mentor fromMentor(MentorDTO mentorDTO) {
        return Mentor.builder()
                .id(mentorDTO.id)
                .name(mentorDTO.name)
                .lastName(mentorDTO.lastName)
                .company(mentorDTO.company)
                .technologies(mentorDTO.skills)
                .rol(mentorDTO.profiles)
                .linkedin(mentorDTO.linkedin)
                .phone(mentorDTO.phone)
                .birthdate(mentorDTO.birthdate)
                .nationality(mentorDTO.nationality)
                .dni(mentorDTO.dni)
//la URL de la img no debería actualizarse nunca, a no ser que se cree la img por 1ra vez
                .build();
    }
}

