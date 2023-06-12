package org.mambey.gestiondestock.services;

import java.util.List;

import org.mambey.gestiondestock.dto.MvtStkDto;

public interface MvtStkService {
    
    MvtStkDto save(MvtStkDto dto);

    MvtStkDto findById(Integer id);

    List<MvtStkDto> findAll();

    void delete(Integer id);
}
