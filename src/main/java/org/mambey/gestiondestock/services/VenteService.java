package org.mambey.gestiondestock.services;

import java.util.List;

import org.mambey.gestiondestock.dto.VentesDto;

public interface VenteService {
    
    VentesDto save(VentesDto dto);

    VentesDto findById(Integer id);

    VentesDto findByCode(String code);

    List<VentesDto> findAll();

    void delete(Integer id);
}
