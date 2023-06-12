package org.mambey.gestiondestock.controller.api;

import java.util.List;

import static org.mambey.gestiondestock.utils.Constants.APP_ROOT;

import org.mambey.gestiondestock.dto.CommandeFournisseurDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(value= "/{idCommandeFournisseur}")
    ResponseEntity<CommandeFournisseurDto> findById(@PathVariable("idCommandeFournisseur") Integer id);

    @GetMapping(value= "/code/{codeCommandeFournisseur}")
    ResponseEntity<CommandeFournisseurDto> findByCode(@PathVariable("codeCommandeFournisseur") String code);

    @GetMapping(value= "/all")
    ResponseEntity<List<CommandeFournisseurDto>> findAll();

    @DeleteMapping(value= "/delete/{idCommandeFournisseur}")
    ResponseEntity<Void> delete(@PathVariable("idCommandeFournisseur") Integer id);
}
