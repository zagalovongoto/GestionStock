package org.mambey.gestiondestock.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChangerMotDePasseUtilisateurDto {
    
    private Integer id;

    @NotBlank
    private String motDePasse;

    @NotBlank
    private String confirmMotDePasse;
}
