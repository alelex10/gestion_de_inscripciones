package com.PoloIT.GestionDeInscripciones.DTO;

import com.PoloIT.GestionDeInscripciones.Entity.Event;
import com.PoloIT.GestionDeInscripciones.Entity.Registration;
import com.PoloIT.GestionDeInscripciones.Entity.TeamGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EventDTO(
        Long id,
        @NotNull(message = "Name required")
        @NotEmpty(message = "Name required")
        String name,
        @NotNull(message = "description required")
        @NotEmpty(message = "description required")
        String description,
        String imgURL,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime finishAt,
        @NotNull(message = "registration required")
        RegistrationDTO registration,
        TeamGroupDTO teamGroup,
        boolean isActive,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime updatedAt
) {


    public EventDTO(Event event) {
        this(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getImg(),
                event.getCreatedAt(),
                event.getFinishAt(),
                RegistrationDTO.convertRegistrationDTO(event.getRegistration()),
                TeamGroupDTO.converTeamGroupDTO(event.getTeamGroup()),
                event.isActive(),
                event.getUpdatedAt()
        );
    }

    //
    public static Event fromEvent(EventDTO eventDTO) {
        Event event = Event.builder()
                .name(eventDTO.name())
                .registration(Registration.builder()
                        .finishAt(eventDTO.registration().finishAt())
                        .createdAt(eventDTO.registration().createdAt())
                        .build())
                .teamGroup(TeamGroup.builder()
                        .updatedAt(eventDTO.registration().createdAt())
                        .build())
                .description(eventDTO.description())
                .createdAt(eventDTO.createdAt())
                .finishAt(eventDTO.finishAt())
                .isActive(true)
                .updatedAt(eventDTO.updatedAt)
                .build();
        event.getRegistration().setEvent(event);
        event.getTeamGroup().setEvent(event);
        return event;
    }
}
