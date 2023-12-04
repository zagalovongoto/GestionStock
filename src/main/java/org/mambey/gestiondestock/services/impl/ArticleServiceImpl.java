package org.mambey.gestiondestock.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mambey.gestiondestock.dto.ArticleDto;
import org.mambey.gestiondestock.dto.LigneCommandeClientDto;
import org.mambey.gestiondestock.dto.LigneCommandeFournisseurDto;
import org.mambey.gestiondestock.dto.LigneVenteDto;
import org.mambey.gestiondestock.exception.EntityAlreadyExistsException;
import org.mambey.gestiondestock.exception.EntityNotFoundException;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvalidOperationException;
import org.mambey.gestiondestock.exception.InvalidEntityException;
import org.mambey.gestiondestock.model.Article;
import org.mambey.gestiondestock.model.LigneCommandeClient;
import org.mambey.gestiondestock.model.LigneCommandeFournisseur;
import org.mambey.gestiondestock.model.LigneVente;
import org.mambey.gestiondestock.repository.ArticleRepository;
import org.mambey.gestiondestock.repository.LigneCommandeClientRepository;
import org.mambey.gestiondestock.repository.LigneCommandeFournisseurRepository;
import org.mambey.gestiondestock.repository.LigneVenteRepository;
import org.mambey.gestiondestock.services.ArticleService;
import org.mambey.gestiondestock.services.ObjectsValidator;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;
    private final LigneVenteRepository ligneVenteRepository;
    private final LigneCommandeClientRepository ligneCommandeClientRepository;
    private final LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;

    private final ObjectsValidator<ArticleDto> articleValidator;

    @Override
    public ArticleDto save(ArticleDto dto) {

        Integer idEntreprise = Integer.parseInt(MDC.get("idEntreprise"));
        dto.setIdEntreprise(idEntreprise);

        var violations = articleValidator.validate(dto);

        if(!violations.isEmpty()){
            log.error("L'article n'est pas valide {}", dto);
            throw new InvalidEntityException("Données invalides", ErrorCodes.ARTICLE_NOT_VALID, violations);
        }

        // On vérifie l'existence de l'article
        if (articleRepository.existsByCodeArticle(dto.getCodeArticle())) {
            log.error("L'article avec le code " + dto.getCodeArticle()+" existe déjà dans la base");
            throw new EntityAlreadyExistsException("Un article avec ce code existe déjà", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        return ArticleDto.fromEntity(
            articleRepository.save(ArticleDto.toEntity(dto))
        );
    }

    @Override
    public ArticleDto findById(Integer id) {
        
        if(id == null){
            log.error("Article ID is null");
            return null;
        }

        Optional<Article> article = articleRepository.findById(id);

        return article.map(ArticleDto::fromEntity)
                      .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun article avec l'ID " + id + " n'a été trouvé dans la BDD", 
                        ErrorCodes.ARTICLE_NOT_FOUND));

    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {

        if(!StringUtils.hasLength(codeArticle)){
            log.error("Article CODE is null");
            return null;
        }

        Optional<Article> article = articleRepository.findByCodeArticle(codeArticle);

        return article.map(ArticleDto::fromEntity)
                      .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun article avec le CODE " + codeArticle + " n'a été trouvé dans la BDD", 
                        ErrorCodes.ARTICLE_NOT_FOUND));

    }

    @Override
    public List<ArticleDto> findAll() {
        return articleRepository.findAll().stream()
                                .map(ArticleDto::fromEntity)
                                .collect(Collectors.toList());
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVentes(Integer idArticle) {

        return ligneVenteRepository.findAllByArticleId(idArticle).stream()
            .map(LigneVenteDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle) {
        
        return ligneCommandeClientRepository.findAllByArticleId(idArticle).stream()
            .map(LigneCommandeClientDto::fromEntity)
            .collect(Collectors.toList()); 
    }

    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle) {
    
        return ligneCommandeFournisseurRepository.findAllByArticleId(idArticle).stream()
            .map(LigneCommandeFournisseurDto::fromEntity)
            .collect(Collectors.toList()); 
    }

    @Override
    public List<ArticleDto> findAllArticleByIdCategory(Integer idCategory) {
        return articleRepository.findAllByCategoryId(idCategory).stream()
            .map(ArticleDto::fromEntity)
            .collect(Collectors.toList()); 
    }

    @Override
    public void delete(Integer id) {

        if(id == null){
            log.error("Article ID is null");
        }

        //findById(id);

        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByArticleId(id);
        if(!ligneCommandeClients.isEmpty()){
            throw new InvalidOperationException(
                "Impossible de supprimer un article déjà utilisé dans des commandes",
                ErrorCodes.ARTICLE_ALREADY_IN_USE
            );
        }

        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByArticleId(id);
        if(!ligneCommandeFournisseurs.isEmpty()){
            throw new InvalidOperationException(
                "Impossible de supprimer un article déjà utilisé dans des commandes fournisseurs",
                ErrorCodes.ARTICLE_ALREADY_IN_USE
            );
        }

        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByArticleId(id);
        if(!ligneVentes.isEmpty()){
            throw new InvalidOperationException(
                "Impossible de supprimer un article déjà utilisé dans des ventes",
                ErrorCodes.ARTICLE_ALREADY_IN_USE
            );
        }

        articleRepository.deleteById(id);
    }
}
