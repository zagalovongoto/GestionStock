package org.mambey.gestiondestock.dto;

import java.math.BigDecimal;
import java.time.Instant;

import javax.validation.constraints.NotNull;

import org.mambey.gestiondestock.model.MvtStk;
import org.mambey.gestiondestock.model.TypeMvtStk;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MvtStkDto {
    
    private Integer id;

    @NotNull(message = "Veuillez renseigner la date")
    private Instant dateMvt;
    
    @NotNull(message = "Veuillez reseigner la quantité")
    private BigDecimal quantite;

    @NotNull(message = "Veuillez renseigner l'article")
    private ArticleDto article;

    private TypeMvtStk typeMvt;

    private Integer idEntreprise;

    public static MvtStkDto fromEntity(MvtStk mvtStk){

        if(mvtStk == null){
            return null;
        }

        return MvtStkDto.builder()
            .id(mvtStk.getId())
            .dateMvt(mvtStk.getDateMvt())
            .quantite(mvtStk.getQuantite())
            .article(ArticleDto.fromEntity(mvtStk.getArticle()))
            .typeMvt(mvtStk.getTypeMvt())
            .idEntreprise(mvtStk.getIdEntreprise())
            .build();
    }

    public static MvtStk toEntity(MvtStkDto mvtStkDto){

        if(mvtStkDto == null){
            return null;
        }

        MvtStk mvtStk = new MvtStk();
        mvtStk.setId(mvtStkDto.getId());
        mvtStk.setDateMvt(mvtStkDto.getDateMvt());
        mvtStk.setQuantite(mvtStkDto.getQuantite());
        mvtStk.setArticle(ArticleDto.toEntity(mvtStkDto.getArticle()));
        mvtStk.setTypeMvt(mvtStkDto.getTypeMvt());
        mvtStk.setIdEntreprise(mvtStkDto.getIdEntreprise());

        return mvtStk;
    }
}
