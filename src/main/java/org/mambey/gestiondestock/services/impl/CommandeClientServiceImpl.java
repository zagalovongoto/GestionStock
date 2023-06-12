package org.mambey.gestiondestock.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mambey.gestiondestock.dto.CommandeClientDto;
import org.mambey.gestiondestock.dto.LigneCommandeClientDto;
import org.mambey.gestiondestock.exception.EntityNotFoundException;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvaliddEntityException;
import org.mambey.gestiondestock.model.Article;
import org.mambey.gestiondestock.model.Client;
import org.mambey.gestiondestock.model.CommandeClient;
import org.mambey.gestiondestock.model.LigneCommandeClient;
import org.mambey.gestiondestock.repository.ArticleRepository;
import org.mambey.gestiondestock.repository.ClientRepository;
import org.mambey.gestiondestock.repository.CommandeClientRepository;
import org.mambey.gestiondestock.repository.LigneCommandeClientRepository;
import org.mambey.gestiondestock.services.CommandeClientService;
import org.mambey.gestiondestock.services.ObjectsValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommandeClientServiceImpl implements CommandeClientService{

    private final CommandeClientRepository commandeClientRepository;
    private final ClientRepository clientRepository;
    private final ArticleRepository articleRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository;

    private final ObjectsValidator<CommandeClientDto> commandeClientValidator;


    @Override
    public CommandeClientDto save(CommandeClientDto dto) {
        
        var violations = commandeClientValidator.validate(dto);

        if(!violations.isEmpty()){
            log.error("La commande client n'est pas valide {}", dto);
            throw new InvaliddEntityException("Données invalides", ErrorCodes.COMMANDE_CLIENT_NOT_VALID, violations);
        }

        //On vérifie que le client existe bien dans la BDD
        Optional<Client> client = clientRepository.findById(dto.getClient().getId());
        if(client.isEmpty()){
            log.warn("Client with ID {} was not find in the DB", dto.getClient().getId());
            throw new EntityNotFoundException("Aucun client aavec l'ID " + dto.getClient().getId() + " n'a été trouvé dans la BDD");
        }

        // On vérifie que les différents articles de la command existent bien dans la BDD
        List<String> articleErrors = new ArrayList<>();
        if(dto.getLigneCommandeClients() != null){
            dto.getLigneCommandeClients().forEach(ligneCmdClt -> {
                if(ligneCmdClt.getArticle() != null){
                    Optional<Article> article = articleRepository.findById(ligneCmdClt.getArticle().getId());
                    if(article.isEmpty()){
                        articleErrors.add("L'article avec l'ID "+ ligneCmdClt.getArticle().getId() + " n'existe pas dans la BDD");
                    }
                }else{
                    articleErrors.add("Impossible d'enregistrer une commande avec un article NULL");
                }
            });
        }

        if(!articleErrors.isEmpty()){
            log.warn("Article n'existepas dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND);
            throw new InvaliddEntityException("Article n'existe pas dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }

        CommandeClient savedCmdClt = commandeClientRepository.save(CommandeClientDto.toEntity(dto));

        if(dto.getLigneCommandeClients() != null){
            dto.getLigneCommandeClients().forEach(ligneCmdCltDto -> {
                LigneCommandeClient ligneCmdClt = LigneCommandeClientDto.toEntity(ligneCmdCltDto);
                ligneCmdClt.setCommandeClient(savedCmdClt);
                ligneCommandeClientRepository.save(ligneCmdClt);
            });
        }

        return CommandeClientDto.fromEntity(savedCmdClt);
        
    }

    @Override
    public CommandeClientDto findById(Integer id) {
        if(id == null){
            log.error("CommandeClient ID is null");
            return null;
        }

        Optional<CommandeClient> commadeClient = commandeClientRepository.findById(id);

        return commadeClient.map(CommandeClientDto::fromEntity)
                      .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande client avec l'ID " + id + " n'a été trouvé dans la BDD", 
                        ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public CommandeClientDto findByCode(String code) {
        
        if(!StringUtils.hasLength(code)){
            log.error("Commande client CODE is null");
            return null;
        }

        Optional<CommandeClient> cmdClt = commandeClientRepository.findCommandClientByCode(code);

        return cmdClt.map(CommandeClientDto::fromEntity)
                      .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande client avec le Code " + code + " n'a été trouvé dans la BDD", 
                        ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public List<CommandeClientDto> findAll() {
        return commandeClientRepository.findAll().stream()
                    .map(CommandeClientDto::fromEntity)
                    .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {

        if(id == null){
            log.error("Command client ID is null");
        }

        commandeClientRepository.deleteById(id);
    }
    
}
