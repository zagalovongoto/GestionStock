package org.mambey.gestiondestock.controller;

import java.util.List;

import org.mambey.gestiondestock.controller.api.FournisseurApi;
import org.mambey.gestiondestock.dto.FournisseurDto;
import org.mambey.gestiondestock.services.FournisseurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FournisseurController implements FournisseurApi{
    
    private final FournisseurService fournisseurService;

    @Override
    public FournisseurDto save(FournisseurDto dto) {

        return fournisseurService.save(dto);
    }

    @Override
    public FournisseurDto findById(Integer id) {
        return fournisseurService.findById(id);
    }

    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurService.findAll();
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {

        fournisseurService.delete(id);
        return ResponseEntity.noContent().build();
    }

    
}
