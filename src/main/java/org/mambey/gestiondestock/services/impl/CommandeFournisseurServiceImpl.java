package org.mambey.gestiondestock.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mambey.gestiondestock.dto.CommandeFournisseurDto;
import org.mambey.gestiondestock.dto.LigneCommandeFournisseurDto;
import org.mambey.gestiondestock.exception.EntityNotFoundException;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvaliddEntityException;
import org.mambey.gestiondestock.model.Article;
import org.mambey.gestiondestock.model.CommandeFournisseur;
import org.mambey.gestiondestock.model.Fournisseur;
import org.mambey.gestiondestock.model.LigneCommandeFournisseur;
import org.mambey.gestiondestock.repository.ArticleRepository;
import org.mambey.gestiondestock.repository.CommandeFournisseurRepository;
import org.mambey.gestiondestock.repository.FournisseurRepository;
import org.mambey.gestiondestock.repository.LigneCommandeFournisseurRepository;
import org.mambey.gestiondestock.services.CommandeFournisseurService;
import org.mambey.gestiondestock.services.ObjectsValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService{

    private final CommandeFournisseurRepository commandeFournisseurRepository;
    private final FournisseurRepository fournisseurRepository;
    private final ArticleRepository articleRepository;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;

    private final ObjectsValidator<CommandeFournisseurDto> commandeFournisseurValidator;


    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
        
        var violations = commandeFournisseurValidator.validate(dto);

        if(!violations.isEmpty()){
            log.error("La commande fournisseur n'est pas valide {}", dto);
            throw new InvaliddEntityException("Données invalides", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID, violations);
        }

        //On vérifie que le fournisseur existe bien dans la BDD
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(dto.getFournisseur().getId());
        if(fournisseur.isEmpty()){
            log.warn("Fournisseur with ID {} was not find in the DB", dto.getFournisseur().getId());
            throw new EntityNotFoundException("Aucun fournisseur aavec l'ID " + dto.getFournisseur().getId() + " n'a été trouvé dans la BDD");
        }

        // On vérifie que les différents articles de la commande existent bien dans la BDD
        List<String> articleErrors = new ArrayList<>();
        if(dto.getLigneCommandeFournisseurs() != null){
            dto.getLigneCommandeFournisseurs().forEach(ligneCmdFrn -> {
                if(ligneCmdFrn.getArticle() != null){
                    Optional<Article> article = articleRepository.findById(ligneCmdFrn.getArticle().getId());
                    if(article.isEmpty()){
                        articleErrors.add("L'article avec l'ID "+ ligneCmdFrn.getArticle().getId() + " n'existe pas dans la BDD");
                    }
                }else{
                    articleErrors.add("Impossible d'enregistrer une commande avec un article NULL");
                }
            });
        }

        if(!articleErrors.isEmpty()){
            log.warn("Article n'existe pas dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND);
            throw new InvaliddEntityException("Article n'existe pas dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }

        CommandeFournisseur savedCmdFrn = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(dto));

        if(dto.getLigneCommandeFournisseurs() != null){
            dto.getLigneCommandeFournisseurs().forEach(ligneCmdFrnDto -> {
                LigneCommandeFournisseur ligneCmdFrn = LigneCommandeFournisseurDto.toEntity(ligneCmdFrnDto);
                ligneCmdFrn.setCommandeFournisseur(savedCmdFrn);
                ligneCommandeFournisseurRepository.save(ligneCmdFrn);
            });
        }

        return CommandeFournisseurDto.fromEntity(savedCmdFrn);
        
    }

    @Override
    public CommandeFournisseurDto findById(Integer id) {
        if(id == null){
            log.error("Commande fournisseur ID is null");
            return null;
        }

        Optional<CommandeFournisseur> commandeFournisseur = commandeFournisseurRepository.findById(id);

        return commandeFournisseur.map(CommandeFournisseurDto::fromEntity)
                      .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande fournisseur avec l'ID " + id + " n'a été trouvée dans la BDD", 
                        ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND));
    }

    @Override
    public CommandeFournisseurDto findByCode(String code) {
        
        if(!StringUtils.hasLength(code)){
            log.error("Commande fournisseur CODE is null");
            return null;
        }

        Optional<CommandeFournisseur> cmdFrn = commandeFournisseurRepository.findCommandFournisseurByCode(code);

        return cmdFrn.map(CommandeFournisseurDto::fromEntity)
                      .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande fournisseur avec le Code " + code + " n'a été trouvé dans la BDD", 
                        ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND));
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurRepository.findAll().stream()
                    .map(CommandeFournisseurDto::fromEntity)
                    .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {

        if(id == null){
            log.error("Command fournisseur ID is null");
        }

        commandeFournisseurRepository.deleteById(id);
    }
    
}
