package com.PoloIT.GestionDeInscripciones.Services;

import com.PoloIT.GestionDeInscripciones.Config.ExecptionControll.ResponseException;
import com.PoloIT.GestionDeInscripciones.DTO.StudentDTO;
import com.PoloIT.GestionDeInscripciones.DTO.admin.AdminUpdateDTO;
import com.PoloIT.GestionDeInscripciones.Entity.Admin;
import com.PoloIT.GestionDeInscripciones.Entity.Mentor;
import com.PoloIT.GestionDeInscripciones.Repository.AdminRepository;
import com.PoloIT.GestionDeInscripciones.Utils.FileUserServices;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl {
    private final AdminRepository adminRepository;
    private final UserServiceImpl userService;
    private final FileUserServices fileUserServices;

    public void update(AdminUpdateDTO adminUpdateDTO) {
        Admin admin = userService.getUserContext().getAdmin();
        admin.update(adminUpdateDTO);
    }
    public void changeImg(MultipartFile file, HttpServletRequest request) {
        Admin admin = userService.getUserContext().getAdmin();

        if (Objects.isNull(admin.getImgUrl())) {
            admin.setImgUrl(fileUserServices.saveFile(file, request));
            adminRepository.save(admin);
        }

        fileUserServices.saveFile(admin.getImgUrl(), file);
    }
}
