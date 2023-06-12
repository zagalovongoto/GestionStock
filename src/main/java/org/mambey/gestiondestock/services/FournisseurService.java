package org.mambey.gestiondestock.services;

import java.util.List;

import org.mambey.gestiondestock.dto.FournisseurDto;

public interface FournisseurService {
    
    FournisseurDto save(FournisseurDto dto);

    FournisseurDto findById(Integer id);

    List<FournisseurDto> findAll();

    void delete(Integer id);
}
