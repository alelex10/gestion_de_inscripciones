package com.PoloIT.GestionDeInscripciones.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Getter
public class Ong {
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
            name = "ongs_mentors",
            joinColumns = @JoinColumn(name = "ong_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students;
}
