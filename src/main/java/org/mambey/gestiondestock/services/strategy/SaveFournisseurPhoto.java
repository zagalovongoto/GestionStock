package org.mambey.gestiondestock.services.strategy;

import org.mambey.gestiondestock.dto.FournisseurDto;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvalidOperationException;
import org.mambey.gestiondestock.services.FileStorageService;
import org.mambey.gestiondestock.services.FournisseurService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.flickr4java.flickr.FlickrException;

import lombok.RequiredArgsConstructor;

@Service("fournisseurStrategy")
@RequiredArgsConstructor
public class SaveFournisseurPhoto implements Strategy{

    private final FournisseurService fournisseurService;
    private final FileStorageService fileStorageService;

    @Override
    public FournisseurDto savePhoto(Integer id, MultipartFile photo) throws FlickrException {
        
        FournisseurDto fournisseur = fournisseurService.findById(id);
        String urlPhoto = fileStorageService.storeFile(photo, fournisseur.getId(), fournisseur.getNom(), "fournisseur");

        if(StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        fournisseur.setPhoto(urlPhoto);
        
        return fournisseurService.save(fournisseur);
    }
    
}
