package org.mambey.gestiondestock.controller;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mambey.gestiondestock.dto.CategoryDto;
import org.mambey.gestiondestock.services.CategoryService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest slice test ne charge les beans de spring security du coup il faut désactiver la sécurité
@WebMvcTest(controllers = CategoryController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFindAll() throws Exception{
        CategoryDto cat1 = CategoryDto.builder()
            .code("CAT001")
            .designation("SAVONS")
            .idEntreprise(1)
            .build();

        CategoryDto cat2 = CategoryDto.builder()
            .code("CAT002")
            .designation("COSMETIQUES")
            .idEntreprise(1)
            .build();

        List<CategoryDto> liste = Arrays.asList(cat1, cat2);

        Mockito.when(categoryService.findAll()).thenReturn(liste);

        mockMvc.perform(get("/gestiondestock/v1/categories/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].code", Matchers.is("CAT001")));

    }
}
