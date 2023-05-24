package org.mambey.gestiondestock.dto;

import javax.validation.constraints.NotBlank;

import org.mambey.gestiondestock.model.Adresse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdresseDto {
    
    @NotBlank(message = "Veuillez renseigner l'adresse 1")
    private String adresse1;

    private String adresse2;

    @NotBlank(message = "Veuillez renseigner la ville")
    private String ville;

    @NotBlank(message = "Veuillez renseigner le code postal")
    private String codePostale;

    @NotBlank(message = "Veuillez renseigner le pays")
    private String pays;

    public static AdresseDto fromEntity(Adresse adresse){

        if(adresse == null){
            return null;
        }

        return AdresseDto.builder()
            .adresse1(adresse.getAdresse1())
            .adresse2(adresse.getAdresse2())
            .codePostale(adresse.getCodePostale())
            .ville(adresse.getVille())
            .pays(adresse.getPays())
            .build();
    }

    public static Adresse toEntity(AdresseDto adresseDto){

        if(adresseDto == null){
            return null;
        }

        return Adresse.builder()
            .adresse1(adresseDto.getAdresse1())
            .adresse2(adresseDto.getAdresse2())
            .codePostale(adresseDto.getCodePostale())
            .ville(adresseDto.getVille())
            .pays(adresseDto.getPays())
            .build();
    }
}
