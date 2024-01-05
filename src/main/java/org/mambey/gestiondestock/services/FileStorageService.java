package org.mambey.gestiondestock.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    
    String storeFile(MultipartFile file, int idArticle, String codeArticle, String context);

    String getUploadFolder(String context);

    byte[] getPhoto(String context, int id) throws IOException;
}
