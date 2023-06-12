package org.mambey.gestiondestock.services.impl;

import java.util.Collections;

import org.mambey.gestiondestock.exception.EntityNotFoundException;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.model.Utilisateur;
import org.mambey.gestiondestock.repository.UtilisateurRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailsService implements UserDetailsService{

    private UtilisateurRepository utilisateurRepository;

    public ApplicationUserDetailsService(UtilisateurRepository repository){
        this.utilisateurRepository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByEmail(email).orElseThrow(
            () -> new EntityNotFoundException("utilisateur introuvable", ErrorCodes.UTILISATEUR_NOT_FOUND)
        );

        return new User(utilisateur.getEmail(), utilisateur.getMotDePasse(), Collections.emptyList());
    }
    
}
