package org.mambey.gestiondestock.controller;

import java.util.List;

import org.mambey.gestiondestock.controller.api.ClientApi;
import org.mambey.gestiondestock.dto.ClientDto;
import org.mambey.gestiondestock.services.ClientService;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ClientController implements ClientApi{
    
    private final ClientService clientService;

    @Override
    public ClientDto save(ClientDto dto) {

        return clientService.save(dto);
    }

    @Override
    public ClientDto findById(Integer id) {
        return clientService.findById(id);
    }

    @Override
    public List<ClientDto> findAll() {
        return clientService.findAll();
    }

    @Override
    public void delete(Integer id) {
        clientService.delete(id);
    }

    
}
