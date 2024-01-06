package org.mambey.gestiondestock.services.strategy;


import java.io.IOException;

import org.mambey.gestiondestock.dto.ArticleDto;
import org.mambey.gestiondestock.services.ArticleService;
import org.mambey.gestiondestock.services.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import lombok.RequiredArgsConstructor;

@Service("articleStrategy")
@RequiredArgsConstructor
public class SaveArticlePhoto implements Strategy{

    private final FileStorageService fileStorageService;
    private final ArticleService articleService;
    private final String nomRepertoire = "article";//Nom du repertoire qui doit contenir les fichiers

    @Override
    public String savePhoto(Integer idArticle, MultipartFile file){

        ArticleDto article = articleService.findById(idArticle);

        //Stratégie de nommage: id_codeArticle
        String nomFichier = idArticle + "_" + article.getCodeArticle() + this.getFileExtension(file);

        String response = fileStorageService.storeFile(file, nomFichier, nomRepertoire);

        article.setPhoto(nomFichier);
        
        articleService.save(article);

        return response;
    }

    public byte[] getPhoto(Integer id) throws IOException{

        ArticleDto article = articleService.findById(id);

        return fileStorageService.getPhoto(nomRepertoire, article.getPhoto());
    }
    
}
