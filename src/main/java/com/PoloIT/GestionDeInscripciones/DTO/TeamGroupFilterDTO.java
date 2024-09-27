package com.PoloIT.GestionDeInscripciones.DTO;

import java.util.List;

public record TeamGroupFilterDTO(
        int groups,
        List<TeamFilter> studentFilter,
        List<TeamFilter> mentorFilter
) {
}
