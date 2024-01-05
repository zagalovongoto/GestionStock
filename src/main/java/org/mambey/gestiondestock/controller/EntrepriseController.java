package org.mambey.gestiondestock.controller;

import java.util.List;

import org.mambey.gestiondestock.controller.api.EntrepriseApi;
import org.mambey.gestiondestock.dto.EntrepriseDto;
import org.mambey.gestiondestock.services.EntrepriseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EntrepriseController implements EntrepriseApi{
    
    private final EntrepriseService entrepriseService;

    @Override
    public EntrepriseDto save(EntrepriseDto dto) {
        return entrepriseService.save(dto);
    }

    @Override
    public EntrepriseDto findById(Integer id) {
        return entrepriseService.findById(id);
    }

    @Override
    public List<EntrepriseDto> findAll() {
        return entrepriseService.findAll();
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {

        entrepriseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    
}
