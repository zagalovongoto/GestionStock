package org.mambey.gestiondestock.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.mambey.gestiondestock.security.handlers.AuthEntryPointJwt;
import org.mambey.gestiondestock.security.handlers.CustomAccessDeniedHandler;
import org.mambey.gestiondestock.security.service.UserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity
//(securedEnabled = true,
//jsr250Enabled = true,
//prePostEnabled = true) // by default
public class WebSecurityConfig {
  
  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Autowired
  private CustomAccessDeniedHandler forbiddenHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }
  
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }
 
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler)
                                                    .accessDeniedHandler(forbiddenHandler)
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> 
          auth.antMatchers(
                    "/**/**/auth/authenticate",//end point pour s'authentifier et obtenir un token
                    "/**/**/entreprises/create",//Créer une entreprise et son user principal
                    "/**/**/utilisateurs/create",//ajouter un utilisateur
                    "/swagger-ui/**",//swagger-ui/index.html pour générer la documentation
                    "/v3/api-docs**/**",//renvoie le fichier json ///v3/api-docs.yaml télécharge le fichier yaml
                    "/initialize",
                    "/**/**/photos/**"
                  ).permitAll()
              .anyRequest().authenticated()
        );

    // fix H2 database console: Refused to display ' in a frame because it set 'X-Frame-Options' to 'deny'
    //http.headers(headers -> headers.frameOptions(frameOption -> frameOption.sameOrigin()));
    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)//Autoriser les requêtes preflight de CORS
        .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
  }

  @Bean
  public CorsFilter corsFilter() {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials(false);//Autorise les cookies
      //config.addAllowedOrigin("http://localhost:4200"); // Remplacez par l'origine de votre application Angular
      config.addAllowedOrigin("*");
      config.addAllowedHeader("*");
      config.addAllowedMethod("OPTIONS");
      config.addAllowedMethod("GET");
      config.addAllowedMethod("POST");
      config.addAllowedMethod("PUT");
      config.addAllowedMethod("PATCH");
      config.addAllowedMethod("DELETE");
      config.setMaxAge(3600L);

      source.registerCorsConfiguration("/**", config);
      return new CorsFilter((CorsConfigurationSource) source); // Cast vers CorsConfigurationSource
  }
}
