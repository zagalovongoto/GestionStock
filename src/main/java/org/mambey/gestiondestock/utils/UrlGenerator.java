package org.mambey.gestiondestock.utils;

import static org.mambey.gestiondestock.utils.Constants.APP_ROOT;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class UrlGenerator {

    public static String generate(String context, int id){
        
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(APP_ROOT)
                .path("/photos")
                .path("/"+context)
                .path("/"+String.valueOf(id))
                .toUriString();

    }
    
}
