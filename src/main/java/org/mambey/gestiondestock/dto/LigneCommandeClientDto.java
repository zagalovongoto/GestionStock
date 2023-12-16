package org.mambey.gestiondestock.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.mambey.gestiondestock.model.LigneCommandeClient;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LigneCommandeClientDto {
   
    private Integer id;

    @NotNull
    private ArticleDto article;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private CommandeClientDto commandeClient;

    @NotNull
    private BigDecimal quantite;

    @NotNull
    private BigDecimal prixUnitaire;

    private Integer idEntreprise;

    public static LigneCommandeClientDto fromEntity(LigneCommandeClient ligneCommandeClient){

        if(ligneCommandeClient == null){
            return null;
        }

        return LigneCommandeClientDto.builder()
            .id(ligneCommandeClient.getId())
            .article(ArticleDto.fromEntity(ligneCommandeClient.getArticle()))
            .commandeClient(CommandeClientDto.fromEntity(ligneCommandeClient.getCommandeClient()))
            .quantite(ligneCommandeClient.getQuantite())
            .prixUnitaire(ligneCommandeClient.getPrixUnitaire())
            .idEntreprise(ligneCommandeClient.getIdEntreprise())
            .build();
    }

    public static LigneCommandeClient toEntity(LigneCommandeClientDto ligneCommandeClientDto){

        if(ligneCommandeClientDto == null){
            return null;
        }

        LigneCommandeClient ligneCommandeClient = new LigneCommandeClient();
        ligneCommandeClient.setId(ligneCommandeClientDto.getId());
        ligneCommandeClient.setArticle(ArticleDto.toEntity(ligneCommandeClientDto.getArticle()));
        ligneCommandeClient.setCommandeClient(CommandeClientDto.toEntity(ligneCommandeClientDto.getCommandeClient()));
        ligneCommandeClient.setQuantite(ligneCommandeClientDto.getQuantite());
        ligneCommandeClient.setPrixUnitaire(ligneCommandeClientDto.getPrixUnitaire());
        ligneCommandeClient.setIdEntreprise(ligneCommandeClientDto.getIdEntreprise());

        return ligneCommandeClient;
    }
}
