package com.PoloIT.GestionDeInscripciones.Utils;

import com.PoloIT.GestionDeInscripciones.Config.ExecptionControll.ResponseException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileEventServices {
    @Value("${image.directory.event}")
    private Path directoryPath;

    public String saveFile(MultipartFile file, HttpServletRequest request) {
        String fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());

        try {
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

        } catch (IOException e) {
            throw new ResponseException("500", "Error creating directory: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            Files.copy(file.getInputStream(), directoryPath.resolve(fileName));
        } catch (IOException e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new ResponseException("404", "A file of that name already exists.", HttpStatus.CONFLICT);
            }
            throw new ResponseException("500", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String url = ServletUriComponentsBuilder
                .fromHttpUrl(host)
                .path("/api/v1/public/media/event/")
                .path(fileName)
                .toUriString();

        return url;
    }


    public void saveFile(String fileName, MultipartFile file) {
        String result = fileName.substring(fileName.indexOf("event/") + "event/".length());
        Path filePath = directoryPath.resolve(result);

        try {

            if (!Files.exists(directoryPath)) {
                throw new ResponseException("500", "ERRROR EN EL SERVIDOR", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }

            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new ResponseException("404", "A file of that name already exists.", HttpStatus.CONFLICT);
            }
            throw new ResponseException("500", "Error saving file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    public Resource loadResource(String filename) {

        try {

            Path file = directoryPath.resolve(filename);
            Resource resource = new UrlResource((file.toUri()));

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }

            throw new ResponseException("404", "No existe la imagen " + filename, HttpStatus.NOT_FOUND);

        } catch (MalformedURLException e) {

            throw new ResponseException("404", "No existe la imagen " + filename, HttpStatus.NOT_FOUND);
        }
    }


}
