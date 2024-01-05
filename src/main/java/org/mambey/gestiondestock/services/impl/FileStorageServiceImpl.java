package org.mambey.gestiondestock.services.impl;

import org.mambey.gestiondestock.dto.ArticleDto;
import org.mambey.gestiondestock.exception.EntityNotFoundException;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.services.ArticleService;
import org.mambey.gestiondestock.services.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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

    private final ArticleService articleService;

    @Value("${uploadDir}")
    private String uploadDir;

    @Value("${defaultImageName}")
    private String defaultImageName;

    @Override
    public String storeFile(MultipartFile file, int idArticle, String codeArticle, String context) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        fileName = idArticle + "_" + codeArticle + fileExtension;
        ArticleDto article = articleService.findById(idArticle);

        try {

            Path uploadPath = Paths.get(this.getUploadFolder(context));

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            article.setPhoto(fileName);
            articleService.save(article);

            //return filePath.toString();
            return String.valueOf(fileName);

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + fileName, e);
        }
    }

    public byte[] getPhoto(String context, int id) throws IOException{

        ArticleDto article = articleService.findById(id);

        File file = new File(this.getUploadFolder(context) + article.getPhoto());

        if(file.isFile() && file.exists()){
            Path filePath = file.toPath();
            return Files.readAllBytes(filePath);
        }else{
            File defaultFile = new File(this.getUploadFolder(context) + this.defaultImageName);
            
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

