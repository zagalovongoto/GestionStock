package org.mambey.gestiondestock.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mambey.gestiondestock.dto.ClientDto;
import org.mambey.gestiondestock.exception.EntityNotFoundException;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvalidOperationException;
import org.mambey.gestiondestock.exception.InvalidEntityException;
import org.mambey.gestiondestock.model.Client;
import org.mambey.gestiondestock.model.CommandeClient;
import org.mambey.gestiondestock.repository.ClientRepository;
import org.mambey.gestiondestock.repository.CommandeClientRepository;
import org.mambey.gestiondestock.services.ClientService;
import org.mambey.gestiondestock.services.ObjectsValidator;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;
    private final CommandeClientRepository commandeClientRepository;
    private final ObjectsValidator<ClientDto> clienValidator;

    @Override
    public ClientDto save(ClientDto dto) {

        Integer idEntreprise = Integer.parseInt(MDC.get("idEntreprise"));
        dto.setIdEntreprise(idEntreprise);

        var violations = clienValidator.validate(dto);

        if(!violations.isEmpty()){
            log.error("Le client n'est pas valide {}", dto);
            throw new InvalidEntityException("Données invalides", ErrorCodes.CLIENT_NOT_VALID, violations);
        }

        return ClientDto.fromEntity(
            clientRepository.save(ClientDto.toEntity(dto))
        );
    }

    @Override
    public ClientDto findById(Integer id) {
        
        if(id == null){
            log.error("Client ID is null");
            return null;
        }

        Optional<Client> client = clientRepository.findById(id);

        return client.map(ClientDto::fromEntity)
                      .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun client avec l'ID " + id + " n'a été trouvé dans la BDD", 
                        ErrorCodes.CLIENT_NOT_FOUND));

    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                                .map(ClientDto::fromEntity)
                                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Client ID is null");
        }

        findById(id);

        List<CommandeClient> commandeClients = commandeClientRepository.findAllByClientId(id);
        if(!commandeClients.isEmpty()){
            log.warn("Suppression d'un client lié à des commandes");
            throw new InvalidOperationException(
                "Impossible de supprimer un client qui a deja des commandes",
                ErrorCodes.CLIENT_ALREADY_IN_USE
            );
        }

        clientRepository.deleteById(id);
    }
    

}
