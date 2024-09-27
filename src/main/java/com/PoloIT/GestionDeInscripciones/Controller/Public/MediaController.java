package com.PoloIT.GestionDeInscripciones.Controller.Public;


import com.PoloIT.GestionDeInscripciones.Services.EventServiceImpl;
import com.PoloIT.GestionDeInscripciones.Services.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/v1/public/media/")
@RequiredArgsConstructor
public class MediaController {
    private final EventServiceImpl eventServiceImpl;
    private final StudentServiceImpl studentService;

    @GetMapping("event/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> loadEvent(@PathVariable String filename) throws IOException, IOException {
        Resource file = eventServiceImpl.loadResource(filename);
        String contentType = Files.probeContentType(file.getFile().toPath());
        //! este es para descargar archivos
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);

    }

    @GetMapping("profile/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> loadProfile(@PathVariable String filename) throws IOException, IOException {
        Resource file = studentService.loadResource(filename);
        String contentType = Files.probeContentType(file.getFile().toPath());
        //! este es para descargar archivos
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);


    }
}
