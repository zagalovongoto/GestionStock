package org.mambey.gestiondestock.repository;

import java.util.Optional;

import org.mambey.gestiondestock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer>{
    
    Optional<Utilisateur> findUtilisateurByEmail(String email);

    Boolean existsByEmail(String email);
}
