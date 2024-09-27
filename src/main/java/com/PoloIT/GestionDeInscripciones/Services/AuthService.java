package com.PoloIT.GestionDeInscripciones.Services;

import com.PoloIT.GestionDeInscripciones.DTO.UserDto;

import java.util.Map;

public interface AuthService {
    Map<String, String> authenticate(UserDto userDto);

    Map<String, String> register(UserDto userDto);
}