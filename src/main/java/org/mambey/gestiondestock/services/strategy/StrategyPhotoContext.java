package org.mambey.gestiondestock.services.strategy;

import java.io.InputStream;

import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvalidOperationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

import com.flickr4java.flickr.FlickrException;

import lombok.Setter;

@Service
public class StrategyPhotoContext {
    
    private Strategy strategy;
    private BeanFactory beanFactory;
    @Setter
    private String context;

    public StrategyPhotoContext(BeanFactory beanFactory){

        this.beanFactory = beanFactory;
    }

    public Object savePhoto(String context, Integer id, InputStream photo, String title) throws FlickrException{

        determineContext(context);
        return strategy.savePhoto(id, photo, title);
    }

    private void determineContext(String context){

        final String beanName = context + "Strategy";

        switch(context){

            case "article": 
                strategy = beanFactory.getBean(beanName, SaveArticlePhoto.class);
                break;

            case "client": 
                strategy = beanFactory.getBean(beanName, SaveClientPhoto.class);
                break;

            case "entreprise": 
                strategy = beanFactory.getBean(beanName, SaveEntreprisePhoto.class);
                break;

            case "fournisseur": 
                strategy = beanFactory.getBean(beanName, SaveFournisseurPhoto.class);
                break;

            case "utilisateur": 
                strategy = beanFactory.getBean(beanName, SaveUtilisateurPhoto.class);
                break;

            default: throw new InvalidOperationException("Contexte inconnu pour l'enregistrement de la photo", ErrorCodes.UNKNOWN_CONTEXT);
        }
    }
}
