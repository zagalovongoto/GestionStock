package org.mambey.gestiondestock.dto;

import java.time.Instant;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.mambey.gestiondestock.model.CommandeFournisseur;
import org.mambey.gestiondestock.model.EtatCommande;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandeFournisseurDto {
    
    private Integer id;

    @NotBlank(message = "Veuillez remplir le code commande")
    private String code;

    //@NotNull(message = "Veuillez renseigner la date de commande")
    private Instant dateCommande;

    @NotNull(message = "Veuillez renseigner l'état de la commande")
    private EtatCommande etatCommade;

    private FournisseurDto fournisseur;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<LigneCommandeFournisseurDto> ligneCommandeFournisseurs;

    private Integer idEntreprise;

    public static CommandeFournisseurDto fromEntity(CommandeFournisseur commandeFournisseur){

        if(commandeFournisseur == null){
            return null;
        }

        return CommandeFournisseurDto.builder()
            .id(commandeFournisseur.getId())
            .code(commandeFournisseur.getCode())
            .dateCommande(commandeFournisseur.getDateCommande())
            .etatCommade(commandeFournisseur.getEtatCommade())
            .fournisseur(FournisseurDto.fromEntity(commandeFournisseur.getFournisseur()))
            .idEntreprise(commandeFournisseur.getIdEntreprise())
            .build();

    }

    public static CommandeFournisseur toEntity(CommandeFournisseurDto commandeFournisseurDto){

        if(commandeFournisseurDto == null){
            return null;
        }

        CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
        commandeFournisseur.setId(commandeFournisseurDto.getId());
        commandeFournisseur.setCode(commandeFournisseurDto.getCode());
        commandeFournisseur.setDateCommande(commandeFournisseurDto.getDateCommande());
        commandeFournisseur.setEtatCommade(commandeFournisseurDto.getEtatCommade());
        commandeFournisseur.setFournisseur(FournisseurDto.toEntity(commandeFournisseurDto.getFournisseur()));
        commandeFournisseur.setIdEntreprise(commandeFournisseurDto.getIdEntreprise());
        
        return commandeFournisseur;

    }

    public Boolean isCommandeLivree(){
        return EtatCommande.LIVREE.equals(this.etatCommade);
    }
}
