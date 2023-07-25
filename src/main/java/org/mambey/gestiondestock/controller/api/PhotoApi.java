package org.mambey.gestiondestock.controller.api;

import org.springframework.web.bind.annotation.PathVariable;
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
    Object savePhoto(@PathVariable("context") String context, @PathVariable("id") Integer id, @RequestPart("file") MultipartFile photo, @PathVariable("title") String title) throws FlickrException, IOException;
}
