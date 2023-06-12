package org.mambey.gestiondestock.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfiguration {

    private PasswordEncoder passwordEncoder;
    
    public SecurityConfiguration(PasswordEncoder encoder){
        this.passwordEncoder = encoder;
    }

    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService){

        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        return http.csrf( csrf -> csrf.disable())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(auth -> auth.antMatchers("/**/**/auth/authenticate").permitAll())
                    .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                    .build();
    }
}
