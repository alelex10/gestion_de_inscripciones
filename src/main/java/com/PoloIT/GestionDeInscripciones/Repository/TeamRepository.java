package com.PoloIT.GestionDeInscripciones.Repository;

import com.PoloIT.GestionDeInscripciones.Entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
