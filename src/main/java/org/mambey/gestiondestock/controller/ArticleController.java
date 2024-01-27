package org.mambey.gestiondestock.controller;

import java.util.List;

import org.mambey.gestiondestock.controller.api.ArticleApi;
import org.mambey.gestiondestock.dto.ArticleDto;
import org.mambey.gestiondestock.dto.LigneCommandeClientDto;
import org.mambey.gestiondestock.dto.LigneCommandeFournisseurDto;
import org.mambey.gestiondestock.dto.LigneVenteDto;
import org.mambey.gestiondestock.services.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ArticleController implements ArticleApi{
    
    private final ArticleService articleService;
    //private final String context = "article";

    @Override
    public ArticleDto save(ArticleDto dto) {
        return articleService.save(dto);
    }

    @Override
    public ArticleDto findById(Integer id) {
        return articleService.findById(id);
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        return articleService.findByCodeArticle(codeArticle);
    }

    @Override
    public List<ArticleDto> findAll() {
        
        return articleService.findAll();/* .stream()
                      .map(article -> {
                        article.setPhoto(UrlGenerator.generate(context, article.getId()));
                        return article;
                      })
                      .collect(Collectors.toList()); */
                      
    }

    public List<ArticleDto> findByMotCle(String motCle){
        return articleService.findByCodeArticleContainingIgnoreCaseOrDesignationContainingIgnoreCase(motCle);
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVentes(Integer idArticle) {
        return articleService.findHistoriqueVentes(idArticle);
    }

    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle) {
        return articleService.findHistoriqueCommandeClient(idArticle);
    }

    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle) {
        return articleService.findHistoriqueCommandeFournisseur(idArticle);
    }

    @Override
    public List<ArticleDto> findAllArticleByIdCategory(Integer idArticle) {
        return articleService.findAllArticleByIdCategory(idArticle);
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        articleService.delete(id);

        return ResponseEntity.noContent().build();
    }   
}
