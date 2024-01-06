package org.mambey.gestiondestock.services.strategy;

import java.io.IOException;

import org.mambey.gestiondestock.dto.ClientDto;
import org.mambey.gestiondestock.services.ClientService;
import org.mambey.gestiondestock.services.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service("clientStrategy")
@RequiredArgsConstructor
public class SaveClientPhoto implements Strategy{

    private final ClientService clientService;
    private final FileStorageService fileStorageService;
    private final String nomRepertoire = "client";//Nom du repertoire qui doit contenir les fichiers

    @Override
    public String savePhoto(Integer idClient, MultipartFile file) {
        
        ClientDto client = clientService.findById(idClient);

        //Stratégie de nommage: id_nom_prenom
        String nomFichier = idClient + "_" + client.getNom()+"_" + client.getPrenom() + this.getFileExtension(file);;

        String response = fileStorageService.storeFile(file, nomFichier, nomRepertoire);

        client.setPhoto(nomFichier);
        
        clientService.save(client);

        return response;
    }

    public byte[] getPhoto(Integer id) throws IOException{

        ClientDto client = clientService.findById(id);

        return fileStorageService.getPhoto(nomRepertoire, client.getPhoto());
    }
    
}
