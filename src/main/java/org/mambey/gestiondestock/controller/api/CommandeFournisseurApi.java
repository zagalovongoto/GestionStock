package org.mambey.gestiondestock.controller.api;

import java.math.BigDecimal;
import java.util.List;

import static org.mambey.gestiondestock.utils.Constants.APP_ROOT;

import org.mambey.gestiondestock.dto.CommandeFournisseurDto;
import org.mambey.gestiondestock.model.EtatCommande;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping(value = APP_ROOT + "/commandefournisseurs")
public interface CommandeFournisseurApi {
    
    @PostMapping(value= "/create")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<CommandeFournisseurDto> save(@RequestBody CommandeFournisseurDto dto);

    @PatchMapping(value= "/update/etat/{etatCommande}/{idCommande}")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<CommandeFournisseurDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(value= "/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<CommandeFournisseurDto> updateQuantiteCommandee(
        @PathVariable("idCommande") Integer idCommande, 
        @PathVariable("idLigneCommande") Integer idLigneCommande,
        @PathVariable("quantite") BigDecimal quantite
    );

    @PatchMapping(value= "/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<CommandeFournisseurDto> updateArticle(
        @PathVariable("idCommande") Integer idCommande, 
        @PathVariable("idLigneCommande") Integer idLigneCommande, 
        @PathVariable("idArticle") Integer idArticle);

    @PatchMapping(value= "/update/fournisseur/{idCommande}/{idFournisseur}")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<CommandeFournisseurDto> updateFournisseur(@PathVariable("idCommande") Integer idCommande, @PathVariable("idFournisseur") Integer idFournisseur);

    @DeleteMapping(value= "/delete/article/{idCommande}/{idLigneCommande}")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<CommandeFournisseurDto> deleteArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande);


    @GetMapping(value= "/{idCommandeFournisseur}")
    ResponseEntity<CommandeFournisseurDto> findById(@PathVariable("idCommandeFournisseur") Integer id);

    @GetMapping(value= "/code/{codeCommandeFournisseur}")
    ResponseEntity<CommandeFournisseurDto> findByCode(@PathVariable("codeCommandeFournisseur") String code);

    @GetMapping(value= "/all")
    ResponseEntity<List<CommandeFournisseurDto>> findAll();

    @DeleteMapping(value= "/delete/{idCommandeFournisseur}")
    ResponseEntity<Void> delete(@PathVariable("idCommandeFournisseur") Integer id);
}
