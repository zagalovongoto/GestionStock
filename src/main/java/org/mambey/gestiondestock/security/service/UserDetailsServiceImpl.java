package org.mambey.gestiondestock.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import org.mambey.gestiondestock.model.Utilisateur;
import org.mambey.gestiondestock.repository.UtilisateurRepository;
import org.mambey.gestiondestock.security.model.ExtendedUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
  @Autowired
  UtilisateurRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Utilisateur user = userRepository.findUtilisateurByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + email));
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));

    return new ExtendedUser(user.getEmail(), user.getMotDePasse(), user.getEntreprise().getId(), authorities);
  }
}
