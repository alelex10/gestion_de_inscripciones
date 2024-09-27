package com.PoloIT.GestionDeInscripciones.Services;

import com.PoloIT.GestionDeInscripciones.Config.ExecptionControll.ResponseException;
import com.PoloIT.GestionDeInscripciones.Entity.Registration;
import com.PoloIT.GestionDeInscripciones.Entity.User;
import com.PoloIT.GestionDeInscripciones.Repository.RegistraionRepository;
import com.PoloIT.GestionDeInscripciones.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl {
    private final UserRepository userRepository;
    private final RegistraionRepository registraionRepository;

    public void register(Long id) {
        Registration registration = registraionRepository.findById(id)
                .map(this::setUserEvent)
                .orElseThrow(() -> new ResponseException("404", "ID NOT FOUND EVENT/REGISTRATION", HttpStatus.NOT_FOUND));
        registraionRepository.save(registration);
    }


    private void isActiveRegister(Registration registration) {
        if (registration.getFinishAt().isBefore(LocalDateTime.now()))
            throw new ResponseException("Event", "Registration ended on " + registration.getFinishAt(), HttpStatus.NOT_ACCEPTABLE);
    }

    private Registration setUserEvent(Registration registration) {
        //! se verifca si ya finalizo el registro al evento.
        isActiveRegister(registration);

        User user = getUserContext();

        isRegistered(user, registration);

        //! Verificamos si student  o mentor están vacíos sin información.
        //! Se requiere que ambos perfiles estén completos para luego crear los grupos.

        if (user.getRol().name().equalsIgnoreCase("mentor")) {
            //? Para pruebas desactivar, ya que si el student  o mentor no tine info no se registra en el evento.
//            if (user.getMentor().areFieldsValid()) {
//                registration.getMentors().add(user.getMentor());
//                return registration;
//            }
            registration.getMentors().add(user.getMentor());
            return registration;
            // throw new ResponseException("Information", "Complete Mentor information", HttpStatus.NOT_ACCEPTABLE);
        }

        if (user.getRol().name().equalsIgnoreCase("student")) {
            //? Para pruebas desactivar, ya que si el student  o mentor no tine info no se registra en el evento.
//            if (user.getStudent().areFieldsValid()) {
//                registration.getStudents().add(user.getStudent());
//                return registration;
//            }

            registration.getStudents().add(user.getStudent());
            return registration;
            //   throw new ResponseException("Information", "Complete Student information", HttpStatus.NOT_ACCEPTABLE);
        }

        throw new ResponseException("505", "INTERNAL SERVER ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void isRegistered(User user, Registration registration) {
        //! verificamos si ya esta registrado en el evento, si lo esta devuelve un mensaje.
        //! No se registraria ya que se usa un SET. Solo le informamos al front.
        if (user.getRol().name().equalsIgnoreCase("studetn") && registration.getStudents().stream().anyMatch(student -> student.getId().equals(user.getStudent().getId()))) {
            throw new ResponseException("200", "Already registered", HttpStatus.ACCEPTED);
        }

        if (user.getRol().name().equalsIgnoreCase("mentor") && registration.getMentors().stream().anyMatch(mentor -> mentor.getId().equals(user.getMentor().getId()))) {
            throw new ResponseException("200", "Already registered", HttpStatus.ACCEPTED);
        }
    }

    private User getUserContext() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new ResponseException("505", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR));
    }


}
