package com.PoloIT.GestionDeInscripciones.Services;

import com.PoloIT.GestionDeInscripciones.Entity.Company;
import com.PoloIT.GestionDeInscripciones.Repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<String> ImgsUrls() {
        return companyRepository.findAll().stream().map(Company::getImgUrl).toList();
    }
}
