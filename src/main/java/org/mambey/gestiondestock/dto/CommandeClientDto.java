package org.mambey.gestiondestock.dto;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.mambey.gestiondestock.model.CommandeClient;
import org.mambey.gestiondestock.model.EtatCommande;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandeClientDto {
    
    private Integer id;

    @NotBlank(message = "Veuillez remplir le code commande")
    private String code;

    //@NotNull(message = "Veuillez renseigner la date de commande")
    private Instant date;

    @NotNull(message = "Veuillez renseigner l'état de la commande")
    private EtatCommande etatCommade;
    
    @NotNull(message = "Veuillez renseigner le client")
    private ClientDto client;

    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<LigneCommandeClientDto> ligneCommandeClients;

    private Integer idEntreprise;

    public static CommandeClientDto fromEntity(CommandeClient commandeClient){

        if(commandeClient == null){
            return null;
        }
        
        return CommandeClientDto.builder()
            .id(commandeClient.getId())
            .code(commandeClient.getCode())
            .date(commandeClient.getDate())
            .etatCommade(commandeClient.getEtatCommade())
            .client(ClientDto.fromEntity(commandeClient.getClient()))
            .idEntreprise(commandeClient.getIdEntreprise())
            .ligneCommandeClients(
                    commandeClient.getLigneCommandeClients() != null ?
                            commandeClient.getLigneCommandeClients().stream()
                                    .map(LigneCommandeClientDto::fromEntity)
                                    .collect(Collectors.toList()) : null
            )
            .build();

    }

    public static CommandeClient toEntity(CommandeClientDto commandeClientDto){

        if(commandeClientDto == null){
            return null;
        }

        CommandeClient commandeClient = new CommandeClient();
        commandeClient.setId(commandeClientDto.getId());
        commandeClient.setCode(commandeClientDto.getCode());
        commandeClient.setDate(commandeClientDto.getDate());
        commandeClient.setEtatCommade(commandeClientDto.getEtatCommade());
        commandeClient.setClient(ClientDto.toEntity(commandeClientDto.getClient()));
        commandeClient.setIdEntreprise(commandeClientDto.getIdEntreprise());
        
        return commandeClient;

    }

    public Boolean isCommandeLivree(){
        return EtatCommande.LIVREE.equals(this.etatCommade);
    }
}
