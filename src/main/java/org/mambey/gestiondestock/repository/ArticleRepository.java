package org.mambey.gestiondestock.repository;

import java.util.List;
import java.util.Optional;

import org.mambey.gestiondestock.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Integer>{
    
    //Spring comprend automatiquement
    Optional<Article> findByCodeArticle(String code);

    List<Article> findAllByCategoryId(Integer idCategory);

    //Spring comprend automatiquement
    Optional<Article> findByCodeArticleAndDesignation(String code, String designation);

    //Peut ignorer la casse
    Article findByCodeArticleIgnoreCaseAndDesignationIgnoreCase(String code, String designation);

    List<Article>  findByCodeArticleContainingIgnoreCaseOrDesignationContainingIgnoreCase(String motCle1, String motCle2);

    // Exemple de requête personnalisée ** Requête JPQL/HQL
    @Query("select a from Article a where codeArticle = :code and designation = :designation")
    List<Article> findByCustumQuery(String code, String designation);

    // Requête JPQL/HQL
    @Query("select a from Article a where codeArticle = :code and designation = :designation")
    List<Article> findByCustumQuery2(@Param("code") String c, @Param("designation")String d);

    //Requête Native
    @Query(value = "select * from article where codearticle = :code and designation = :designation", nativeQuery = true)
    List<Article> findByCustumQuery3(@Param("code") String c, @Param("designation") String d);

    //Verifie si un article existe
    Boolean existsByCodeArticle(String codeArticle);

}
