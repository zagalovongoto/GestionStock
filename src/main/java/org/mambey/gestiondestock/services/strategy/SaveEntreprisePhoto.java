package org.mambey.gestiondestock.services.strategy;

import org.mambey.gestiondestock.dto.EntrepriseDto;
import org.mambey.gestiondestock.services.EntrepriseService;
import org.mambey.gestiondestock.services.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.flickr4java.flickr.FlickrException;

import lombok.RequiredArgsConstructor;

@Service("entrepriseStrategy")
@RequiredArgsConstructor
public class SaveEntreprisePhoto implements Strategy{

    private final EntrepriseService entrepriseService;
    private final FileStorageService fileStorageService;

    @Override
    public EntrepriseDto savePhoto(Integer id, MultipartFile photo) throws FlickrException {
        
        EntrepriseDto entreprise = entrepriseService.findById(id);
        String urlPhoto = fileStorageService.storeFile(photo, entreprise.getId(), entreprise.getNom(), "entreprise");

        entreprise.setPhoto(urlPhoto);
        
        return entrepriseService.save(entreprise);
    }
    
}
