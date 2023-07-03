package org.mambey.gestiondestock.controller;

import java.math.BigDecimal;
import java.util.List;

import org.mambey.gestiondestock.controller.api.CommandeFournisseurApi;
import org.mambey.gestiondestock.dto.CommandeFournisseurDto;
import org.mambey.gestiondestock.model.EtatCommande;
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

    @Override
    public ResponseEntity<CommandeFournisseurDto> updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(commandeFournisseurService.updateEtatCommande(idCommande, etatCommande));
    }

    @Override
    public ResponseEntity<CommandeFournisseurDto> updateQuantiteCommandee(Integer idCommande, Integer idLigneCommande,
            BigDecimal quantite) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(commandeFournisseurService.updateQuantiteCommandee(idCommande, idLigneCommande, quantite));
    }

    @Override
    public ResponseEntity<CommandeFournisseurDto> updateArticle(Integer idCommande, Integer idLigneCommande,
            Integer idArticle) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(commandeFournisseurService.updateArticle(idCommande, idLigneCommande, idArticle));
    }

    @Override
    public ResponseEntity<CommandeFournisseurDto> updateFournisseur(Integer idCommande, Integer idFournisseur) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(commandeFournisseurService.updateFournisseur(idCommande, idFournisseur));
    }

    @Override
    public ResponseEntity<CommandeFournisseurDto> deleteArticle(Integer idCommande, Integer idLigneCommande) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(commandeFournisseurService.deleteArticle(idCommande, idLigneCommande));
    }
    

}
