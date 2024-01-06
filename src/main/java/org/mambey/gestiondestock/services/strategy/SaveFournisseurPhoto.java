package org.mambey.gestiondestock.services.strategy;

import java.io.IOException;

import org.mambey.gestiondestock.dto.FournisseurDto;
import org.mambey.gestiondestock.services.FileStorageService;
import org.mambey.gestiondestock.services.FournisseurService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service("fournisseurStrategy")
@RequiredArgsConstructor
public class SaveFournisseurPhoto implements Strategy{

    private final FournisseurService fournisseurService;
    private final FileStorageService fileStorageService;
    private final String nomRepertoire = "fournisseur";//Nom du repertoire qui doit contenir les fichiers

    @Override
    public String savePhoto(Integer idFournisseur, MultipartFile file) {
        
        FournisseurDto fournisseur = fournisseurService.findById(idFournisseur);

        //Stratégie de nommage: id_nomFournisseur
        String nomFichier = idFournisseur + "_" + fournisseur.getNom() + this.getFileExtension(file);

        String response = fileStorageService.storeFile(file, nomFichier, nomRepertoire);

        fournisseur.setPhoto(nomFichier);
        
        fournisseurService.save(fournisseur);

        return response;
    }

    public byte[] getPhoto(Integer id) throws IOException{

        FournisseurDto fournisseurDto = fournisseurService.findById(id);

        return fileStorageService.getPhoto(nomRepertoire, fournisseurDto.getPhoto());
    }
    
}
