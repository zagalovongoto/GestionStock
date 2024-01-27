package org.mambey.gestiondestock.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.mambey.gestiondestock.model.Article;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDto {
     
    private Integer id;

    @NotBlank(message = "Veuillez renseigner le code Article")
    private String codeArticle;

    @NotBlank(message = "Veuillez renseigner la désignation")
    private String designation;

    @NotNull(message = "Veuillez renseigner le prix unitaire")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal prixUnitaireHt;

    @NotNull(message = "Veuillez renseigner le prix unitaire")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal tauxTva;

    @NotNull(message = "Veuillez renseigner le prix unitaire")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal prixUnitaireTtc;

    //@JsonIgnore
    private String photo;

    private Integer idEntreprise;

    @NotNull(message = "Veuillez renseigner la catégorie")
    private CategoryDto category;

    public static ArticleDto fromEntity(Article article){

        if(article == null){
            return null;
        }

        //String sptr = System.getProperty("file.separator");
        //String photoUrl = APP_ROOT+sptr+"photos"+sptr+"article"+sptr+String.valueOf(article.getId());

        return ArticleDto.builder()
            .id(article.getId())
            .codeArticle(article.getCodeArticle())
            .designation(article.getDesignation())
            .prixUnitaireHt(article.getPrixUnitaireHt())
            .tauxTva(article.getTauxTva())
            .prixUnitaireTtc(article.getPrixUnitaireTtc())
            .photo(article.getPhoto())
            .category(CategoryDto.fromEntity(article.getCategory()))
            .idEntreprise(article.getIdEntreprise())
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
        article.setIdEntreprise(articleDto.getIdEntreprise());

        return article;
    }
}
