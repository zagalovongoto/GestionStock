package org.mambey.gestiondestock.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mambey.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import org.mambey.gestiondestock.dto.UtilisateurDto;
import org.mambey.gestiondestock.exception.EntityAlreadyExistsException;
import org.mambey.gestiondestock.exception.EntityNotFoundException;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvalidOperationException;
import org.mambey.gestiondestock.exception.InvalidEntityException;
import org.mambey.gestiondestock.model.Roles;
import org.mambey.gestiondestock.model.Utilisateur;
import org.mambey.gestiondestock.repository.RolesRepository;
import org.mambey.gestiondestock.repository.UtilisateurRepository;
import org.mambey.gestiondestock.security.model.ERole;
import org.mambey.gestiondestock.services.ObjectsValidator;
import org.mambey.gestiondestock.services.UtilisateurService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
            throw new InvalidEntityException("Données invalides", ErrorCodes.UTILISATEUR_NOT_VALID, violations);
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
                Roles modRole = roleRepository.findByRoleName(ERole.ROLE_MODERATEUR.name())
                    .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun role avec le nom " + ERole.ROLE_MODERATEUR + " n'a été trouvé dans la BDD", 
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

        findById(id);

        utilisateurRepository.deleteById(id);
    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        
        return utilisateurRepository.findUtilisateurByEmail(email)
            .map(UtilisateurDto::fromEntity)
            .orElseThrow(() -> new EntityNotFoundException(
                "Aucun utilisateur avec l'email = " + email + " n'a été trouvé dans la BDD",
                ErrorCodes.UTILISATEUR_NOT_FOUND
            ));
    }

    @Override
    public UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto) {
        
        validate(dto);
        
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(dto.getId());
        if(utilisateurOptional.isEmpty()){
            log.warn("Aucun utilisateur n'a été trouvé avec l'ID "+ dto.getId());
            throw new EntityNotFoundException(
                "Aucun utilisateur n'a été trouvé avec l'ID "+ dto.getId() , 
                ErrorCodes.UTILISATEUR_NOT_FOUND
            );
        }

        Utilisateur utilisateur = utilisateurOptional.get();
        utilisateur.setMotDePasse(encoder.encode(dto.getMotDePasse()));
        return UtilisateurDto.fromEntity(
            utilisateurRepository.save(utilisateur)
        );
    }
    

    private void validate(ChangerMotDePasseUtilisateurDto dto){

        if(dto == null){
            log.warn("Impossible de modifier le mot de passe avec un objet null");
            throw new InvalidEntityException(
                "Aucune information n'a été fourni pour pouvoir changer le mot de passe." , 
                ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID
            );
        }

        if(dto.getId() == null){
            log.warn("Impossible de modifier le mot de passe avec un ID null");
            throw new InvalidEntityException(
                "ID utilisateur null: impossible de modifier le mot de passe" , 
                ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID
            );
        }

        if(!StringUtils.hasLength(dto.getMotDePasse()) || !StringUtils.hasLength(dto.getConfirmMotDePasse())){
            log.warn("Impossible de modifier le mot de passe avec un ID null");
            throw new InvalidOperationException(
                "Impossible de modifier le mot de passe avec un mot de passe NULL" , 
                ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID
            );
        }

        if(!dto.getMotDePasse().equals(dto.getConfirmMotDePasse())){
            log.warn("Impossible de modifier le mot de passe avec deux mots de passe différents");
            throw new InvalidOperationException(
                "MOts de passe utilisateur non conformes: Impossible de modifier le mot de passe" , 
                ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID
            );
        }
    }
}
