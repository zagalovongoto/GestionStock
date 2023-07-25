package org.mambey.gestiondestock.controller;

import java.math.BigDecimal;
import java.util.List;

import org.mambey.gestiondestock.controller.api.CommandeClientApi;
import org.mambey.gestiondestock.dto.CommandeClientDto;
import org.mambey.gestiondestock.model.EtatCommande;
import org.mambey.gestiondestock.services.CommandeClientService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommandeClientController implements CommandeClientApi{

    private final CommandeClientService commandeClientService;

    @Override
    public ResponseEntity<CommandeClientDto> save(CommandeClientDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(commandeClientService.save(dto));
    }

    @Override
    public ResponseEntity<CommandeClientDto> updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(commandeClientService.updateEtatCommande(idCommande, etatCommande));
    }


    @Override
    public ResponseEntity<CommandeClientDto> updateQuantiteCommandee(Integer idCommande, Integer idLigneCommande,
            BigDecimal quantite) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(commandeClientService.updateQuantiteCommandee(idCommande, idLigneCommande, quantite));
    } 

    @Override
    public ResponseEntity<CommandeClientDto> updateClient(Integer idCommande, Integer idClient) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(commandeClientService.updateClient(idCommande, idClient));
    }

    @Override
    public ResponseEntity<CommandeClientDto> updateArticle(Integer idCommande, Integer idLigneCommande,
            Integer idArticle) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(commandeClientService.updateArticle(idCommande, idLigneCommande, idArticle));
    }


    @Override
    public ResponseEntity<CommandeClientDto> deleteArticle(Integer idCommande, Integer idLigneCommande) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(commandeClientService.deleteArticle(idCommande, idLigneCommande));
    }

    @Override
    public ResponseEntity<CommandeClientDto> findById(Integer id) {
        return ResponseEntity.ok(commandeClientService.findById(id));
    }

    @Override
    public ResponseEntity<CommandeClientDto> findByCode(String code) {
        return ResponseEntity.ok(commandeClientService.findByCode(code));
    }

    @Override
    public ResponseEntity<List<CommandeClientDto>> findAll() {
        return ResponseEntity.ok(commandeClientService.findAll());
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) {
        commandeClientService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<CommandeClientDto>> findAllByPage(int page, int size) {

        Page<CommandeClientDto> mapage = commandeClientService.findAllByPage(page, size);
        
        return ResponseEntity.ok(mapage.getContent());
    }

}
