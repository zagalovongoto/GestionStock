package org.mambey.gestiondestock.controller.api;

import java.math.BigDecimal;
import java.util.List;

import static org.mambey.gestiondestock.utils.Constants.APP_ROOT;

import org.mambey.gestiondestock.dto.CommandeClientDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping(value = APP_ROOT + "/commandeclients")
@Tag(name="commandeclt")
public interface CommandeClientApi {
    
    @PostMapping(value= "/create")
    @Operation(operationId = "saveCmdeClt")
    ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto dto);

    @PatchMapping(value= "/update/etat/{etatCommande}/{idCommande}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "CmdCltupdateEtatCommande")
    ResponseEntity<CommandeClientDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(value= "/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "CmdCltUpdateQuantiteCommandee")
    ResponseEntity<CommandeClientDto> updateQuantiteCommandee(
        @PathVariable("idCommande") Integer idCommande, 
        @PathVariable("idLigneCommande") Integer idLigneCommande,
        @PathVariable("quantite") BigDecimal quantite
    );

    @PatchMapping(value= "/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "CmdCltUpdateArticle")
    ResponseEntity<CommandeClientDto> updateArticle(
        @PathVariable("idCommande") Integer idCommande, 
        @PathVariable("idLigneCommande") Integer idLigneCommande, 
        @PathVariable("idArticle") Integer idArticle);

    @PatchMapping(value= "/update/client/{idCommande}/{idClient}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "CmdCltUpdateClient")
    ResponseEntity<CommandeClientDto> updateClient(@PathVariable("idCommande") Integer idCommande, @PathVariable("idClient") Integer idClient);

    @DeleteMapping(value= "/delete/article/{idCommande}/{idLigneCommande}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "CmdCltdeleteArticle")
    ResponseEntity<CommandeClientDto> deleteArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande);

    @GetMapping(value= "/{idCommandeClient}")
    @Operation(operationId = "findCmdCltById")
    ResponseEntity<CommandeClientDto> findById(@PathVariable("idCommandeClient") Integer id);

    @GetMapping(value= "/code/{codeCommandeClient}")
    @Operation(operationId = "findCmdCltByCode")
    ResponseEntity<CommandeClientDto> findByCode(@PathVariable("codeCommandeClient") String code);

    @GetMapping(value= "/all")
    @Operation(operationId = "findAllCmdClt")
    ResponseEntity<List<CommandeClientDto>> findAll();

    @GetMapping(value= "/page")
    @Operation(operationId = "findAllCmdCltByPage")
    ResponseEntity<List<CommandeClientDto>> findAllByPage(@RequestParam("page") int page, @RequestParam("size") int size);

    @DeleteMapping(value= "/delete/{idCommandeClient}")
    @Operation(operationId = "deleteCmdClt")
    ResponseEntity<Void> delete(@PathVariable("idCommandeClient") Integer id);
}
