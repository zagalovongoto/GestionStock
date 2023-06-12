package org.mambey.gestiondestock.services;

import java.util.List;

import org.mambey.gestiondestock.dto.CommandeFournisseurDto;

public interface CommandeFournisseurService {
    
    CommandeFournisseurDto save(CommandeFournisseurDto dto);

    CommandeFournisseurDto findById(Integer id);

    CommandeFournisseurDto findByCode(String code);

    List<CommandeFournisseurDto> findAll();

    void delete(Integer id);
}
