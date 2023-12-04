package org.mambey.gestiondestock.security.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.mambey.gestiondestock.model.Utilisateur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails{

    private Integer id;

    private String username;

    @JsonIgnore
    private String password;

    private Integer idEntreprise;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Integer id, String username, String password, Integer idEntreprise,
      Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.idEntreprise = idEntreprise;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Utilisateur user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
            .collect(Collectors.toList());

        return new UserDetailsImpl(
            user.getId(), 
            user.getEmail(),//nous utilisons l'email en lieu et place du username
            user.getMotDePasse(),
            user.getEntreprise().getId(), 
            authorities);
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Integer getIdEntreprise() {
        return idEntreprise;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
