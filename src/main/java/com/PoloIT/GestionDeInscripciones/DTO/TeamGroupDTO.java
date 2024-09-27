package com.PoloIT.GestionDeInscripciones.DTO;

import com.PoloIT.GestionDeInscripciones.Entity.TeamGroup;

import java.util.Set;
import java.util.stream.Collectors;

public record TeamGroupDTO(Set<TeamDTO> teams) {
    public TeamGroupDTO(TeamGroup teamGroup) {
        this(teamGroup.getTeams().stream().map(TeamDTO::new).collect(Collectors.toSet()));
    }

    public static TeamGroupDTO converTeamGroupDTO(TeamGroup teamGroup) {
        return new TeamGroupDTO(teamGroup);
    }
}
