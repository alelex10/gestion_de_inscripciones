package com.PoloIT.GestionDeInscripciones.Services;

import com.PoloIT.GestionDeInscripciones.Entity.Ong;
import com.PoloIT.GestionDeInscripciones.Repository.OngRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OngService {
    private final OngRepository ongRepository;

    public List<String> ImgsUrls() {
        return ongRepository.findAll().stream().map(Ong::getImgUrl).toList();
    }
}
