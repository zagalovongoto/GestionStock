package org.mambey.gestiondestock.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mambey.gestiondestock.dto.FournisseurDto;
import org.mambey.gestiondestock.exception.EntityNotFoundException;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvalidOperationException;
import org.mambey.gestiondestock.exception.InvalidEntityException;
import org.mambey.gestiondestock.model.CommandeFournisseur;
import org.mambey.gestiondestock.model.Fournisseur;
import org.mambey.gestiondestock.repository.CommandeFournisseurRepository;
import org.mambey.gestiondestock.repository.FournisseurRepository;
import org.mambey.gestiondestock.services.FournisseurService;
import org.mambey.gestiondestock.services.ObjectsValidator;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FournisseurServiceImpl implements FournisseurService{

    private final FournisseurRepository fournisseurRepository;
    private final CommandeFournisseurRepository commandeFournisseurRepository;
    private final ObjectsValidator<FournisseurDto> fournisseurValidator;

    @Override
    public FournisseurDto save(FournisseurDto dto) {

        Integer idEntreprise = Integer.parseInt(MDC.get("idEntreprise"));
        dto.setIdEntreprise(idEntreprise);
        
        var violations = fournisseurValidator.validate(dto);

        if(!violations.isEmpty()){
            log.error("Le fournisseur n'est pas valide {}", dto);
            throw new InvalidEntityException("Données invalides", ErrorCodes.FOURNISSEUR_NOT_VALID, violations);
        }

        return FournisseurDto.fromEntity(
            fournisseurRepository.save(FournisseurDto.toEntity(dto))
        );
    }

    @Override
    public FournisseurDto findById(Integer id) {
        
        if(id == null){
            log.error("Fournisseur ID is null");
            return null;
        }

        Optional<Fournisseur> client = fournisseurRepository.findById(id);

        return client.map(FournisseurDto::fromEntity)
                      .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun fournisseur avec l'ID " + id + " n'a été trouvé dans la BDD", 
                        ErrorCodes.FOURNISSEUR_NOT_FOUND));

    }

    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurRepository.findAll().stream()
                                .map(FournisseurDto::fromEntity)
                                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Fournisseur ID is null");
        }

        findById(id);

        List<CommandeFournisseur> commandeFournisseurs = commandeFournisseurRepository.findAllByFournisseurId(id);
        if(!commandeFournisseurs.isEmpty()){
            throw new InvalidOperationException(
                "Impossible de supprimer un fournisseur qui a deja des commandes",
                ErrorCodes.FOURNISSEUR_ALREADY_IN_USE
            );
        }

        fournisseurRepository.deleteById(id);
    }
    

}
