package org.mambey.gestiondestock.services.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mambey.gestiondestock.dto.CategoryDto;
import org.mambey.gestiondestock.model.Category;
import org.mambey.gestiondestock.repository.ArticleRepository;
import org.mambey.gestiondestock.repository.CategoryRepository;
import org.mambey.gestiondestock.services.CategoryService;
import org.mambey.gestiondestock.services.ObjectsValidator;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock CategoryRepository categoryRepository;
    @Mock ArticleRepository articleRepository;
    CategoryService categoryService;

    @BeforeEach
    public void setIdEntreprise(){
        MDC.put("idEntreprise", "1");
        ObjectsValidator<CategoryDto> categoryValidator = new ObjectsValidator<CategoryDto>();
        CategoryService categoryService = new CategoryServiceImpl(categoryRepository, articleRepository, categoryValidator);
        this.categoryService = categoryService;
    }

    @Test
    public void shoulSaveCategoruWithSuccess() {

        //Given
        CategoryDto expectedCategory = CategoryDto.builder()
            
            .code("Cat test")
            .designation("Designation test")
            .idEntreprise(1)
            .build();

        Category category = CategoryDto.toEntity(expectedCategory);
        Mockito.when(categoryRepository.save(category)).thenReturn(category);

        //When
        CategoryDto savedCategory = categoryService.save(expectedCategory);

        //Then
        assertNotNull(savedCategory);
        Assertions.assertEquals(expectedCategory.getCode(), savedCategory.getCode());
        Assertions.assertEquals(expectedCategory.getDesignation(), savedCategory.getDesignation());
        Assertions.assertEquals(expectedCategory.getIdEntreprise(), savedCategory.getIdEntreprise());
        
    }
}