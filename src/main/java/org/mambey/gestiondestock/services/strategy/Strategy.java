package org.mambey.gestiondestock.services.strategy;

import org.springframework.web.multipart.MultipartFile;

import com.flickr4java.flickr.FlickrException;

public interface Strategy {
    
    Object savePhoto(Integer id, MultipartFile photo) throws FlickrException;
}
