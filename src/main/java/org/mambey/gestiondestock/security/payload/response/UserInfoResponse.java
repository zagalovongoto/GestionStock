package org.mambey.gestiondestock.security.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    
    private Long id;

    private String username;

    private String email;

    private List<String> roles;

}
