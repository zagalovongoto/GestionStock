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

@RequestMapping(value = APP_ROOT + "/articles")
public interface ArticleApi {
    
    @PostMapping(value= "/create")
    @ResponseStatus(HttpStatus.CREATED)
    ArticleDto save(@RequestBody ArticleDto dto);

    @GetMapping(value= "/{idArticle}")
    ArticleDto findById(@PathVariable("idArticle") Integer id);

    @GetMapping(value= "/find/{codeArticle}")
    ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle);

    @GetMapping(value= "/all")
    List<ArticleDto> findAll();

    @GetMapping(value= "/historique/vente/{idArticle}")
    List<LigneVenteDto> findHistoriqueVentes(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(value= "/historique/commandeclient/{idArticle}")
    List<LigneCommandeClientDto> findHistoriqueCommandeClient(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(value= "/historique/commandefournisseur/{idArticle}")
    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(value= "/filter/{idCategory}")
    List<ArticleDto> findAllArticleByIdCategory(@PathVariable("idCategory") Integer idArticle);

    @DeleteMapping(value= "/delete/{idArticle}")
    void delete(@PathVariable("idArticle") Integer id);
}
