package org.mambey.gestiondestock.services;

import java.util.List;

import org.mambey.gestiondestock.dto.EntrepriseDto;

public interface EntrepriseService {
    
    EntrepriseDto save(EntrepriseDto dto);

    EntrepriseDto findById(Integer id);

    List<EntrepriseDto> findAll();

    void delete(Integer id);
}
