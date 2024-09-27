package com.PoloIT.GestionDeInscripciones.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Registration {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "event_id")
    private Event event;

    private LocalDateTime createdAt;
    private LocalDateTime finishAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @ManyToMany
    @JoinTable(
            name = "registration_mentors",
            joinColumns = @JoinColumn(name = "registration_id"),
            inverseJoinColumns = @JoinColumn(name = "mentor_id")
    )
    private Set<Mentor> mentors;

    @ManyToMany
    @JoinTable(
            name = "registration_students",
            joinColumns = @JoinColumn(name = "registration_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Registration that = (Registration) o;
        return Objects.equals(event.getId(), that.event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(event.getId());
    }
}
