package org.mambey.gestiondestock.services.strategy;

import java.io.IOException;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public interface Strategy {
    
    String savePhoto(Integer id, MultipartFile photo);

    byte[] getPhoto(Integer id) throws IOException;

    default String getFileExtension(MultipartFile file){

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));

        return fileExtension;
    }
}
