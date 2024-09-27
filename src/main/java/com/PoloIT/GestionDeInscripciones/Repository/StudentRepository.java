package com.PoloIT.GestionDeInscripciones.Repository;

import com.PoloIT.GestionDeInscripciones.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
