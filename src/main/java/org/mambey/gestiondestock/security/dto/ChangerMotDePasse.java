package org.mambey.gestiondestock.security.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangerMotDePasse {
    
    @NotBlank
    private String ancien;

    @NotBlank
    private String nouveau;

    @NotBlank
    private String confirmation;

}
