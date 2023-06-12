package org.mambey.gestiondestock.dto;

import java.math.BigDecimal;

import org.mambey.gestiondestock.model.LigneVente;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LigneVenteDto {
    
    private Integer id;

    private VentesDto vente;

    private ArticleDto article;

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;

    private Integer idEntreprise;

    public static LigneVenteDto fromEntity(LigneVente ligneVente){

        if(ligneVente == null ){
            return null;
        }

        return LigneVenteDto.builder()
            .id(ligneVente.getId())
            .vente(VentesDto.fromEntity(ligneVente.getVente()))
            .article(ArticleDto.fromEntity(ligneVente.getArticle()))
            .quantite(ligneVente.getQuantite())
            .prixUnitaire(ligneVente.getPrixUnitaire())
            .idEntreprise(ligneVente.getIdEntreprise())
            .build();
    }

    public static LigneVente toEntity(LigneVenteDto ligneVenteDto){

        if(ligneVenteDto == null ){
            return null;
        }

        LigneVente ligneVente = new LigneVente();
        ligneVente.setId(ligneVenteDto.getId());
        ligneVente.setVente(VentesDto.toEntity(ligneVenteDto.getVente()));
        ligneVente.setArticle(ArticleDto.toEntity(ligneVenteDto.getArticle()));
        ligneVente.setQuantite(ligneVenteDto.getQuantite());
        ligneVente.setPrixUnitaire(ligneVenteDto.getPrixUnitaire());
        ligneVente.setIdEntreprise(ligneVenteDto.getIdEntreprise());

        return ligneVente;
    }
}
