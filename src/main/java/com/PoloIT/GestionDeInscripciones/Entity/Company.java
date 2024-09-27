package com.PoloIT.GestionDeInscripciones.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class Company {
    @Id
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
    private String name;
    private String imgUrl;

    @ManyToMany()
    @JoinTable(
            name = "company_mentor",
            joinColumns = @JoinColumn(name = "compañy_id"),
            inverseJoinColumns = @JoinColumn(name = "mentor_id")
    )
    private Set<Mentor> mentors;
}
