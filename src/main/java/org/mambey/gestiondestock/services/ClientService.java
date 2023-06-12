package org.mambey.gestiondestock.services;

import java.util.List;

import org.mambey.gestiondestock.dto.ClientDto;

public interface ClientService {
    
    ClientDto save(ClientDto dto);

    ClientDto findById(Integer id);

    List<ClientDto> findAll();

    void delete(Integer id);
}
