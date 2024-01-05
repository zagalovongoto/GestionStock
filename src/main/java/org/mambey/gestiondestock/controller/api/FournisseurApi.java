package org.mambey.gestiondestock.controller.api;
import static org.mambey.gestiondestock.utils.Constants.APP_ROOT;

import java.util.List;

import org.mambey.gestiondestock.dto.FournisseurDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping(value = APP_ROOT + "/fournisseurs")
@Tag(name="fournisseurApi")
public interface FournisseurApi {
    
    @PostMapping(
        value= "/create", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "saveFournisseur")
    FournisseurDto save(@RequestBody FournisseurDto dto);

    @GetMapping(
        value= "/{idFournisseurs}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findFournisseurById")
    FournisseurDto findById(@PathVariable("idFournisseurs") Integer id);

    @GetMapping(
        value= "/all", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findAllFournisseur")
    List<FournisseurDto> findAll();

    @DeleteMapping(
        value= "/delete/{idFournisseurs}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "deleteFournisseur")
    ResponseEntity<?> delete(@PathVariable("idFournisseurs") Integer id);
}
