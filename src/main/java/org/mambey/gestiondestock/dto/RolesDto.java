package org.mambey.gestiondestock.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.mambey.gestiondestock.model.Roles;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RolesDto {
    
    private Integer id;

    @NotBlank(message = "Veuillez renseigner le role")
    private String roleName;

    @NotNull
    private UtilisateurDto utilisateur;

    private Integer idEntreprise;

    public static RolesDto fromEntity(Roles roles){

        if(roles == null){
            return null;
        }

        return RolesDto.builder()
            .id(roles.getId())
            .roleName(roles.getRoleName())
            //.utilisateur(UtilisateurDto.fromEntity(roles.getUtilisateur()))
            .idEntreprise(roles.getIdEntreprise())
            .build();
    }

    public static Roles toEntity(RolesDto rolesDto){

        if(rolesDto == null){
            return null;
        }

        Roles roles = new Roles();
        roles.setId(rolesDto.getId());
        roles.setRoleName(rolesDto.getRoleName());
        //roles.setUtilisateur(UtilisateurDto.toEntity(rolesDto.getUtilisateur()));
        roles.setIdEntreprise(rolesDto.getIdEntreprise());

        return roles;
    }
}
