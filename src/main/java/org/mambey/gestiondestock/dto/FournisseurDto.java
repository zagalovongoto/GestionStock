package org.mambey.gestiondestock.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.mambey.gestiondestock.model.Fournisseur;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FournisseurDto {
    
    private Integer id;

    @NotBlank(message = "Veuillez renseigner le nom du fournisseur")
    private String nom;

    @NotBlank(message = "Veuillez renseigner le prenom du client")
    private String prenom;

    @NotNull(message = "l'adresse ne doit pas être nulle")
    private AdresseDto adresse;

    private String photo;

    @NotBlank(message = "Le mail ne doit pas être nul")
    private String mail;

    @NotBlank(message = "Le numero de telephone ne doit pas être nul")
    private String numTel;

    @JsonIgnore
    private List<CommandeFournisseurDto> commandeFournisseurs;

    private Integer idEntreprise;

    public static FournisseurDto fromEntity(Fournisseur fournisseur){

        if(fournisseur == null){
            return null;
        }

        return FournisseurDto.builder()
            .id(fournisseur.getId())
            .nom(fournisseur.getNom())
            .prenom(fournisseur.getPrenom())
            .adresse(AdresseDto.fromEntity(fournisseur.getAdresse()))
            .photo(fournisseur.getPhoto())
            .mail(fournisseur.getMail())
            .numTel(fournisseur.getNumTel())
            .idEntreprise(fournisseur.getIdEntreprise())
            .build();
    } 

    public static Fournisseur toEntity(FournisseurDto fournisseurDto){

        if(fournisseurDto == null){
            return null;
        }

        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(fournisseurDto.getId());
        fournisseur.setNom(fournisseurDto.getNom());
        fournisseur.setPrenom(fournisseurDto.getPrenom());
        fournisseur.setAdresse(AdresseDto.toEntity(fournisseurDto.getAdresse()));
        fournisseur.setPhoto(fournisseurDto.getPhoto());
        fournisseur.setMail(fournisseurDto.getMail());
        fournisseur.setNumTel(fournisseurDto.getNumTel());
        fournisseur.setIdEntreprise(fournisseurDto.getIdEntreprise());

        return fournisseur;
    } 
}
