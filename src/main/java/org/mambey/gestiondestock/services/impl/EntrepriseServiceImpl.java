package org.mambey.gestiondestock.services.impl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mambey.gestiondestock.dto.EntrepriseDto;
import org.mambey.gestiondestock.dto.UtilisateurDto;
import org.mambey.gestiondestock.exception.EntityNotFoundException;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvalidEntityException;
import org.mambey.gestiondestock.model.Entreprise;
import org.mambey.gestiondestock.model.Roles;
import org.mambey.gestiondestock.model.Utilisateur;
import org.mambey.gestiondestock.repository.EntrepriseRepository;
import org.mambey.gestiondestock.repository.RolesRepository;
import org.mambey.gestiondestock.repository.UtilisateurRepository;
import org.mambey.gestiondestock.services.EntrepriseService;
import org.mambey.gestiondestock.services.ObjectsValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EntrepriseServiceImpl implements EntrepriseService{

    private final EntrepriseRepository entrepriseRepository;
    private final RolesRepository rolesRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder encoder;

    private final ObjectsValidator<EntrepriseDto> entrepriseValidator;

    @Value("${defaultUserPassword}")
    private String defaultPassword;

    @Override
    public EntrepriseDto save(EntrepriseDto dto) {

        var violations = entrepriseValidator.validate(dto);

        if(!violations.isEmpty()){
            log.error("L'entreprise n'est pas valide {}", dto);
            throw new InvalidEntityException("Données invalides", ErrorCodes.ENTREPRISE_NOT_VALID, violations);
        }


        EntrepriseDto savedEntreprise = EntrepriseDto.fromEntity(
            entrepriseRepository.save(EntrepriseDto.toEntity(dto))
        );

        UtilisateurDto utilisateurDto = fromEntreprise(savedEntreprise);
        /* Roles userRole = rolesRepository.findByRoleName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun role avec le nom " + ERole.ROLE_ADMIN + " n'a été trouvé dans la BDD", 
                        ErrorCodes.ROLE_NOT_FOUND)); */
        List<Roles> userRoles = rolesRepository.findAll();
        Set<Roles> roles = new HashSet<>();
        userRoles.forEach(role -> {
            roles.add(role);
        });
        //roles.add(userRole);
        Utilisateur utilisateur = UtilisateurDto.toEntity(utilisateurDto);
        utilisateur.setRoles(roles);

        utilisateurRepository.save(utilisateur);

        return savedEntreprise;
    }

    @Override
    public EntrepriseDto findById(Integer id) {
        
        if(id == null){
            log.error("Entreprise ID is null");
            return null;
        }

        Optional<Entreprise> entreprise = entrepriseRepository.findById(id);

        return entreprise.map(EntrepriseDto::fromEntity)
                      .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune entreprise avec l'ID " + id + " n'a été trouvée dans la BDD", 
                        ErrorCodes.ENTREPRISE_NOT_FOUND));

    }

    @Override
    public List<EntrepriseDto> findAll() {
        return entrepriseRepository.findAll().stream()
                                .map(EntrepriseDto::fromEntity)
                                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Entreprise ID is null");
        }

        entrepriseRepository.deleteById(id);
    }
    
    private UtilisateurDto fromEntreprise(EntrepriseDto dto){
        
        return UtilisateurDto.builder()
            .adresse(dto.getAdresse())
            .nom(dto.getNom())
            .prenom(dto.getCodeFiscal())
            .email(dto.getEmail())
            .motDePasse(encoder.encode(defaultPassword))
            .entreprise(dto)
            .dateNaissance(LocalDate.now().toString())
            .photo(dto.getPhoto())
            .entreprise(dto)
            .build();

    }

    /* private String generateRandomPassword(){
        return "Zagzagzag";
    } */

}
