package org.mambey.gestiondestock.integrationTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Test;
import org.mambey.gestiondestock.dto.CategoryDto;
import org.mambey.gestiondestock.services.CategoryService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    CategoryService categoryService;

    @BeforeEach
    public void setIdEntreprise(){
        MDC.put("idEntreprise", "1");
    }

    @Test
    public void shoulSaveCategoruWithSuccess() {

        CategoryDto expectedCategory = CategoryDto.builder()
            
            .code("Cat test")
            .designation("Designation test")
            .idEntreprise(1)
            .build();

        CategoryDto savedCategory = categoryService.save(expectedCategory);

        assertNotNull(savedCategory);
        assertNotNull(savedCategory.getId());
        Assertions.assertEquals(expectedCategory.getCode(), savedCategory.getCode());
        Assertions.assertEquals(expectedCategory.getDesignation(), savedCategory.getDesignation());
        Assertions.assertEquals(expectedCategory.getIdEntreprise(), savedCategory.getIdEntreprise());
        
    }
}







