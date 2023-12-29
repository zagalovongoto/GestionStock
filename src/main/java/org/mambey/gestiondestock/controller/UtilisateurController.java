package org.mambey.gestiondestock.controller;

import java.util.List;

import org.mambey.gestiondestock.controller.api.UtilisateurApi;
import org.mambey.gestiondestock.dto.UtilisateurDto;
import org.mambey.gestiondestock.services.UtilisateurService;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UtilisateurController implements UtilisateurApi{
    
    private final UtilisateurService utilisateurService;

    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        System.out.println(dto.toString());
        return utilisateurService.save(dto);
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        return utilisateurService.findById(id);
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurService.findAll();
    }

    @Override
    public void delete(Integer id) {
        utilisateurService.delete(id);
    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        return utilisateurService.findByEmail(email);
    }

    
}
