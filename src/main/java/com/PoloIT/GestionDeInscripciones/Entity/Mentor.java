package com.PoloIT.GestionDeInscripciones.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Getter
public class Mentor {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String lastName;
    private Set<String> rol;
    private String dni;
    private String phone;
    private LocalDate birthdate;
    private String nationality;
    private Set<String> technologies;
    private String linkedin;
    private String studies;
    private String imgUrl;
    //! crear la compania entidad?
    private String company;
    //*Registration
    @ManyToMany(mappedBy = "mentors")
    private Set<Registration> registrations;


    //*TEAMS
    @ManyToMany(mappedBy = "mentors")
    private Set<Team> team;
    //*COMPANIES
    @ManyToMany(mappedBy = "mentors")
    private Set<Company> companies;

    public boolean areFieldsValid() {
        return this.name != null &&
                this.company != null &&
                this.technologies != null && !this.technologies.isEmpty() &&
                this.linkedin != null &&
                this.rol != null && !this.rol.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mentor mentor = (Mentor) o;
        return user != null && user.getId() != null &&
                user.getId().equals(mentor.user != null ? mentor.user.getId() : null);
    }

    @Override
    public int hashCode() {
        return user != null && user.getId() != null ? Objects.hash(user.getId()) : 0;
    }

}
