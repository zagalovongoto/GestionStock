package org.mambey.gestiondestock.controller.api;
import static org.mambey.gestiondestock.utils.Constants.APP_ROOT;

import java.util.List;

import org.mambey.gestiondestock.dto.ArticleDto;
import org.mambey.gestiondestock.dto.LigneCommandeClientDto;
import org.mambey.gestiondestock.dto.LigneCommandeFournisseurDto;
import org.mambey.gestiondestock.dto.LigneVenteDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping(value = APP_ROOT + "/articles")
@Tag(name="articleApi")
public interface ArticleApi {
    
    @PostMapping(
        value= "/create", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "save article",
        description = "save article",
        operationId = "saveArticle"  // Spécifie l'identifiant d'opération
    )
    ArticleDto save(@RequestBody ArticleDto dto);

    @GetMapping(
        value= "/{idArticle}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findArticleById")
    ArticleDto findById(@PathVariable("idArticle") Integer id);

    @GetMapping(
        value= "/find/{codeArticle}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findArticleByCode")
    ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle);

    @GetMapping(
        value= "/all", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findAllArticle")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<ArticleDto> findAll();

    @GetMapping(
        value= "/historique/vente/{idArticle}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findArticleHistoriqueVente")
    List<LigneVenteDto> findHistoriqueVentes(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(
        value= "/historique/commandeclient/{idArticle}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findArticleHistoriqueCommandeClient")
    List<LigneCommandeClientDto> findHistoriqueCommandeClient(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(
        value= "/historique/commandefournisseur/{idArticle}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findArticleHistoriqueCommandeFournisseur")
    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(
        value= "/filter/{idCategory}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findAllArticleByIdCategory")
    List<ArticleDto> findAllArticleByIdCategory(@PathVariable("idCategory") Integer idArticle);

    @DeleteMapping(
        value= "/delete/{idArticle}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "deleteArticle")
    ResponseEntity<?> delete(@PathVariable("idArticle") Integer id);

    @GetMapping(
            value= "/by-motcle",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findArticleByMotCle")
    List<ArticleDto> findByMotCle(@RequestParam String motCle);
}
