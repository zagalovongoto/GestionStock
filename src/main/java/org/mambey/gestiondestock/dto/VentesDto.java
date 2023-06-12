package org.mambey.gestiondestock.dto;

import java.time.Instant;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.mambey.gestiondestock.model.Ventes;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VentesDto {
    
    private Integer id;

    @NotBlank(message = "Veuillez renseigner le code vente")
    private String code;

    @NotNull(message = "Veuillez renseigner la date de la vente")
    private Instant dateVente;

    private String commentaire;

    private Integer idEntreprise;

    @NotNull(message = "Veuillez renseigner les lignes de vente")
    private List<LigneVenteDto> ligneVentes;

    public static VentesDto fromEntity(Ventes ventes){

        if(ventes == null){
            return null;
        }

        return VentesDto.builder()
            .id(ventes.getId())
            .code(ventes.getCode())
            .dateVente(ventes.getDateVente())
            .commentaire(ventes.getCommentaire())
            .idEntreprise(ventes.getIdEntreprise())
            .build();
    }

    public static Ventes toEntity(VentesDto ventesDto){

        if(ventesDto == null){
            return null;
        }

        Ventes ventes = new Ventes();
        ventes.setId(ventesDto.getId());
        ventes.setCode(ventesDto.getCode());
        ventes.setDateVente(ventesDto.getDateVente());
        ventes.setCommentaire(ventesDto.getCommentaire());
        ventes.setIdEntreprise(ventesDto.getIdEntreprise());

        return ventes;
    }
}
