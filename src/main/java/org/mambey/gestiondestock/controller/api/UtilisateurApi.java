package org.mambey.gestiondestock.controller.api;
import static org.mambey.gestiondestock.utils.Constants.APP_ROOT;

import java.util.List;

import org.mambey.gestiondestock.dto.UtilisateurDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name="utilisateur")
public interface UtilisateurApi {
    
    @PostMapping(value= "/create")
    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "saveUtilisateur")
    UtilisateurDto save(@RequestBody UtilisateurDto dto);

    @GetMapping(value= "/{idUtilisateur}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @Operation(operationId = "findUtilisateurById")
    UtilisateurDto findById(@PathVariable("idUtilisateur") Integer id);

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping(value= "/all")
    @Operation(operationId = "findAllUtilisateur")
    List<UtilisateurDto> findAll();

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping(value= "/delete/{idUtilisateur}")
    @Operation(operationId = "deleteUtilisateur")
    void delete(@PathVariable("idUtilisateur") Integer id);
}
