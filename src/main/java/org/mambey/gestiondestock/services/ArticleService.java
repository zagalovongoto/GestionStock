package org.mambey.gestiondestock.services;

import java.util.List;

import org.mambey.gestiondestock.dto.ArticleDto;
import org.mambey.gestiondestock.dto.LigneCommandeClientDto;
import org.mambey.gestiondestock.dto.LigneCommandeFournisseurDto;
import org.mambey.gestiondestock.dto.LigneVenteDto;
import org.mambey.gestiondestock.model.Article;

public interface ArticleService {
    
    ArticleDto save(ArticleDto dto);

    ArticleDto findById(Integer id);

    ArticleDto findByCodeArticle(String codeArticle);

    List<ArticleDto> findAll();

    List<LigneVenteDto> findHistoriqueVentes(Integer idArticle);

    List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle);

    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle);

    List<ArticleDto> findAllArticleByIdCategory(Integer idCategory);

    List<ArticleDto>  findByCodeArticleContainingIgnoreCaseOrDesignationContainingIgnoreCase(String motCle);

    void delete(Integer id);
    
}
