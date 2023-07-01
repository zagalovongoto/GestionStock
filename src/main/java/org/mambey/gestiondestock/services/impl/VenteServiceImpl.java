package org.mambey.gestiondestock.services.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.mambey.gestiondestock.dto.ArticleDto;
import org.mambey.gestiondestock.dto.LigneVenteDto;
import org.mambey.gestiondestock.dto.MvtStkDto;
import org.mambey.gestiondestock.dto.VentesDto;
import org.mambey.gestiondestock.exception.EntityNotFoundException;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvalidOperationException;
import org.mambey.gestiondestock.exception.InvaliddEntityException;
import org.mambey.gestiondestock.model.Article;
import org.mambey.gestiondestock.model.LigneVente;
import org.mambey.gestiondestock.model.SourceMvtStk;
import org.mambey.gestiondestock.model.TypeMvtStk;
import org.mambey.gestiondestock.model.Ventes;
import org.mambey.gestiondestock.repository.ArticleRepository;
import org.mambey.gestiondestock.repository.LigneVenteRepository;
import org.mambey.gestiondestock.repository.VenteRepository;
import org.mambey.gestiondestock.services.MvtStkService;
import org.mambey.gestiondestock.services.ObjectsValidator;
import org.mambey.gestiondestock.services.VenteService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class VenteServiceImpl implements VenteService{
    
    private final VenteRepository venteRepository;
    private final ArticleRepository articleRepository;
    private final LigneVenteRepository ligneVenteRepository;
    private MvtStkService mvtStkService;
    private final ObjectsValidator<VentesDto> ventesValidator;

    @Override
    public VentesDto save(VentesDto dto) {
        
        var violations = ventesValidator.validate(dto);
        if(!violations.isEmpty()){
            log.error("La vente n'est pas valide {}", dto);
            throw new InvaliddEntityException("Données invalides", ErrorCodes.VENTE_NOT_VALID, violations);
        }

        List<String> articleErrors = new ArrayList<>();
        dto.getLigneVentes().forEach(ligneVenteDto -> {
            Optional<Article> article = articleRepository.findById(ligneVenteDto.getArticle().getId());
            if(article.isEmpty()){
                articleErrors.add("Aucun article avec l'ID "+ligneVenteDto.getArticle().getId()+" n'a été trouvé dans la base");
            }
        });

        if(!articleErrors.isEmpty()){
            log.error("One or more articles were not found in the DB, {}", articleErrors);
            throw new InvaliddEntityException("Un ou plusieurs articles 'ont pas été trouvés dans la BD", ErrorCodes.VENTE_NOT_VALID, articleErrors);
        }

        Ventes savedVentes = venteRepository.save(VentesDto.toEntity(dto));

        dto.getLigneVentes().forEach(ligneVenteDto -> {
            LigneVente lignVente = LigneVenteDto.toEntity(ligneVenteDto);
            lignVente.setVente(savedVentes);
            ligneVenteRepository.save(lignVente);
            updateMvtStk(lignVente);
        });

        return VentesDto.fromEntity(savedVentes);
    }

    @Override
    public VentesDto findById(Integer id) {
        
        if(id == null){
            log.error("Vente ID is null");
            return null;
        }

        Optional<Ventes> vente = venteRepository.findById(id);

        return vente.map(VentesDto::fromEntity)
                      .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune vente avec l'ID " + id + " n'a été trouvée dans la BDD", 
                        ErrorCodes.VENTE_NOT_FOUND));
    }

    @Override
    public VentesDto findByCode(String code) {
        if(!StringUtils.hasLength(code)){
            log.error("Commande client CODE is null");
            return null;
        }

        Optional<Ventes> vente = venteRepository.findVentesByCode(code);

        return vente.map(VentesDto::fromEntity)
                      .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune vente avec le Code " + code + " n'a été trouvée dans la BDD", 
                        ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public List<VentesDto> findAll() {
        return venteRepository.findAll().stream()
                    .map(VentesDto::fromEntity)
                    .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Vente ID is null");
        }

        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByVenteId(id);
        if(!ligneVentes.isEmpty()){
            throw new InvalidOperationException(
                "Impossible de supprimer une vente ayant des lignes de vente",
                ErrorCodes.VENTE_ALREADY_IN_USE
            );
        }

        venteRepository.deleteById(id);
    }

    private void updateMvtStk(LigneVente lig){

        MvtStkDto sortieStock = MvtStkDto.builder()
            .article(ArticleDto.fromEntity(lig.getArticle()))
            .dateMvt(Instant.now())
            .typeMvt(TypeMvtStk.SORTIE)
            .sourceMvt(SourceMvtStk.VENTE)
            .quantite(lig.getQuantite())
            .idEntreprise(lig.getIdEntreprise())
            .build();

        mvtStkService.sortieStock(sortieStock);
        
    }
}
