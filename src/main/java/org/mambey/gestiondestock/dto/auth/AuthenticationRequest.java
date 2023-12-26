package org.mambey.gestiondestock.dto.auth;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {
    
    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
