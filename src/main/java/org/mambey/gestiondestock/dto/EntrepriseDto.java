package org.mambey.gestiondestock.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.mambey.gestiondestock.model.Entreprise;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EntrepriseDto {
    
    private Integer id;

    @NotBlank(message = "Veuillez renseigner l'entreprise")
    private String nom;

    private String description;

    @NotNull(message = "Veuillez renseigner l'adresse")
    @Valid
    private AdresseDto adresse;

    @NotBlank(message = "Veuillez renseigner le code fiscal")
    private String codeFiscal;

    private String photo;

    @NotBlank(message = "Veuillez renseigner l'adresse email")
    @Email(message = "Email invalide")
    private String email;

    @NotBlank(message = "Veuillez renseigner le numero de telephone")
    private String numTel;

    @JsonIgnore
    private List<UtilisateurDto> utilisateurs;

    public static EntrepriseDto fromEntity(Entreprise entreprise){

        if(entreprise == null){
            return null;
        }

        return EntrepriseDto.builder()
            .id(entreprise.getId())
            .nom(entreprise.getNom())
            .description(entreprise.getDescription())
            .adresse(AdresseDto.fromEntity(entreprise.getAdresse()))
            .codeFiscal(entreprise.getCodeFiscal())
            .photo(entreprise.getPhoto())
            .email(entreprise.getEmail())
            .numTel(entreprise.getNumTel())
            .build();
    }

    public static Entreprise toEntity(EntrepriseDto entrepriseDto){

        if(entrepriseDto == null){
            return null;
        }

        Entreprise entreprise = new Entreprise();
        entreprise.setId(entrepriseDto.getId());
        entreprise.setNom(entrepriseDto.getNom());
        entreprise.setDescription(entrepriseDto.getDescription());
        entreprise.setAdresse(AdresseDto.toEntity(entrepriseDto.getAdresse()));
        entreprise.setCodeFiscal(entrepriseDto.getCodeFiscal());
        entreprise.setPhoto(entrepriseDto.getPhoto());
        entreprise.setEmail(entrepriseDto.getEmail());
        entreprise.setNumTel(entrepriseDto.getNumTel());

        return entreprise;
    }
}
