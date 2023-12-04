package org.mambey.gestiondestock.services.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import org.mambey.gestiondestock.dto.ArticleDto;
import org.mambey.gestiondestock.dto.CategoryDto;
import org.mambey.gestiondestock.exception.EntityAlreadyExistsException;
import org.mambey.gestiondestock.model.Article;
import org.mambey.gestiondestock.repository.ArticleRepository;
import org.mambey.gestiondestock.repository.LigneCommandeClientRepository;
import org.mambey.gestiondestock.repository.LigneCommandeFournisseurRepository;
import org.mambey.gestiondestock.repository.LigneVenteRepository;
import org.mambey.gestiondestock.services.ArticleService;
import org.mambey.gestiondestock.services.ObjectsValidator;
import static org.mockito.BDDMockito.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.MDC;

@ExtendWith(MockitoExtension.class)//JUnit peut utiliser les annotations de Mockito comme @Mock et @InjectMock
@MockitoSettings(strictness = Strictness.LENIENT)//On désactive le mode strict de Mockito
public class ArticleServiceImplTest {

    @Mock ArticleRepository articleRepository;
    @Mock LigneVenteRepository  ligneVenteRepository;
    @Mock LigneCommandeClientRepository ligneCommandeClientRepository;
    @Mock LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;

    ArticleService articleService;

    ArticleDto articleDto;

    CategoryDto categoryDto;

    @BeforeEach
    void Setup(){

        ObjectsValidator<ArticleDto> articleValidator = new ObjectsValidator<ArticleDto>();
        ArticleService articleService = new ArticleServiceImpl(articleRepository, 
                                                                        ligneVenteRepository, 
                                                                        ligneCommandeClientRepository, 
                                                                        ligneCommandeFournisseurRepository, 
                                                                        articleValidator);
        this.articleService = articleService;

        CategoryDto categoryDto = CategoryDto.builder()
                                   .id(Integer.valueOf(1))
                                   .code("CAT003")
                                   .designation("CATEGORIE 3")
                                   .idEntreprise(1)
                                   .build();
        this.categoryDto = categoryDto;

        ArticleDto articleDto = ArticleDto.builder()
                                   .codeArticle("DET01")
                                   .designation("Madar Liquide")
                                   .prixUnitaireHt(BigDecimal.valueOf(1200))
                                   .tauxTva(BigDecimal.valueOf(10))
                                   .prixUnitaireTtc(BigDecimal.valueOf(1500))
                                   .category(categoryDto)
                                   .idEntreprise(1)
                                   .build();

        this.articleDto = articleDto;

        MDC.put("idEntreprise", "1");

    }

    @Test
    @DisplayName("JUnit test for save artcile method")
    void testSave() {

        //Given
        Article article = ArticleDto.toEntity(articleDto);
        Article articleWithId = ArticleDto.toEntity(articleDto);
        articleWithId.setId(1);
        when(articleRepository.save(article)).thenReturn(articleWithId);
        when(articleRepository.existsByCodeArticle(articleDto.getCodeArticle())).thenReturn(false);
        //BDDMockito.given(articleRepository.save(article)).willReturn(article);

        //When
        ArticleDto saved = articleService.save(articleDto);

        //Then
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo(1);
        //assertThat(saved).usingRecursiveComparison().isEqualTo(saved);

        //When using strict stubbing mode, you typically don't need to explicitly use Mockito.verify for stubbed methods because Mockito will automatically check for unused stubs and raise an exception if any are found.
        verify(this.articleRepository).save(article);
    }

    @Test
    @DisplayName("JUnit test for save article method which throws exception")
    void testSaveGivenExistingArticle() {

        //Given
        Article article = ArticleDto.toEntity(articleDto);
        when(articleRepository.save(article)).thenReturn(article);
        when(articleRepository.existsByCodeArticle(articleDto.getCodeArticle())).thenReturn(true);

        //When & Then
        assertThrows(EntityAlreadyExistsException.class, () -> {
            articleService.save(articleDto);
        });

        verify(articleRepository, never()).save(any(Article.class));
    }

    @Test
    void testFindAll() {

        //Given
        Article article1 = ArticleDto.toEntity(articleDto);
        ArticleDto articleDto = ArticleDto.builder()
                                          .codeArticle("BL002")
                                          .designation("SANTEX")
                                          .prixUnitaireHt(BigDecimal.valueOf(2000))
                                          .tauxTva(BigDecimal.valueOf(12.5))
                                          .prixUnitaireTtc(BigDecimal.valueOf(2500))
                                          .category(categoryDto)
                                          .idEntreprise(1)
                                          .build();
        Article article2 = ArticleDto.toEntity(articleDto);
        List<Article> maListe = List.of(article1, article2);
        
        given(articleRepository.findAll()).willReturn(maListe);
        
        //When
        List<ArticleDto> articleListe = articleService.findAll();

        //Then
        assertThat(articleListe).isNotNull();
        assertThat(articleListe.size()).isEqualTo(2);
    }

    @Test
    void testFindAllEmptyList() {

        //Given
        given(articleRepository.findAll()).willReturn(Collections.emptyList());
        
        //When
        List<Article> articleListe = articleRepository.findAll();

        //Then
        assertThat(articleListe.size()).isEqualTo(0);

    }

    @Test
    void testFindById() {
        //Given
        Article article = ArticleDto.toEntity(articleDto);
        article.setId(1);
        given(articleRepository.findById(1)).willReturn(java.util.Optional.of(article));

        //When
        ArticleDto dto = articleService.findById(1);

        //Then
        assertThat(dto).isNotNull();
    }
    
    @Test
    void testFindByCodeArticle() {
        //Given
        Article article = ArticleDto.toEntity(articleDto);
        article.setId(1);
        given(articleRepository.findByCodeArticle("BL002")).willReturn(java.util.Optional.of(article));

        //When
        ArticleDto dto = articleService.findByCodeArticle("BL002");

        //Then
        assertThat(dto).isNotNull();
    }

    @Test
    void testDelete() {

        willDoNothing().given(articleRepository).deleteById(1);

        // when -  action or the behaviour that we are going test
        articleService.delete(1);

        // then - verify the output
        verify(articleRepository, times(1)).deleteById(1);
    }

    @Test
    @Disabled
    void testFindAllArticleByIdCategory() {

    } 

    @Test
    @Disabled
    void testFindHistoriqueCommandeClient() {

    }

    @Test
    @Disabled
    void testFindHistoriqueCommandeFournisseur() {

    }

    @Test
    @Disabled
    void testFindHistoriqueVentes() {

    }

}
