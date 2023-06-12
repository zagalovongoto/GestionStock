package org.mambey.gestiondestock.dto;

import java.time.Instant;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.mambey.gestiondestock.model.CommandeClient;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandeClientDto {
    
    private Integer id;

    @NotBlank(message = "Veuillez remplir le code commande")
    private String code;

    @NotNull(message = "Veuillez renseigner la date de commande")
    @JsonFormat( shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Instant date;
    
    @NotNull(message = "Veuillez renseigner le client")
    private ClientDto client;

    //@JsonIgnore
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
            .client(ClientDto.fromEntity(commandeClient.getClient()))
            .idEntreprise(commandeClient.getIdEntreprise())
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
        commandeClient.setIdEntreprise(commandeClientDto.getIdEntreprise());
        
        return commandeClient;

    }
}
