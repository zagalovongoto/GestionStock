package org.mambey.gestiondestock.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.mambey.gestiondestock.model.Utilisateur;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    @Size(max = 50, message = "Email trop long")
    private String email;

    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message="Format de date invalide")
    private String dateNaissance;

    @NotBlank(message = "Veuillez renseigner l'adresse email de l'utilisateur")
    @Size(min = 8, message = "Mot de passe trop court")
    //@Size(max = 50, message = "Mot de passe trop long")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String motDePasse;

    @NotNull
    @Valid
    private AdresseDto adresse;

    private String photo;

    @NotNull
    private EntrepriseDto entreprise;

    private Set<String> roles;

    public static UtilisateurDto fromEntity(Utilisateur utilisateur){

        if(utilisateur == null){
            return null;
        }

        return UtilisateurDto.builder()
            .id(utilisateur.getId())
            .nom(utilisateur.getNom())
            .prenom(utilisateur.getPrenom())
            .email(utilisateur.getEmail())
            .dateNaissance(utilisateur.getDateNaissance().toString())
            .motDePasse(utilisateur.getMotDePasse())
            .adresse(AdresseDto.fromEntity(utilisateur.getAdresse()))
            .photo(utilisateur.getPhoto())
            .entreprise(EntrepriseDto.fromEntity(utilisateur.getEntreprise()))
            .roles(
                utilisateur.getRoles() != null ?
                utilisateur.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toSet()) : null
            )
            .build();
    }

    public static Utilisateur toEntity(UtilisateurDto utilisateurDto){

        if(utilisateurDto == null){
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurDto.getId());
        utilisateur.setNom(utilisateurDto.getNom());
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setDateNaissance(LocalDate.parse(utilisateurDto.getDateNaissance(), formatter));
        utilisateur.setMotDePasse(utilisateurDto.getMotDePasse());
        utilisateur.setAdresse(AdresseDto.toEntity(utilisateurDto.getAdresse()));
        utilisateur.setPhoto(utilisateurDto.getPhoto());
        utilisateur.setEntreprise(EntrepriseDto.toEntity(utilisateurDto.getEntreprise()));
        /*utilisateur.setRoles(utilisateurDto.getRoles() != null ?
                utilisateurDto.getRoles().stream()
                .map(RolesDto::toEntity)
                .collect(Collectors.toSet()) : null);*/

        return utilisateur;
    }
}
