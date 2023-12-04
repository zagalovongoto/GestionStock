package org.mambey.gestiondestock.repository;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mambey.gestiondestock.config.JpaAuditingConfiguration;
import org.mambey.gestiondestock.model.Article;
import org.mambey.gestiondestock.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(JpaAuditingConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    Article article;
    Category category;

    @BeforeEach
    void setup(){//Avant chaque test on insère une catégorie et un article

        Category category = new Category();
        category.setCode("CAT003");
        category.setDesignation("LEGUMINEUSE");
        category.setIdEntreprise(1);

        Category savedCat = categoryRepository.save(category);
        this.category = savedCat;

        Article article = new Article();
        article.setCodeArticle("DET01");
        article.setDesignation("Madar Liquide");
        article.setPrixUnitaireHt(BigDecimal.valueOf(1200));
        article.setTauxTva(BigDecimal.valueOf(10));
        article.setPrixUnitaireTtc(BigDecimal.valueOf(10));
        article.setIdEntreprise(1);
        article.setCategory(savedCat);
        
        Article savedArticle = articleRepository.save(article);
        this.article = savedArticle;

    }

    @AfterEach//On réinitialise les données après chaque test(méthode annotée par @Test)
    void tearDown(){
        article = null;
        articleRepository.deleteAll();
    }

    @Test//Les tests s'exécutent en isolation. Les données insérées 
    //dans une méthode @test ne peuvent pas être utilisées dans une autre méthode @Test
    void testSave(){
        //Given
        Article article = this.article;
        article.setCodeArticle("DET02");

        //When
        Article savedArticle = articleRepository.save(article);

        //Then
        assertThat(savedArticle).isNotNull();
        assertThat(savedArticle.getCreationDate()).isNotNull();//Vérifie le JPA auditing
        assertThat(savedArticle.getLastModifiedDate()).isNotNull();//Vérifie le JPA auditing
    }

    @Test
    void testExistsByCodeArticle() {

        boolean exist = articleRepository.existsByCodeArticle("DET01");
        assertThat(exist).isTrue();
    }

    @Test
    void testExistsByCodeArticleFAil() {

        boolean exist = articleRepository.existsByCodeArticle("DET02");
        assertThat(exist).isFalse();
    }

    @Test
    void testFindAllByCategoryId() {

        List<Article> listArticles = this.articleRepository.findAllByCategoryId(this.category.getId());
        assertThat(listArticles).size().isEqualTo(1);
    }

    @Test//Exemple de test d'une méthode qui retourne un Optional
    void testFindByCodeArticle() {

        Article article = articleRepository.findByCodeArticleAndDesignation("DET01", "Madar Liquide").get();
        assertThat(article).isNotNull();
    }

    @Test
    @Disabled
    void testFindByCodeArticleAndDesignation() {

    }

    @Test
    @Disabled
    void testFindByCodeArticleIgnoreCaseAndDesignationIgnoreCase() {

    }

    @Test
    @Disabled
    void testFindByCustumQuery() {

    }

    @Test
    @Disabled
    void testFindByCustumQuery2() {

    }

    @Test
    @Disabled
    void testFindByCustumQuery3() {

    }
}
