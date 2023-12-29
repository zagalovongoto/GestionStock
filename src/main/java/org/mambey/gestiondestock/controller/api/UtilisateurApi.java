package org.mambey.gestiondestock.controller.api;
import static org.mambey.gestiondestock.utils.Constants.APP_ROOT;

import java.util.List;

import org.mambey.gestiondestock.dto.UtilisateurDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping(value = APP_ROOT + "/utilisateurs")
@Tag(name="utilisateurApi")
public interface UtilisateurApi {
    
    @PostMapping(
        value= "/create", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "saveUtilisateur")
    UtilisateurDto save(@RequestBody UtilisateurDto dto);

    @GetMapping(
        value= "/{idUtilisateur}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findUtilisateurById")
    UtilisateurDto findById(@PathVariable("idUtilisateur") Integer id);

    @GetMapping(
        value= "/find/{emailUtilisateur}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findUtilisateurByEmail")
    UtilisateurDto findByEmail(@PathVariable("emailUtilisateur") String email);

    @GetMapping(
        value= "/all", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findAllUtilisateur")
    List<UtilisateurDto> findAll();

    @DeleteMapping(
        value= "/delete/{idUtilisateur}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "deleteUtilisateur")
    void delete(@PathVariable("idUtilisateur") Integer id);
}
