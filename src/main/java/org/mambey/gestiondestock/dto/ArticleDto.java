package org.mambey.gestiondestock.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.mambey.gestiondestock.model.Article;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ArticleDto {
     
    private Integer id;

    @NotBlank(message = "Veuillez renseigner la ville")
    private String codeArticle;

    @NotBlank(message = "Veuillez renseigner la désignation")
    private String designation;

    @NotBlank(message = "Veuillez renseigner le prix unitaire")
    private BigDecimal prixUnitaireHt;

    @NotBlank(message = "Veuillez renseigner le taux TVA")
    private BigDecimal tauxTva;

    @NotBlank(message = "Veuillez renseigner le prix unitaire")
    private BigDecimal prixUnitaireTtc;

    private String photo;

    @NotNull(message = "Veuillez renseigner la catégorie")
    private CategoryDto category;

    public static ArticleDto fromEntity(Article article){

        if(article == null){
            return null;
        }

        return ArticleDto.builder()
            .id(article.getId())
            .codeArticle(article.getCodeArticle())
            .designation(article.getDesignation())
            .prixUnitaireHt(article.getPrixUnitaireHt())
            .tauxTva(article.getTauxTva())
            .prixUnitaireTtc(article.getPrixUnitaireTtc())
            .photo(article.getPhoto())
            .category(CategoryDto.fromEntity(article.getCategory()))
            .build();
    }

    public static Article toEntity(ArticleDto articleDto){

        if(articleDto == null){
            return null;
        }

        Article article  = new Article();
        article.setId(articleDto.getId());
        article.setCodeArticle(articleDto.getCodeArticle());
        article.setDesignation(articleDto.getDesignation());
        article.setPrixUnitaireHt(articleDto.getPrixUnitaireHt());
        article.setTauxTva(articleDto.getTauxTva());
        article.setPrixUnitaireTtc(articleDto.getPrixUnitaireTtc());
        article.setPhoto(articleDto.getPhoto());
        article.setCategory(CategoryDto.toEntity(articleDto.getCategory()));

        return article;
    }
}
