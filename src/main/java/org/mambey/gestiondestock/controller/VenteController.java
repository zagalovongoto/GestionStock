package org.mambey.gestiondestock.controller;

import java.util.List;

import org.mambey.gestiondestock.controller.api.VenteApi;
import org.mambey.gestiondestock.dto.VentesDto;
import org.mambey.gestiondestock.services.VenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VenteController implements VenteApi{

    private final VenteService venteService;

    @Override
    public VentesDto save(VentesDto dto) {
        return venteService.save(dto);
    }

    @Override
    public VentesDto findById(Integer id) {
        return venteService.findById(id);
    }

    @Override
    public VentesDto findByCode(String code) {
        return venteService.findByCode(code);
    }

    @Override
    public List<VentesDto> findAll() {
        return venteService.findAll();
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {

        venteService.delete(id);

        return ResponseEntity.noContent().build();
    }
    
}
