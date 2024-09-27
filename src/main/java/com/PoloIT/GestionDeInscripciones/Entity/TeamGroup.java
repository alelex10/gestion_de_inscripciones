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
@Setter
@Getter
@Builder
public class TeamGroup {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "event_id")
    private Event event;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "teamGroup")
    private Set<Team> teams;


    //! agregar una lista de los que no quedaron.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamGroup teamGroup = (TeamGroup) o;
        return Objects.equals(event.getId(), teamGroup.event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(event.getId());
    }
}
