package org.mambey.gestiondestock.services.strategy;

import org.mambey.gestiondestock.dto.UtilisateurDto;
import org.mambey.gestiondestock.services.FileStorageService;
import org.mambey.gestiondestock.services.UtilisateurService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.flickr4java.flickr.FlickrException;

import lombok.RequiredArgsConstructor;

@Service("utilisateurStrategy")
@RequiredArgsConstructor
public class SaveUtilisateurPhoto implements Strategy{

    private final FileStorageService fileStorageService;
    private final UtilisateurService utilisateurService;

    @Override
    public UtilisateurDto savePhoto(Integer id, MultipartFile photo) throws FlickrException {
        
        UtilisateurDto utilisateur = utilisateurService.findById(id);

        String urlPhoto = fileStorageService.storeFile(photo, utilisateur.getId(), utilisateur.getNom(), "utilisateur");
        utilisateur.setPhoto(urlPhoto);
        
        return utilisateurService.save(utilisateur);
    }
    
}
