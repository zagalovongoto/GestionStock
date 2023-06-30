package org.mambey.gestiondestock.dto;

import java.time.Instant;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.mambey.gestiondestock.model.CommandeFournisseur;
import org.mambey.gestiondestock.model.EtatCommande;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandeFournisseurDto {
    
    private Integer id;

    @NotBlank(message = "Veuillez remplir le code commande")
    private String code;

    @NotNull(message = "Veuillez renseigner la date de commande")
    @JsonFormat( shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Instant dateCommande;

    @NotNull(message = "Veuillez renseigner l'état de la commande")
    private EtatCommande etatCommade;

    private FournisseurDto fournisseur;

    @JsonIgnore
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
        commandeFournisseur.setIdEntreprise(commandeFournisseurDto.getIdEntreprise());
        
        return commandeFournisseur;

    }

    public Boolean isCommandeLivree(){
        return EtatCommande.LIVREE.equals(this.etatCommade);
    }
}
