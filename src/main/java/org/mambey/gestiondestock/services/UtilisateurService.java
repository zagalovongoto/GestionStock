package org.mambey.gestiondestock.services;

import java.util.List;

import org.mambey.gestiondestock.dto.UtilisateurDto;

public interface UtilisateurService {
    
    UtilisateurDto save(UtilisateurDto dto);

    UtilisateurDto findById(Integer id);

    List<UtilisateurDto> findAll();

    void delete(Integer id);
}
