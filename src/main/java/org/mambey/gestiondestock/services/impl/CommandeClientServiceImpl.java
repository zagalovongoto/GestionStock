package org.mambey.gestiondestock.services.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mambey.gestiondestock.dto.ArticleDto;
import org.mambey.gestiondestock.dto.ClientDto;
import org.mambey.gestiondestock.dto.CommandeClientDto;
import org.mambey.gestiondestock.dto.LigneCommandeClientDto;
import org.mambey.gestiondestock.dto.MvtStkDto;
import org.mambey.gestiondestock.exception.EntityNotFoundException;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvalidOperationException;
import org.mambey.gestiondestock.exception.InvalidEntityException;
import org.mambey.gestiondestock.model.Article;
import org.mambey.gestiondestock.model.Client;
import org.mambey.gestiondestock.model.CommandeClient;
import org.mambey.gestiondestock.model.EtatCommande;
import org.mambey.gestiondestock.model.LigneCommandeClient;
import org.mambey.gestiondestock.model.SourceMvtStk;
import org.mambey.gestiondestock.model.TypeMvtStk;
import org.mambey.gestiondestock.repository.ArticleRepository;
import org.mambey.gestiondestock.repository.ClientRepository;
import org.mambey.gestiondestock.repository.CommandeClientRepository;
import org.mambey.gestiondestock.repository.LigneCommandeClientRepository;
import org.mambey.gestiondestock.services.CommandeClientService;
import org.mambey.gestiondestock.services.MvtStkService;
import org.mambey.gestiondestock.services.ObjectsValidator;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final LigneCommandeClientRepository ligneCommandeClientRepository;
    private final MvtStkService mvtStkService;
    private final ObjectsValidator<CommandeClientDto> commandeClientValidator;


    @Override
    public CommandeClientDto save(CommandeClientDto dto) {
        
        Integer idEntreprise = Integer.parseInt(MDC.get("idEntreprise"));
        dto.setIdEntreprise(idEntreprise);
        dto.setDate(Instant.now());

        var violations = commandeClientValidator.validate(dto);

        if(!violations.isEmpty()){
            log.error("La commande client n'est pas valide {}", dto);
            throw new InvalidEntityException("Données invalides", ErrorCodes.COMMANDE_CLIENT_NOT_VALID, violations);
        }

        if(dto.getId() != null && dto.isCommandeLivree()){
            throw new InvalidOperationException(
                "Impossible de modifier une commande déjà livrée",
                ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
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
            throw new InvalidEntityException("Article n'existe pas dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }

        CommandeClient savedCmdClt = commandeClientRepository.save(CommandeClientDto.toEntity(dto));

        if(dto.getLigneCommandeClients() != null){
            dto.getLigneCommandeClients().forEach(ligneCmdCltDto -> {
                LigneCommandeClient ligneCmdClt = LigneCommandeClientDto.toEntity(ligneCmdCltDto);
                ligneCmdClt.setCommandeClient(savedCmdClt);
                ligneCmdClt.setIdEntreprise(idEntreprise);
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

        /* if(id == null){
            log.error("CommandeClient ID is null");
            return null;
        }

        Optional<CommandeClient> commandeClient = commandeClientRepository.findById(id);
        List<LigneCommandeClientDto> ligneCommandeClients;
        if(commandeClient.isPresent()){
            ligneCommandeClients = ligneCommandeClientRepository.findAllByCommandeClientId(id).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());

        }else{
            throw new EntityNotFoundException(
                        "Aucune commande client avec l'ID " + id + " n'a été trouvé dans la BDD", 
                        ErrorCodes.COMMANDE_CLIENT_NOT_FOUND);
        }

        CommandeClientDto cmdClt = CommandeClientDto.fromEntity(commandeClient.get());
        cmdClt.setLigneCommandeClients(ligneCommandeClients);
        return cmdClt; */
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
            return;
        }

        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByCommandeClientId(id);
        if(!ligneCommandeClients.isEmpty()){
            log.warn("Suppression d'une commande rattachée à des lignes de commande");
            throw new InvalidOperationException(
                "Impossible de supprimer une commande client deja utilisé",
                ErrorCodes.COMMANDE_CLIENT_ALREADY_IN_USE
            );
        }
        commandeClientRepository.deleteById(id);
    }

    @Override
    public List<LigneCommandeClientDto> findAllLignesCommandesClientByCommandeClientId(Integer idCommande) {
        
        return ligneCommandeClientRepository.findAllByCommandeClientId(idCommande).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        
        checkIdCommande(idCommande);

        if(!StringUtils.hasLength(String.valueOf(etatCommande))){
            log.error("L'état de la commande client est nul");
            throw new InvalidOperationException(
                "Impossible de modifier l'état de la commande avec un état NULL",
                ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);

        commandeClient.setEtatCommade(etatCommande);

        CommandeClient savedCmdClt =  commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient));
        
        if(commandeClient.isCommandeLivree()){
            updateMvtStk(idCommande);
        }
        
        return CommandeClientDto.fromEntity(savedCmdClt);
    }

    @Override
    public CommandeClientDto updateQuantiteCommandee(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        
        checkIdCommande(idCommande);

        checkIdLigneCommande(idLigneCommande);

        if(quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0){
            log.error("La quantite de la ligne de commande est NULL");
            throw new InvalidOperationException(
                "Impossible de modifier la quantité de la ligne de commande avec une valeur NULL",
                ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);

        Optional<LigneCommandeClient> ligneCommandeClientOptional = ligneCommandeClientRepository.findById(idLigneCommande);

        if(ligneCommandeClientOptional.isEmpty()){
            throw new EntityNotFoundException(
                "Aucune ligne de commande n'a été trouvée avec l'ID "+idLigneCommande,
                ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }

        LigneCommandeClient ligneCommandeClient = ligneCommandeClientOptional.get();
        ligneCommandeClient.setQuantite(quantite);
        ligneCommandeClientRepository.save(ligneCommandeClient);

        return commandeClient;
    }

    @Override
    public CommandeClientDto updateClient(Integer idCommande, Integer idClient) {
        
        checkIdCommande(idCommande);

        if(idClient == null){
            log.error("L'ID client is NULL");
            throw new InvalidOperationException(
                "Impossible de modifier la commande avec ID client NULL",
                ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);

        Optional<Client> clientOptional = clientRepository.findById(idClient);

        if(clientOptional.isEmpty()){
            throw new EntityNotFoundException(
                "Aucun client avec l'ID "+idClient+" n'a été trouvé dans la BDD",
                ErrorCodes.CLIENT_NOT_FOUND
            );
        }

        commandeClient.setClient(ClientDto.fromEntity(clientOptional.get()));

        return CommandeClientDto.fromEntity(
            commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient))
        );
    }

    @Override
    public CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle){
            
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle);

        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        Optional<LigneCommandeClient> ligneCommandeClientOptional = checkLigneCommande(idLigneCommande);
        Optional<Article> articleOptional = articleRepository.findById(idArticle);

        if(articleOptional.isEmpty()){
            throw new EntityNotFoundException(
                "Aucun article avec l'ID "+idArticle+" n'a été trouvé dans la BDD",
                ErrorCodes.ARTICLE_NOT_FOUND
            );
        }

        LigneCommandeClient ligneCommandeClientToSave = ligneCommandeClientOptional.get();
        ligneCommandeClientToSave.setArticle(articleOptional.get());

        ligneCommandeClientRepository.save(ligneCommandeClientToSave);

        return commandeClient;
    }

    @Override
    public CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        CommandeClientDto commandeClient = checkEtatCommande(idCommande);
        //Juste pour vérifier que la ligne de commande client existe
        checkLigneCommande(idLigneCommande);
        ligneCommandeClientRepository.deleteById(idLigneCommande);

        return commandeClient;
    }

    private void checkIdCommande(Integer idCommande){

        if(idCommande == null){
            log.error("Command client ID is null");
            throw new InvalidOperationException(
                "Impossible de modifier l'état de la commande avec un ID NULL",
                ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }
    }

    private void checkIdLigneCommande(Integer idLigneCommande){

        if(idLigneCommande == null){
            log.error("L'ID de la ligne de commande est NULL");
            throw new InvalidOperationException(
                "Impossible de modifier la quantité de la ligne de commande avec ID NULL",
                ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }
    }

    private void checkIdArticle(Integer idArticle){

        if(idArticle == null){
            log.error("L'ID du nouvel article est NULL");
            throw new InvalidOperationException(
                "Impossible de modifier l'état de la commande avec un ID article null",
                ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }
    }

    private CommandeClientDto checkEtatCommande(Integer idCommande){

        CommandeClientDto commandeClient = findById(idCommande);
        if(commandeClient.isCommandeLivree()){
            throw new InvalidOperationException(
                "Impossible de modifier de modifier la commande lorsqu'elle est livrée",
                ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }

        return commandeClient;
    }

    private Optional<LigneCommandeClient> checkLigneCommande(Integer idLigneCommande){

        Optional<LigneCommandeClient> ligneCommandeClientOptional =  ligneCommandeClientRepository.findById(idLigneCommande);

        if(ligneCommandeClientOptional.isEmpty()){
            throw new EntityNotFoundException(
                "Aucune ligne de commande n'a été trouvée avec l'ID "+idLigneCommande,
                ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }

        return  ligneCommandeClientOptional;
    }

    private void updateMvtStk(Integer idCommande){

        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByCommandeClientId(idCommande);
        ligneCommandeClients.forEach(lig -> {
            MvtStkDto sortieStock = MvtStkDto.builder()
                .article(ArticleDto.fromEntity(lig.getArticle()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStk.SORTIE)
                .sourceMvt(SourceMvtStk.COMMANDE_CLIENT)
                .quantite(lig.getQuantite())
                .idEntreprise(lig.getIdEntreprise())
                .build();

            mvtStkService.sortieStock(sortieStock);
        });
        
    }

    @Override
    public Page<CommandeClientDto> findAllByPage(int page, int size) {

        Pageable mypage = PageRequest.of(page, size);

        return commandeClientRepository.findAll(mypage)
            .map(CommandeClientDto::fromEntity);
    }
}
