package com.PoloIT.GestionDeInscripciones.Services;

import com.PoloIT.GestionDeInscripciones.Config.ExecptionControll.ResponseException;
import com.PoloIT.GestionDeInscripciones.DTO.MentorDTO;
import com.PoloIT.GestionDeInscripciones.DTO.StudentDTO;
import com.PoloIT.GestionDeInscripciones.Entity.Mentor;
import com.PoloIT.GestionDeInscripciones.Entity.Student;
import com.PoloIT.GestionDeInscripciones.Entity.User;
import com.PoloIT.GestionDeInscripciones.Enums.Rol;
import com.PoloIT.GestionDeInscripciones.Repository.MentorRepository;
import com.PoloIT.GestionDeInscripciones.Repository.UserRepository;
import com.PoloIT.GestionDeInscripciones.Utils.FileUserServices;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl {
    private final UserRepository userRepository;
    private final MentorRepository mentorRepository;
    private final UserServiceImpl userService;
    private final FileUserServices fileUserServices;

    public void update(MentorDTO mentorDTO) {
        mentorRepository.save(dateUpdate(mentorDTO));
    }

    private Mentor dateUpdate(MentorDTO mentorDTO) {
        Mentor mentor = MentorDTO.fromMentor(mentorDTO);

        User userContext = userService.getUserContext();
        if (userContext.getRol()== Rol.ADMIN){
            if (Objects.isNull(mentorDTO.id()))
                throw new ResponseException("404", "Id requerido", HttpStatus.NOT_FOUND);
            mentor.setId(mentorDTO.id());
        } else if (userContext.getRol() == Rol.MENTOR)
            mentor.setId(userService.getUserContext().getId());
        return mentor;
    }

    public MentorDTO getById(Long id) {
        return mentorRepository.findById(id).map(MentorDTO::new)
                .orElseThrow(() -> new ResponseException("404", "Mentor not Fount", HttpStatus.NOT_FOUND));
    }

    public Map<String, List<MentorDTO>> allMentor() {
        List<MentorDTO> mentorDTOS = mentorRepository.findAll()
                .stream().map(MentorDTO::new)
                .sorted(Comparator.comparing(MentorDTO::id))
                .toList();
        return Map.of("Mentores", mentorDTOS);
    }

    public void delete() {
        userService.getUserContext().setDelete(true);
    }
    public void changeImg(MultipartFile file, HttpServletRequest request) {
        Mentor mentor = userService.getUserContext().getMentor();

        if (Objects.isNull(mentor.getImgUrl())) {
            mentor.setImgUrl(fileUserServices.saveFile(file, request));
            mentorRepository.save(mentor);
        }

        fileUserServices.saveFile(mentor.getImgUrl(), file);
    }
}
