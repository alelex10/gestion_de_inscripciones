package com.PoloIT.GestionDeInscripciones.Services;

import com.PoloIT.GestionDeInscripciones.Config.ExecptionControll.ResponseException;
import com.PoloIT.GestionDeInscripciones.DTO.StudentDTO;
import com.PoloIT.GestionDeInscripciones.Entity.Student;
import com.PoloIT.GestionDeInscripciones.Entity.User;
import com.PoloIT.GestionDeInscripciones.Enums.Rol;
import com.PoloIT.GestionDeInscripciones.Repository.StudentRepository;
import com.PoloIT.GestionDeInscripciones.Utils.FileUserServices;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl {
    private final StudentRepository studentRepository;
    private final UserServiceImpl userService;
    private final FileUserServices fileUserServices;

    public void update(StudentDTO studentDTO) {
        studentRepository.save(dateUpdate(studentDTO));
    }

    private Student dateUpdate(StudentDTO studentDTO) {

        Student student = StudentDTO.fromStudent(studentDTO);

        User userContext = userService.getUserContext();
        if (userContext.getRol()== Rol.ADMIN){
            if (Objects.isNull(studentDTO.id()))
                throw new ResponseException("404", "Id requerido", HttpStatus.NOT_FOUND);
            student.setId(studentDTO.id());
        } else if (userContext.getRol() == Rol.STUDENT)
            student.setId(userService.getUserContext().getId());
        return student;
    }

    public StudentDTO getById(Long id) {
        return studentRepository.findById(id).map(StudentDTO::new)
                .orElseThrow(() -> new ResponseException("404", "Studen not Fount", HttpStatus.NOT_FOUND));
    }

    public void changeImg(MultipartFile file, HttpServletRequest request) {
        Student student = userService.getUserContext().getStudent();

        if (Objects.isNull(student.getImgUrl())) {
            student.setImgUrl(fileUserServices.saveFile(file, request));
            studentRepository.save(student);
        }

        fileUserServices.saveFile(student.getImgUrl(), file);
    }

    public Resource loadResource(String filename) {
        return fileUserServices.loadResource(filename);
    }

    public Map<String, List<StudentDTO>> allStudent() {
        List<StudentDTO> studentDTOS = studentRepository.findAll()
                .stream().map(StudentDTO::new)
                .sorted(Comparator.comparing(StudentDTO::id))
                .toList();
        return Map.of("Estudiantes", studentDTOS);
    }
//user service ya tiene el getUserContext()

    public void delete() {
        userService.getUserContext().setDelete(true);
    }

}
