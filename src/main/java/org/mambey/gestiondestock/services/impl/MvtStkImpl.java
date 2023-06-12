package org.mambey.gestiondestock.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mambey.gestiondestock.dto.MvtStkDto;
import org.mambey.gestiondestock.exception.EntityNotFoundException;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvaliddEntityException;
import org.mambey.gestiondestock.model.MvtStk;
import org.mambey.gestiondestock.repository.MvtStkRepository;
import org.mambey.gestiondestock.services.MvtStkService;
import org.mambey.gestiondestock.services.ObjectsValidator;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MvtStkImpl implements MvtStkService{

    private final MvtStkRepository mvtStkRepository;

    private final ObjectsValidator<MvtStkDto> mvtStkValidator;

    @Override
    public MvtStkDto save(MvtStkDto dto) {
        
        var violations = mvtStkValidator.validate(dto);

        if(!violations.isEmpty()){
            log.error("Le mouvelent de stock n'est pas valide {}", dto);
            throw new InvaliddEntityException("Données invalides", ErrorCodes.MVT_STK_NOT_VALID, violations);
        }

        return MvtStkDto.fromEntity(
            mvtStkRepository.save(MvtStkDto.toEntity(dto))
        );
    }

    @Override
    public MvtStkDto findById(Integer id) {
        
        if(id == null){
            log.error("Client ID is null");
            return null;
        }

        Optional<MvtStk> mvtStk = mvtStkRepository.findById(id);

        return mvtStk.map(MvtStkDto::fromEntity)
                     .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun mouvement de stock avec l'ID " + id + " n'a été trouvé dans la BDD", 
                        ErrorCodes.CLIENT_NOT_FOUND));
    }

    @Override
    public List<MvtStkDto> findAll() {
        
        return mvtStkRepository.findAll().stream()
                                .map(MvtStkDto::fromEntity)
                                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        
        if(id == null){
            log.error("Mouvement stock ID is null");
        }

        mvtStkRepository.deleteById(id);
    }
    

}
