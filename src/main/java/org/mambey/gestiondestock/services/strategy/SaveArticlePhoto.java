package org.mambey.gestiondestock.services.strategy;

import java.io.InputStream;

import org.mambey.gestiondestock.dto.ArticleDto;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvalidOperationException;
import org.mambey.gestiondestock.services.ArticleService;
import org.mambey.gestiondestock.services.FlickrService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.flickr4java.flickr.FlickrException;

import lombok.RequiredArgsConstructor;

@Service("articleStrategy")
@RequiredArgsConstructor
public class SaveArticlePhoto implements Strategy{

    private final FlickrService flickrService;
    private final ArticleService articleService;

    @Override
    public ArticleDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException{

        ArticleDto article = articleService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);
        if(StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        article.setPhoto(urlPhoto);
        
        return articleService.save(article);
    }
    
}
