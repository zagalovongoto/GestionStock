package org.mambey.gestiondestock.services.strategy;

import java.io.IOException;

import org.mambey.gestiondestock.dto.UtilisateurDto;
import org.mambey.gestiondestock.services.FileStorageService;
import org.mambey.gestiondestock.services.UtilisateurService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service("utilisateurStrategy")
@RequiredArgsConstructor
public class SaveUtilisateurPhoto implements Strategy{

    private final FileStorageService fileStorageService;
    private final UtilisateurService utilisateurService;
    private final String nomRepertoire = "utilisateur";//Nom du repertoire qui doit contenir les fichiers

    @Override
    public String savePhoto(Integer idUtilisateur, MultipartFile file) {
        
        UtilisateurDto utilisateur = utilisateurService.findById(idUtilisateur);

        //Stratégie de nommage: id_nom_prenom
        String nomFichier = idUtilisateur + "_" + utilisateur.getNom() + "_" + utilisateur.getPrenom() + this.getFileExtension(file);

        String response = fileStorageService.storeFile(file, nomFichier, nomRepertoire);

        utilisateur.setPhoto(nomFichier);
        
        utilisateurService.save(utilisateur);

        return response;
    }

    public byte[] getPhoto(Integer id) throws IOException{

        UtilisateurDto utilisateur = utilisateurService.findById(id);

        return fileStorageService.getPhoto(nomRepertoire, utilisateur.getPhoto());
    }
    
}
