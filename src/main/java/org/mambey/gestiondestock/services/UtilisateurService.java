package org.mambey.gestiondestock.services;

import java.util.List;
import org.mambey.gestiondestock.dto.UtilisateurDto;
import org.mambey.gestiondestock.security.dto.ChangerMotDePasse;

public interface UtilisateurService {
    
    UtilisateurDto save(UtilisateurDto dto);

    UtilisateurDto findById(Integer id);

    List<UtilisateurDto> findAll();

    void delete(Integer id);

    UtilisateurDto findByEmail(String email);

    void updatePassword(ChangerMotDePasse dto);

}
