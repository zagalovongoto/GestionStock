package org.mambey.gestiondestock.services.strategy;

import java.io.InputStream;

import org.mambey.gestiondestock.dto.ClientDto;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvalidOperationException;
import org.mambey.gestiondestock.services.ClientService;
import org.mambey.gestiondestock.services.FlickrService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.flickr4java.flickr.FlickrException;

import lombok.RequiredArgsConstructor;

@Service("clientStrategy")
@RequiredArgsConstructor
public class SaveClientPhoto implements Strategy{

    private final FlickrService flickrService;
    private final ClientService clientService;

    @Override
    public ClientDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        
        ClientDto client = clientService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);
        if(StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        client.setPhoto(urlPhoto);
        
        return clientService.save(client);
    }
    
}
