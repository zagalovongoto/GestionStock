package org.mambey.gestiondestock.controller;

import java.io.IOException;

import org.mambey.gestiondestock.controller.api.PhotoApi;
import org.mambey.gestiondestock.security.dto.MessageResponse;
import org.mambey.gestiondestock.services.FileStorageService;
import org.mambey.gestiondestock.services.strategy.StrategyPhotoContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.flickr4java.flickr.FlickrException;

@RestController
public class PhotoController implements PhotoApi{

    private StrategyPhotoContext strategyPhotoContext;

    public PhotoController(StrategyPhotoContext strategyPhotoContext, FileStorageService fileStorageService){

        this.strategyPhotoContext = strategyPhotoContext;
    }

    @Override
    public ResponseEntity<?> savePhoto(String context, Integer id, MultipartFile photo, String title) throws FlickrException, IOException {
        
        String response = strategyPhotoContext.savePhoto(context, id, photo);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(new MessageResponse(response)); 
    }

    @Override
	public byte[] getPhoto(String context, int id) throws Exception, IOException{
		
        return strategyPhotoContext.getPhoto(context, id);
	}
    
}
