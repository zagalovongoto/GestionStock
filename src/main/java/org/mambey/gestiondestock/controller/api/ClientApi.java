package org.mambey.gestiondestock.controller.api;
import static org.mambey.gestiondestock.utils.Constants.APP_ROOT;

import java.util.List;

import org.mambey.gestiondestock.dto.ClientDto;
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

@RequestMapping(value = APP_ROOT + "/clients")
@Tag(name="clientApi")
public interface ClientApi {
    
    @PostMapping(
        value= "/create", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "saveClient")
    ClientDto save(@RequestBody ClientDto dto);

    @GetMapping(
        value= "/{idClient}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findClientById")
    ClientDto findById(@PathVariable("idClient") Integer id);

    @GetMapping(
        value= "/all", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "findAllClient")
    List<ClientDto> findAll();

    @DeleteMapping(
        value= "/delete/{idClient}", 
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "deleteClient")
    void delete(@PathVariable("idClient") Integer id);
}
