package org.mambey.gestiondestock.services.strategy;


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

    @Override
    public ArticleDto savePhoto(Integer id, MultipartFile photo){

        ArticleDto article = articleService.findById(id);
        String urlPhoto = fileStorageService.storeFile(photo, article.getId(), article.getCodeArticle(), "article");

        article.setPhoto(urlPhoto);
        
        return articleService.save(article);
    }
    
}
