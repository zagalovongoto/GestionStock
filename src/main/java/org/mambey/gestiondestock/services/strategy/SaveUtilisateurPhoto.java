package org.mambey.gestiondestock.services.strategy;

import java.io.InputStream;

import org.mambey.gestiondestock.dto.UtilisateurDto;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvalidOperationException;
import org.mambey.gestiondestock.services.FlickrService;
import org.mambey.gestiondestock.services.UtilisateurService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.flickr4java.flickr.FlickrException;

import lombok.RequiredArgsConstructor;

@Service("utilisateurStrategy")
@RequiredArgsConstructor
public class SaveUtilisateurPhoto implements Strategy{

    private final FlickrService flickrService;
    private final UtilisateurService utilisateurService;

    @Override
    public UtilisateurDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        
        UtilisateurDto utilisateur = utilisateurService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);

        if(StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        utilisateur.setPhoto(urlPhoto);
        
        return utilisateurService.save(utilisateur);
    }
    
}
