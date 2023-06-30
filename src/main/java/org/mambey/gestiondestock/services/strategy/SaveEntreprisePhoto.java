package org.mambey.gestiondestock.services.strategy;

import java.io.InputStream;

import org.mambey.gestiondestock.dto.EntrepriseDto;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvalidOperationException;
import org.mambey.gestiondestock.services.EntrepriseService;
import org.mambey.gestiondestock.services.FlickrService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.flickr4java.flickr.FlickrException;

import lombok.RequiredArgsConstructor;

@Service("entrepriseStrategy")
@RequiredArgsConstructor
public class SaveEntreprisePhoto implements Strategy{

    private final FlickrService flickrService;
    private final EntrepriseService entrepriseService;

    @Override
    public EntrepriseDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        
        EntrepriseDto entreprise = entrepriseService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);

        if(StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        entreprise.setPhoto(urlPhoto);
        
        return entrepriseService.save(entreprise);
    }
    
}
