package com.PoloIT.GestionDeInscripciones.Repository;

import com.PoloIT.GestionDeInscripciones.Entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistraionRepository extends JpaRepository<Registration, Long> {
}
