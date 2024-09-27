package com.PoloIT.GestionDeInscripciones.Services;

import com.PoloIT.GestionDeInscripciones.Config.ExecptionControll.ResponseException;
import com.PoloIT.GestionDeInscripciones.DTO.MentorDTO;
import com.PoloIT.GestionDeInscripciones.DTO.StudentDTO;
import com.PoloIT.GestionDeInscripciones.DTO.password.UpdatePasswordDTO;
import com.PoloIT.GestionDeInscripciones.Entity.Mentor;
import com.PoloIT.GestionDeInscripciones.Entity.Student;
import com.PoloIT.GestionDeInscripciones.Entity.User;
import com.PoloIT.GestionDeInscripciones.Repository.MentorRepository;
import com.PoloIT.GestionDeInscripciones.Repository.StudentRepository;
import com.PoloIT.GestionDeInscripciones.Repository.UserRepository;
import com.PoloIT.GestionDeInscripciones.Utils.FileUserServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserServiceIpml {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder encoder;
    private final MentorRepository mentorRepository;
    private final ObjectMapper objectMapper;
    private final FileUserServices fileUserServices;


    public void registerStudent(String data, MultipartFile file, HttpServletRequest request) {
        Student student = setStudent(data, request, file);
        studentRepository.save(student);
    }

    public void registerMentor(String data, MultipartFile file, HttpServletRequest request) {
        Mentor mentor = setMentor(data, request, file);
        mentorRepository.save(mentor);
    }

    public <U> U getUserRolContext(Class<U> userRol) {
        User userContext = getUserContext();
        return switch (userContext.getRol()) {
            case ADMIN -> userRol.cast(userContext.getAdmin());
            case STUDENT -> userRol.cast(userContext.getStudent());
            case MENTOR -> userRol.cast(userContext.getMentor());
            default -> throw new ResponseException("403", "Forbidden", HttpStatus.FORBIDDEN);
        };
    }


    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseException("404", "Studen not Fount", HttpStatus.NOT_FOUND));
        if (user.isDelete())
            throw new ResponseException("403", "Student is deleted", HttpStatus.FORBIDDEN);
        user.setDelete(true);
    }

    public void updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        User user = this.getUserContext();
        if (!encoder.matches(updatePasswordDTO.oldPassword(), user.getPassword()))
            throw new ResponseException("401", "Incorrect old password", HttpStatus.UNAUTHORIZED);
        if (!updatePasswordDTO.confirmPassword().equals(updatePasswordDTO.newPassword()))
            throw new ResponseException("400", "Passwords do not match", HttpStatus.BAD_REQUEST);
        user.setPassword(encoder.encode(updatePasswordDTO.newPassword()));
    }

    private Student setStudent(String data, HttpServletRequest request, MultipartFile file) {
        Student student = dataToStudent(data);
        student.setImgUrl(fileUserServices.saveFile(file, request));
        return student;
    }

    private Mentor setMentor(String data, HttpServletRequest request, MultipartFile file) {
        Mentor mentor = dataToMentor(data);
        mentor.setImgUrl(fileUserServices.saveFile(file, request));
        return mentor;
    }

    private Student dataToStudent(String data) {
        try {
            StudentDTO eventDTO = objectMapper.readValue(data, StudentDTO.class);
            return StudentDTO.fromStudent(eventDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseException("404", "ERROR EN EL SERVIDOR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Mentor dataToMentor(String data) {
        try {
            MentorDTO mentorDTO = objectMapper.readValue(data, MentorDTO.class);
            return MentorDTO.fromMentor(mentorDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseException("404", "ERROR EN EL SERVIDOR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public User getUserContext() {
        return userRepository.findByEmail(
                        SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new ResponseException("404", "User no Fount", HttpStatus.NOT_FOUND));
    }


    public User getUserByID(Long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new ResponseException("404", "User no Fount", HttpStatus.NOT_FOUND));
    }
}
