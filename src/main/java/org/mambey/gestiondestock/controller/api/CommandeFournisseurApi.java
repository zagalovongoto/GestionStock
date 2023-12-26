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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping(value = APP_ROOT + "/commandefournisseurs")
@Tag(name="commandefrs")
public interface CommandeFournisseurApi {
    
    @PostMapping(value= "/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "CmdFrsupdateEtatCommande")
    ResponseEntity<CommandeFournisseurDto> save(@RequestBody CommandeFournisseurDto dto);

    @PatchMapping(value= "/update/etat/{etatCommande}/{idCommande}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "CmdFrsupdateEtatCommande")
    ResponseEntity<CommandeFournisseurDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(value= "/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "CmdFrsUpdateQuantiteCommandee")
    ResponseEntity<CommandeFournisseurDto> updateQuantiteCommandee(
        @PathVariable("idCommande") Integer idCommande, 
        @PathVariable("idLigneCommande") Integer idLigneCommande,
        @PathVariable("quantite") BigDecimal quantite
    );

    @PatchMapping(value= "/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "CmdFrsUpdateArticle")
    ResponseEntity<CommandeFournisseurDto> updateArticle(
        @PathVariable("idCommande") Integer idCommande, 
        @PathVariable("idLigneCommande") Integer idLigneCommande, 
        @PathVariable("idArticle") Integer idArticle);

    @PatchMapping(value= "/update/fournisseur/{idCommande}/{idFournisseur}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "CmdFrsUpdateClient")
    ResponseEntity<CommandeFournisseurDto> updateFournisseur(@PathVariable("idCommande") Integer idCommande, @PathVariable("idFournisseur") Integer idFournisseur);

    @DeleteMapping(value= "/delete/article/{idCommande}/{idLigneCommande}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "CmdFrsdeleteArticle")
    ResponseEntity<CommandeFournisseurDto> deleteArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande);


    @GetMapping(value= "/{idCommandeFournisseur}")
    @Operation(operationId = "findCmdFrsById")
    ResponseEntity<CommandeFournisseurDto> findById(@PathVariable("idCommandeFournisseur") Integer id);

    @GetMapping(value= "/code/{codeCommandeFournisseur}")
    @Operation(operationId = "findCmdFrsByCode")
    ResponseEntity<CommandeFournisseurDto> findByCode(@PathVariable("codeCommandeFournisseur") String code);

    @GetMapping(value= "/all")
    @Operation(operationId = "findAllCmdFrs")
    ResponseEntity<List<CommandeFournisseurDto>> findAll();

    @DeleteMapping(value= "/delete/{idCommandeFournisseur}")
    @Operation(operationId = "deleteCmdFrs")
    ResponseEntity<Void> delete(@PathVariable("idCommandeFournisseur") Integer id);
}
