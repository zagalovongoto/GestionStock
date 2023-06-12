package org.mambey.gestiondestock.controller.api;

import java.util.List;

import static org.mambey.gestiondestock.utils.Constants.APP_ROOT;

import org.mambey.gestiondestock.dto.CommandeClientDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping(value = APP_ROOT + "/commandeclients")
public interface CommandeClientApi {
    
    @PostMapping(value= "/create")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto dto);

    @GetMapping(value= "/{idCommandeClient}")
    ResponseEntity<CommandeClientDto> findById(@PathVariable("idCommandeClient") Integer id);

    @GetMapping(value= "/code/{codeCommandeClient}")
    ResponseEntity<CommandeClientDto> findByCode(@PathVariable("codeCommandeClient") String code);

    @GetMapping(value= "/all")
    ResponseEntity<List<CommandeClientDto>> findAll();

    @DeleteMapping(value= "/delete/{idCommandeClient}")
    ResponseEntity<Void> delete(@PathVariable("idCommandeClient") Integer id);
}
