package com.PoloIT.GestionDeInscripciones.Controller.Public;

import com.PoloIT.GestionDeInscripciones.Services.AuthServiceImpl;
import com.PoloIT.GestionDeInscripciones.Services.EventServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/data/")
@RequiredArgsConstructor
public class SetDataDev {
    private final AuthServiceImpl authService;
    private final EventServiceImpl eventService;

    @GetMapping("")
    public ResponseEntity<?> saveDB() {
        authService.seedData();
        return ResponseEntity.ok("mentores y student creados.");
    }

    @GetMapping("{id}")
    public ResponseEntity<?> registerUserEvent(@PathVariable Long id) {
        eventService.registerUserEvent(id);
        return ResponseEntity.ok("mentores y student  registrados en el evento..");
    }
}
