package org.mambey.gestiondestock.controller.api;

import java.math.BigDecimal;
import java.util.List;

import static org.mambey.gestiondestock.utils.Constants.APP_ROOT;

import org.mambey.gestiondestock.dto.CommandeFournisseurDto;
import org.mambey.gestiondestock.model.EtatCommande;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@Tag(name="commandefrsApi")
public interface CommandeFournisseurApi {
    
    @PostMapping(
        value= "/create",
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "CmdFrsupdateEtatCommande")
    ResponseEntity<CommandeFournisseurDto> save(@RequestBody CommandeFournisseurDto dto);

    @PatchMapping(
        value= "/update/etat/{etatCommande}/{idCommande}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "CmdFrsupdateEtatCommande")
    ResponseEntity<CommandeFournisseurDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(
        value= "/update/quantite/{idCommande}/{idLigneCommande}/{quantite}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "CmdFrsUpdateQuantiteCommandee")
    ResponseEntity<CommandeFournisseurDto> updateQuantiteCommandee(
        @PathVariable("idCommande") Integer idCommande, 
        @PathVariable("idLigneCommande") Integer idLigneCommande,
        @PathVariable("quantite") BigDecimal quantite
    );

    @PatchMapping(
        value= "/update/article/{idCommande}/{idLigneCommande}/{idArticle}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "CmdFrsUpdateArticle")
    ResponseEntity<CommandeFournisseurDto> updateArticle(
        @PathVariable("idCommande") Integer idCommande, 
        @PathVariable("idLigneCommande") Integer idLigneCommande, 
        @PathVariable("idArticle") Integer idArticle);

    @PatchMapping(
        value= "/update/fournisseur/{idCommande}/{idFournisseur}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "CmdFrsUpdateClient")
    ResponseEntity<CommandeFournisseurDto> updateFournisseur(@PathVariable("idCommande") Integer idCommande, @PathVariable("idFournisseur") Integer idFournisseur);

    @DeleteMapping(
        value= "/delete/article/{idCommande}/{idLigneCommande}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "CmdFrsdeleteArticle")
    ResponseEntity<CommandeFournisseurDto> deleteArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande);


    @GetMapping(
        value= "/{idCommandeFournisseur}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findCmdFrsById")
    ResponseEntity<CommandeFournisseurDto> findById(@PathVariable("idCommandeFournisseur") Integer id);

    @GetMapping(
        value= "/code/{codeCommandeFournisseur}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findCmdFrsByCode")
    ResponseEntity<CommandeFournisseurDto> findByCode(@PathVariable("codeCommandeFournisseur") String code);

    @GetMapping(
        value= "/all",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findAllCmdFrs")
    ResponseEntity<List<CommandeFournisseurDto>> findAll();

    @DeleteMapping(
        value= "/delete/{idCommandeFournisseur}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "deleteCmdFrs")
    ResponseEntity<Void> delete(@PathVariable("idCommandeFournisseur") Integer id);
}
