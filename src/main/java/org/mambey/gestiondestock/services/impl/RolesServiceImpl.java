package org.mambey.gestiondestock.services.impl;

import java.util.List;

import org.mambey.gestiondestock.dto.RolesDto;
import org.mambey.gestiondestock.exception.EntityAlreadyExistsException;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.repository.RolesRepository;
import org.mambey.gestiondestock.services.RolesService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RolesServiceImpl implements RolesService{

    private final RolesRepository rolesRepository;
    //private final ObjectsValidator<RolesDto> rolesValidator;

    @Override
    public RolesDto save(RolesDto dto) {
        //var violations = rolesValidator.validate(dto);

        // if(!violations.isEmpty()){
        //     log.error("Le role n'est pas valide {}", dto);
        //     throw new InvalidEntityException("Données invalides", ErrorCodes.ROLE_NOT_VALID, violations);
        // }
        // On vérifie l'existence de l'utilisateur
        if (rolesRepository.findByRoleName(dto.getRoleName()).isPresent()) {
            log.error("Le role de nom " + dto.getRoleName()+" existe déjà dans la base");
            throw new EntityAlreadyExistsException("Ce rôle existe déjà", ErrorCodes.ROLE_ALREADY_EXISTS);
        }

        return RolesDto.fromEntity(
            rolesRepository.save(RolesDto.toEntity(dto))
        );
    }

    @Override
    public RolesDto findById(Integer id) {
        return null;
    }

    @Override
    public List<RolesDto> findAll() {
        return null;
    }

    @Override
    public void delete(Integer id) {
        
    }
    
}
