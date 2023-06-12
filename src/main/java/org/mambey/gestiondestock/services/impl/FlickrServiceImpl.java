package org.mambey.gestiondestock.services.impl;

import java.io.InputStream;

import org.mambey.gestiondestock.services.FlickrService;
import org.springframework.beans.factory.annotation.Autowired;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.uploader.UploadMetaData;

public class FlickrServiceImpl implements FlickrService{

    @Autowired
    private Flickr flickr;

    @Override
    public String savePhoto(InputStream photo, String title) throws FlickrException {
        
        UploadMetaData uploadMetaData = new UploadMetaData();
        uploadMetaData.setTitle(title);
        String photoId = flickr.getUploader().upload(photo, uploadMetaData);

        return flickr.getPhotosInterface().getPhoto(photoId).getMedium640Url();
    }
    
}
