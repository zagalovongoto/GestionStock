package org.mambey.gestiondestock.services;

import java.util.List;

import org.mambey.gestiondestock.dto.RolesDto;

public interface RolesService {

    RolesDto save(RolesDto dto);

    RolesDto findById(Integer id);

    List<RolesDto> findAll();

    void delete(Integer id);
}
