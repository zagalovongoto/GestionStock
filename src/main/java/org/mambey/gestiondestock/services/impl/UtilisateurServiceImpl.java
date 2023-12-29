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
import org.mambey.gestiondestock.exception.InvalidEntityException;
import org.mambey.gestiondestock.model.Roles;
import org.mambey.gestiondestock.model.Utilisateur;
import org.mambey.gestiondestock.repository.RolesRepository;
import org.mambey.gestiondestock.repository.UtilisateurRepository;
import org.mambey.gestiondestock.security.dto.ChangerMotDePasse;
import org.mambey.gestiondestock.security.model.ERole;
import org.mambey.gestiondestock.services.EntrepriseService;
import org.mambey.gestiondestock.services.ObjectsValidator;
import org.mambey.gestiondestock.services.UtilisateurService;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final EntrepriseService entrepriseService;

    private final ObjectsValidator<UtilisateurDto> utilisateurValidator;
    private final ObjectsValidator<ChangerMotDePasse> updateValidator;

    @Override
    public UtilisateurDto save(UtilisateurDto dto) {

        var violations = utilisateurValidator.validate(dto);

        if(!violations.isEmpty()){
            log.error("L'utilisateur n'est pas valide {}", dto);
            throw new InvalidEntityException("Données invalides", ErrorCodes.UTILISATEUR_NOT_VALID, violations);
        }
        // On vérifie l'existence de l'utilisateur
        if (utilisateurRepository.existsByEmail(dto.getEmail())) {
            log.error("L'utilisateur avec l'email " + dto.getEmail()+" existe déjà dans la base");
            throw new EntityAlreadyExistsException(
                "Un utilisateur avec cette adresse email existe déjà", 
                ErrorCodes.UTILISATEUR_ALREADY_EXISTS
            );
        }

        //On s'assure que l'entreprise existe
        entrepriseService.findById(dto.getEntreprise().getId());
        
        Set<String> strRoles = dto.getRoles();
        Set<Roles> roles = new HashSet<>();

        if (strRoles == null) {
            Roles userRole = roleRepository.findByRoleName(ERole.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun role avec le nom " + ERole.ROLE_USER + " n'a été trouvé dans la BDD", 
                        ErrorCodes.ROLE_NOT_FOUND));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                case "ADMIN":
                Roles adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun role avec le nom " + ERole.ROLE_ADMIN + " n'a été trouvé dans la BDD", 
                        ErrorCodes.ROLE_NOT_FOUND));
                roles.add(adminRole);
                break;

                case "MODERATEUR":
                Roles modRole = roleRepository.findByRoleName(ERole.ROLE_MODERATEUR)
                    .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun role avec le nom " + ERole.ROLE_MODERATEUR + " n'a été trouvé dans la BDD", 
                        ErrorCodes.ROLE_NOT_FOUND));
                roles.add(modRole);
                break;
                
                default:
                Roles userRole = roleRepository.findByRoleName(ERole.ROLE_USER)
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
    public void updatePassword(ChangerMotDePasse dto) {
        
        var violations = updateValidator.validate(dto);

        if(!violations.isEmpty()){
            log.error("Invalid datas {}", dto);
            throw new InvalidEntityException("Données invalides", ErrorCodes.UTILISATEUR_NOT_VALID, violations);
        }

        // Vérifier si le nouveau mot de passe et la confirmation sont identiques
        if (!dto.getNouveau().equals(dto.getConfirmation())) {
        log.error("Les mots de passe ne matchent pas");
        throw new InvalidEntityException(
            "Le nouveau mot de passe et la confirmation ne correspondent pas.", 
            ErrorCodes.PASSWORDS_NOT_MATCH
        );
        }

        // Récupérer l'identifiant de l'utilisateur actuel
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Vérifier l'ancien mot de passe
        UtilisateurDto utilisateur = this.findByEmail(email);
        if (!encoder.matches(dto.getAncien(), utilisateur.getMotDePasse())) {
            log.error("Mot de passe incorrect");
            throw new InvalidEntityException(
            "Ancien mot de passe incorect", 
            ErrorCodes.INCORRECT_PASSWORD
            );
        }

        // Changer le mot de passe
        utilisateur.setMotDePasse(encoder.encode(dto.getNouveau()));
        utilisateurRepository.save(UtilisateurDto.toEntity(utilisateur));
    }
}
