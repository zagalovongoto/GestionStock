package org.mambey.gestiondestock.dto;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.mambey.gestiondestock.model.Utilisateur;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UtilisateurDto {
    
    private Integer id;

    @NotBlank(message = "Veuillez renseigner le nom de l'utilisateur")
    private String nom;

    @NotBlank(message = "Veuillez renseigner le prenom de l'utilisateur")
    private String prenom;

    @NotBlank(message = "Veuillez renseigner l'adresse email de l'utilisateur")
    private String email;

    private Instant dateNaissance;

    private String motDePasse;

    @NotNull
    private AdresseDto adresse;

    private String photo;

    private EntrepriseDto entreprise;

    @JsonIgnore
    private List<RolesDto> roles;

    public static UtilisateurDto fromEntity(Utilisateur utilisateur){

        if(utilisateur == null){
            return null;
        }

        return UtilisateurDto.builder()
            .id(utilisateur.getId())
            .nom(utilisateur.getNom())
            .prenom(utilisateur.getPrenom())
            .email(utilisateur.getEmail())
            .dateNaissance(utilisateur.getDateNaissance())
            .motDePasse(utilisateur.getMotDePasse())
            .adresse(AdresseDto.fromEntity(utilisateur.getAdresse()))
            .photo(utilisateur.getPhoto())
            .entreprise(EntrepriseDto.fromEntity(utilisateur.getEntreprise()))
            .roles(
                utilisateur.getRoles() != null ?
                utilisateur.getRoles().stream()
                .map(RolesDto::fromEntity)
                .collect(Collectors.toList()) : null
            )
            .build();
    }

    public static Utilisateur toEntity(UtilisateurDto utilisateurDto){

        if(utilisateurDto == null){
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurDto.getId());
        utilisateur.setNom(utilisateurDto.getNom());
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setDateNaissance(utilisateurDto.getDateNaissance());
        utilisateur.setMotDePasse(utilisateurDto.getMotDePasse());
        utilisateur.setAdresse(AdresseDto.toEntity(utilisateurDto.getAdresse()));
        utilisateur.setPhoto(utilisateurDto.getPhoto());
        utilisateur.setEntreprise(EntrepriseDto.toEntity(utilisateurDto.getEntreprise()));

        return utilisateur;
    }
}
