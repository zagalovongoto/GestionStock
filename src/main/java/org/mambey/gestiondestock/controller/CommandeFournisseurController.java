package org.mambey.gestiondestock.controller;

import java.util.List;

import org.mambey.gestiondestock.controller.api.CommandeFournisseurApi;
import org.mambey.gestiondestock.dto.CommandeFournisseurDto;
import org.mambey.gestiondestock.services.CommandeFournisseurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommandeFournisseurController implements CommandeFournisseurApi{

    private final CommandeFournisseurService commandeFournisseurService;

    @Override
    public ResponseEntity<CommandeFournisseurDto> save(CommandeFournisseurDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(commandeFournisseurService.save(dto));
    }

    @Override
    public ResponseEntity<CommandeFournisseurDto> findById(Integer id) {
        return ResponseEntity.ok(commandeFournisseurService.findById(id));
    }

    @Override
    public ResponseEntity<CommandeFournisseurDto> findByCode(String code) {
        return ResponseEntity.ok(commandeFournisseurService.findByCode(code));
    }

    @Override
    public ResponseEntity<List<CommandeFournisseurDto>> findAll() {
        return ResponseEntity.ok(commandeFournisseurService.findAll());
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) {
        commandeFournisseurService.delete(id);
        return ResponseEntity.ok().build();
    }
    

}
