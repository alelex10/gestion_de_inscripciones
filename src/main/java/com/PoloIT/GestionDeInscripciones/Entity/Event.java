package com.PoloIT.GestionDeInscripciones.Entity;

import com.PoloIT.GestionDeInscripciones.DTO.EventDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Getter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String img;

    @Column(name = "is_active")
    @JsonProperty("isActive")
    private boolean isActive;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime finishAt;

    //* RELATIONSHIP WITH REGISTRATION
    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Registration registration;

    //* RELATIONSHIP WITH TEAMS
    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private TeamGroup teamGroup;


    //* RELATIONSHIP WITH ADMIN
    @ManyToOne()
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public Event(EventDTO eventDTO, Admin admin) {
        this.name = eventDTO.name();
        this.description = eventDTO.description();
        this.admin = admin;
    }


    public void update(EventDTO eventDTO) {
        this.name = eventDTO.name();
        this.description = eventDTO.description();
        this.isActive = eventDTO.isActive();
        this.finishAt = eventDTO.finishAt();

    }

    public void deactivateEvent() {
        this.isActive = false;
    }
}
