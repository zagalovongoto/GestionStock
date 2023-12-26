package org.mambey.gestiondestock.security.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

//Cette classe n'est plus utilisée. Nous avons créé plutôt une implémentation de l'interface UserDetaailsService
public class ExtendedUser extends User{
    
    @Getter
    @Setter
    private Integer idEntreprise;

    public ExtendedUser(String username, String password, Collection<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
    }

    public ExtendedUser(String username, String password, Integer idEntreprise, Collection<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
        this.idEntreprise = idEntreprise;
    }
}
