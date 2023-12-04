package org.mambey.gestiondestock.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.mambey.gestiondestock.model.Category;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {
    
    private Integer id;

    @NotBlank(message = "Veuillez renseigner le code de la catégorie")
    private String code;

    @NotBlank(message = "Veuillez renseigner la designation")
    private String designation;

    @JsonIgnore
    private List<ArticleDto> articles;

    private Integer idEntreprise;


    // Category --> CategoryDto
    public static CategoryDto fromEntity(Category category){
        if(category == null){
            return null;
        }

        return CategoryDto.builder()
            .id(category.getId())
            .code(category.getCode())
            .designation(category.getDesignation())
            .idEntreprise(category.getIdEntreprise())
            .build();
    }

    // CategoryDto --> Category
    public static Category toEntity(CategoryDto categoryDto){

        if(categoryDto == null){
            return null;
        }

        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setCode(categoryDto.getCode());
        category.setDesignation(categoryDto.getDesignation());
        category.setIdEntreprise(categoryDto.getIdEntreprise());

        return category;
    }

}
