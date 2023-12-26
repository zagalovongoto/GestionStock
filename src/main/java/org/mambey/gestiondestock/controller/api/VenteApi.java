package org.mambey.gestiondestock.controller.api;

import java.util.List;

import static org.mambey.gestiondestock.utils.Constants.APP_ROOT;
import org.mambey.gestiondestock.dto.VentesDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping(value = APP_ROOT + "/ventes")
@Tag(name="vente")
public interface VenteApi {
    
    @PostMapping(value= "/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "saveVente")
    VentesDto save(@org.springframework.web.bind.annotation.RequestBody VentesDto dto);

    @GetMapping(value = "/{idVente}")
    @Operation(operationId = "findVenteById")
    VentesDto findById(@PathVariable("idVente") Integer id);

    @GetMapping(value = "/rechercher/{codeVente}")
    @Operation(operationId = "findVenteByCode")
    VentesDto findByCode(@PathVariable("codeVente") String code);

    @GetMapping(value = "/all")
    @Operation(operationId = "findAllVente")
    List<VentesDto> findAll();

    @DeleteMapping(value = "/delete/{idVente}")
    @Operation(operationId = "deleteVente")
    void delete(@PathVariable("idVente") Integer id);
}
