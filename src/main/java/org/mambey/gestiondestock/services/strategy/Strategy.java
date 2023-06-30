package org.mambey.gestiondestock.services.strategy;

import java.io.InputStream;

import com.flickr4java.flickr.FlickrException;

public interface Strategy {
    
    Object savePhoto(Integer id, InputStream photo, String titre) throws FlickrException;
}
