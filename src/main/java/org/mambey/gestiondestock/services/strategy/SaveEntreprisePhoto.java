package org.mambey.gestiondestock.services.strategy;

import java.io.IOException;

import org.mambey.gestiondestock.dto.EntrepriseDto;
import org.mambey.gestiondestock.services.EntrepriseService;
import org.mambey.gestiondestock.services.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service("entrepriseStrategy")
@RequiredArgsConstructor
public class SaveEntreprisePhoto implements Strategy{

    private final EntrepriseService entrepriseService;
    private final FileStorageService fileStorageService;
    private final String nomRepertoire = "entreprise";//Nom du repertoire qui doit contenir les fichiers

    @Override
    public String savePhoto(Integer idEntreprise, MultipartFile file) {
        
        EntrepriseDto entreprise = entrepriseService.findById(idEntreprise);

        //Stratégie de nommage: id_nomEntreprise
        String nomFichier = idEntreprise + "_" + entreprise.getNom() + this.getFileExtension(file);

        String response = fileStorageService.storeFile(file, nomFichier, nomRepertoire);

        entreprise.setPhoto(nomFichier);
        
        entrepriseService.save(entreprise);

        return response;
    }

    public byte[] getPhoto(Integer id) throws IOException{

        EntrepriseDto entreprise = entrepriseService.findById(id);

        return fileStorageService.getPhoto(nomRepertoire, entreprise.getPhoto());

    }
    
}
