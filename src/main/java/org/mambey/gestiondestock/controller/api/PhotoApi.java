package org.mambey.gestiondestock.controller.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.flickr4java.flickr.FlickrException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.mambey.gestiondestock.utils.Constants.APP_ROOT;

import java.io.IOException;

@RequestMapping(value = APP_ROOT)
@Tag(name="photoApi")
public interface PhotoApi {
    
    @PostMapping(
        value = "/photos/{id}/{context}",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "savePhoto")
    ResponseEntity<?> savePhoto(@PathVariable("context") String context, @PathVariable("id") Integer id, @RequestPart("file") MultipartFile photo) throws FlickrException, IOException;

    @GetMapping(value="/photos/{context}/{id}", produces=MediaType.IMAGE_JPEG_VALUE)
	byte[] getPhoto(@PathVariable String context, @PathVariable int id) throws Exception, IOException;

}
