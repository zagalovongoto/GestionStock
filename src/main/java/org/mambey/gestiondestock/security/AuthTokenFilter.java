package org.mambey.gestiondestock.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.mambey.gestiondestock.security.jwt.JwtUtils;
import org.mambey.gestiondestock.security.service.UserDetailsServiceImpl;

import io.jsonwebtoken.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtils jwtUtils;
  
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
  
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
  
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException, java.io.IOException {

      String idEntreprise = null;
      String jwt = jwtUtils.getJwtFromAuthHeader(request);

      try {
        if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
          idEntreprise = jwtUtils.getIdEntrepriseFromJwtToken(jwt);
          String username = jwtUtils.getUserNameFromJwtToken(jwt);
  
          UserDetails userDetails = userDetailsService.loadUserByUsername(username);
          
          UsernamePasswordAuthenticationToken authentication = 
              new UsernamePasswordAuthenticationToken(userDetails,
                                                      null,
                                                      userDetails.getAuthorities());
          
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
  
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      } catch (Exception e) {
        logger.error("Cannot set user authentication: {}", e);
      }
  
      MDC.put("idEntreprise", idEntreprise);//
      filterChain.doFilter(request, response);
    }
}
