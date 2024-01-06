package org.mambey.gestiondestock.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    
    String storeFile(MultipartFile file, String fileName, String context);

    String getUploadFolder(String context);

    byte[] getPhoto(String nomRepertoire, String nomPhoto) throws IOException;
}
