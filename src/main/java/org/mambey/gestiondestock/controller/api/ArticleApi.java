package org.mambey.gestiondestock.controller.api;
import static org.mambey.gestiondestock.utils.Constants.APP_ROOT;

import java.util.List;

import org.mambey.gestiondestock.dto.ArticleDto;
import org.mambey.gestiondestock.dto.LigneCommandeClientDto;
import org.mambey.gestiondestock.dto.LigneCommandeFournisseurDto;
import org.mambey.gestiondestock.dto.LigneVenteDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping(value = APP_ROOT + "/articles")
@Tag(name="article")
public interface ArticleApi {
    
    @PostMapping(value= "/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "save article",
        description = "save article",
        operationId = "saveArticle"  // Spécifie l'identifiant d'opération
    )
    ArticleDto save(@RequestBody ArticleDto dto);

    @GetMapping(value= "/{idArticle}")
    @Operation(operationId = "findArticleById")
    ArticleDto findById(@PathVariable("idArticle") Integer id);

    @GetMapping(value= "/find/{codeArticle}")
    @Operation(operationId = "findArticleByCode")
    ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle);

    @GetMapping(value= "/all")
    @Operation(operationId = "findAllArticle")
    List<ArticleDto> findAll();

    @GetMapping(value= "/historique/vente/{idArticle}")
    @Operation(operationId = "findArticleHistoriqueVente")
    List<LigneVenteDto> findHistoriqueVentes(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(value= "/historique/commandeclient/{idArticle}")
    @Operation(operationId = "findArticleHistoriqueCommandeClient")
    List<LigneCommandeClientDto> findHistoriqueCommandeClient(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(value= "/historique/commandefournisseur/{idArticle}")
    @Operation(operationId = "findArticleHistoriqueCommandeFournisseur")
    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(value= "/filter/{idCategory}")
    @Operation(operationId = "findAllArticleByIdCategory")
    List<ArticleDto> findAllArticleByIdCategory(@PathVariable("idCategory") Integer idArticle);

    @DeleteMapping(value= "/delete/{idArticle}")
    @Operation(operationId = "deleteArticle")
    void delete(@PathVariable("idArticle") Integer id);
}
