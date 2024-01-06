package org.mambey.gestiondestock.services.impl;

import org.mambey.gestiondestock.exception.EntityNotFoundException;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.services.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${uploadDir}")
    private String uploadDir;

    @Value("${defaultImageName}")
    private String defaultImageName;

    @Override
    public String storeFile(MultipartFile file, String nomFichier, String nomRepertoire) {
        
        try {

            Path uploadPath = Paths.get(this.getUploadFolder(nomRepertoire));

            if (!Files.exists(uploadPath)) {

                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(nomFichier);

            //Si un fichier avec le même nom existe on le supprime
            if(Files.exists(filePath)){

                Files.delete(filePath);
            }

            Files.copy(file.getInputStream(), filePath);

            return nomFichier;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + nomFichier, e);
        }
    }

    //Input: nom du repertoire & nom de la photo
    public byte[] getPhoto(String nomReportoire, String nomPhoto) throws IOException{

        File file = new File(this.getUploadFolder(nomReportoire) + nomPhoto);

        if(file.isFile() && file.exists()){
            Path filePath = file.toPath();
            return Files.readAllBytes(filePath);
        }else{
            File defaultFile = new File(this.getUploadFolder(nomReportoire) + this.defaultImageName);
            
            if(defaultFile.isFile() && defaultFile.exists()){
                Path filePath = defaultFile.toPath();
                return Files.readAllBytes(filePath);
            }else{
                throw new EntityNotFoundException(
                    "Fichier introuvable",
                    ErrorCodes.FILE_NOT_FOUND
                );
            }
        }
    }

    @Override
    public String getUploadFolder(String context) {

        String path = context + System.getProperty("file.separator");
        return uploadDir + path;
    }
}

