package org.mambey.gestiondestock.security.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mambey.gestiondestock.security.model.UserDetailsImpl;
import org.mambey.gestiondestock.services.UtilisateurService;
import org.mambey.gestiondestock.security.dto.ChangerMotDePasse;
import org.mambey.gestiondestock.security.dto.LoginRequest;
import org.mambey.gestiondestock.security.dto.LoginResponse;
import org.mambey.gestiondestock.security.dto.MessageResponse;
import org.mambey.gestiondestock.security.jwt.JwtUtils;
import static org.mambey.gestiondestock.utils.Constants.APP_ROOT;

@RestController
@RequiredArgsConstructor
@RequestMapping(APP_ROOT)
@Tag(name = "authenticationApi")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UtilisateurService utilisateurService;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping(
    value = "/auth/authenticate", 
    consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String token = jwtUtils.generateToken(userDetails);

    LoginResponse loginResponse = LoginResponse.builder()
        .accessToken(token)
        .build();

    return ResponseEntity.status(HttpStatus.CREATED)
                         .body(loginResponse);
  }

  @PostMapping(
    value = "/auth/changepwd", 
    consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<MessageResponse> changePwd(@RequestBody ChangerMotDePasse dto){

    utilisateurService.updatePassword(dto);

    return ResponseEntity.status(HttpStatus.OK)
                         .body(new MessageResponse("Mot de passe modifié avec succès"));
  }
}
