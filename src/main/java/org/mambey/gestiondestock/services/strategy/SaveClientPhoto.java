package org.mambey.gestiondestock.services.strategy;

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

    @Override
    public ClientDto savePhoto(Integer id, MultipartFile photo) {
        
        ClientDto client = clientService.findById(id);
        String urlPhoto = fileStorageService.storeFile(photo, client.getId(), client.getNom(), "client");

        client.setPhoto(urlPhoto);
        
        return clientService.save(client);
    }
    
}
