package org.mambey.gestiondestock.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mambey.gestiondestock.dto.UtilisateurDto;
import org.mambey.gestiondestock.exception.EntityAlreadyExistsException;
import org.mambey.gestiondestock.exception.EntityNotFoundException;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvaliddEntityException;
import org.mambey.gestiondestock.model.Roles;
import org.mambey.gestiondestock.model.Utilisateur;
import org.mambey.gestiondestock.repository.RolesRepository;
import org.mambey.gestiondestock.repository.UtilisateurRepository;
import org.mambey.gestiondestock.security.model.ERole;
import org.mambey.gestiondestock.services.ObjectsValidator;
import org.mambey.gestiondestock.services.UtilisateurService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService{

    private final UtilisateurRepository utilisateurRepository;
    private final RolesRepository roleRepository;
    private final PasswordEncoder encoder;

    private final ObjectsValidator<UtilisateurDto> utilisateurValidator;

    @Override
    public UtilisateurDto save(UtilisateurDto dto) {

        var violations = utilisateurValidator.validate(dto);

        if(!violations.isEmpty()){
            log.error("L'utilisateur n'est pas valide {}", dto);
            throw new InvaliddEntityException("Données invalides", ErrorCodes.UTILISATEUR_NOT_VALID, violations);
        }
        // On vérifie l'existence des différents roles renseignés
        if (utilisateurRepository.existsByEmail(dto.getEmail())) {
            throw new EntityAlreadyExistsException("Un utilisateur avec cette adresse email existe déjà", ErrorCodes.UTILISATEUR_NOT_VALID);
        }
        
        Set<String> strRoles = dto.getRoles();
        Set<Roles> roles = new HashSet<>();

        if (strRoles == null) {
            Roles userRole = roleRepository.findByRoleName(ERole.ROLE_USER.name())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun role avec le nom " + ERole.ROLE_USER + " n'a été trouvé dans la BDD", 
                        ErrorCodes.ROLE_NOT_FOUND));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                case "ADMIN":
                Roles adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN.name())
                    .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun role avec le nom " + ERole.ROLE_ADMIN + " n'a été trouvé dans la BDD", 
                        ErrorCodes.ROLE_NOT_FOUND));
                roles.add(adminRole);

                break;
                case "MODERATEUR":
                Roles modRole = roleRepository.findByRoleName(ERole.ROLE_MODERATOR.name())
                    .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun role avec le nom " + ERole.ROLE_MODERATOR + " n'a été trouvé dans la BDD", 
                        ErrorCodes.ROLE_NOT_FOUND));
                roles.add(modRole);

                break;
                default:
                Roles userRole = roleRepository.findByRoleName(ERole.ROLE_USER.name())
                    .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun role avec le nom " + ERole.ROLE_USER + " n'a été trouvé dans la BDD", 
                        ErrorCodes.ROLE_NOT_FOUND));
                roles.add(userRole);
                }
            });
        }

        dto.setMotDePasse(encoder.encode(dto.getMotDePasse()));
        Utilisateur user = UtilisateurDto.toEntity(dto);
        user.setRoles(roles);

        return UtilisateurDto.fromEntity(
            utilisateurRepository.save(user)
        );
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        
        if(id == null){
            log.error("Utilisateur ID is null");
            return null;
        }

        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);

        return utilisateur.map(UtilisateurDto::fromEntity)
                      .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun utilisateur avec l'ID " + id + " n'a été trouvé dans la BDD", 
                        ErrorCodes.UTILISATEUR_NOT_FOUND));

    }

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll().stream()
                                .map(UtilisateurDto::fromEntity)
                                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Fournisseur ID is null");
        }

        utilisateurRepository.deleteById(id);
    }
    

}
