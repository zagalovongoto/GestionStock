package org.mambey.gestiondestock.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.flickr4java.flickr.FlickrException;

import static org.mambey.gestiondestock.utils.Constants.APP_ROOT;

import java.io.IOException;

@RequestMapping(value = APP_ROOT)
public interface PhotoApi {
    
    @PostMapping("/photos/{id}/{title}/{context}")
    Object savePhoto(String context, Integer id, @RequestPart("file") MultipartFile photo, String title) throws FlickrException, IOException;
}
