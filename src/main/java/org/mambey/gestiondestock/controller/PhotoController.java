package org.mambey.gestiondestock.controller;

import java.io.IOException;

import org.mambey.gestiondestock.controller.api.PhotoApi;
import org.mambey.gestiondestock.security.dto.MessageResponse;
import org.mambey.gestiondestock.services.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import static org.mambey.gestiondestock.utils.Constants.APP_ROOT;

import com.flickr4java.flickr.FlickrException;

@RestController
public class PhotoController implements PhotoApi{

    //private StrategyPhotoContext strategyPhotoContext;
    private FileStorageService fileStorageService;

    public PhotoController(FileStorageService fileStorageService){
        //this.strategyPhotoContext = strategyPhotoContext;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public ResponseEntity<?> savePhoto(String context, Integer id, MultipartFile photo, String title) throws FlickrException, IOException {
        
        //return strategyPhotoContext.savePhoto(context, id, photo.getInputStream(), title);

        this.fileStorageService.storeFile(photo, id, title, context);

        String fullUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(APP_ROOT)
                .path("/photos/"+context+"/"+String.valueOf(id))  // Assuming your files are stored in the "uploads" directory
                .toUriString();
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new MessageResponse(fullUrl));
    }

    @Override
	public byte[] getPhoto(String context, int id) throws Exception, IOException{
		
        return fileStorageService.getPhoto(context, id);
	}
    
}
