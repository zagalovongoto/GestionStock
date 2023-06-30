package org.mambey.gestiondestock.services.strategy;

import java.io.InputStream;

import org.mambey.gestiondestock.dto.FournisseurDto;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvalidOperationException;
import org.mambey.gestiondestock.services.FlickrService;
import org.mambey.gestiondestock.services.FournisseurService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.flickr4java.flickr.FlickrException;

import lombok.RequiredArgsConstructor;

@Service("fournisseurStrategy")
@RequiredArgsConstructor
public class SaveFournisseurPhoto implements Strategy{

    private final FlickrService flickrService;
    private final FournisseurService fournisseurService;

    @Override
    public FournisseurDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        
        FournisseurDto fournisseur = fournisseurService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);

        if(StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        fournisseur.setPhoto(urlPhoto);
        
        return fournisseurService.save(fournisseur);
    }
    
}
