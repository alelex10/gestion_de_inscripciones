package com.PoloIT.GestionDeInscripciones.DTO;

import com.PoloIT.GestionDeInscripciones.Entity.Team;

import java.util.Set;
import java.util.stream.Collectors;

public record TeamDTO(
        Long id,
        Set<MentorDTO> mentors, Set<StudentDTO> students
) {
    public TeamDTO(Team team) {
        this(team.getId(), team.getMentors().stream().map(MentorDTO::new).collect(Collectors.toSet()), team.getStudents().stream().map(StudentDTO::new).collect(Collectors.toSet()));
    }

    public static TeamDTO converTeamDTO(Team team) {
        return new TeamDTO(team);
    }

    public static Team converTeam(TeamDTO teamDTO) {
        return Team.builder()
                .students(teamDTO.students.stream().map(StudentDTO::fromStudent).collect(Collectors.toSet()))
                .mentors(teamDTO.mentors.stream().map(MentorDTO::fromMentor).collect(Collectors.toSet()))
                .id(teamDTO.id)
                .build();
    }

}
