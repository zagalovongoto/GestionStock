package org.mambey.gestiondestock.controller;

import java.io.IOException;

import org.mambey.gestiondestock.controller.api.PhotoApi;
import org.mambey.gestiondestock.services.strategy.StrategyPhotoContext;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.flickr4java.flickr.FlickrException;

@RestController
public class PhotoController implements PhotoApi{

    private StrategyPhotoContext strategyPhotoContext;

    public PhotoController(StrategyPhotoContext strategyPhotoContext){
        this.strategyPhotoContext = strategyPhotoContext;
    }

    @Override
    public Object savePhoto(String context, Integer id, MultipartFile photo, String title) throws FlickrException, IOException {
        
        return strategyPhotoContext.savePhoto(context, id, photo.getInputStream(), title);
    }
    
}
