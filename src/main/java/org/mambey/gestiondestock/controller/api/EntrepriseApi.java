package org.mambey.gestiondestock.controller.api;
import static org.mambey.gestiondestock.utils.Constants.APP_ROOT;

import java.util.List;

import org.mambey.gestiondestock.dto.EntrepriseDto;
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

@RequestMapping(value = APP_ROOT + "/entreprises")
@Tag(name="entrepriseApi")
public interface EntrepriseApi {
    
    @PostMapping(
        value= "/create", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "saveEntreprise")
    EntrepriseDto save(@RequestBody EntrepriseDto dto);

    @GetMapping(
        value= "/{idEntreprises}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findEntrepriseById")
    EntrepriseDto findById(@PathVariable("idEntreprises") Integer id);

    @GetMapping(
        value= "/all", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findAllEntreprise")
    List<EntrepriseDto> findAll();

    @DeleteMapping(
        value= "/delete/{idEntreprises}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "deleteEntreprise")
    void delete(@PathVariable("idEntreprises") Integer id);
}
