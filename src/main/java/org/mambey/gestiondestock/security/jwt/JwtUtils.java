package org.mambey.gestiondestock.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
//import org.mambey.gestiondestock.security.model.ExtendedUser;
import org.mambey.gestiondestock.security.model.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${edem.app.jwtSecret}")
  private String jwtSecret;

  @Value("${edem.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  @Value("${edem.app.jwtHeaderName}")
  private String jwtCookie;

  public String getJwtFromAuthHeader(HttpServletRequest request) {
    final String headerValue = request.getHeader("Authorization");
    String jwt = null;

    if(headerValue != null && headerValue.startsWith("Bearer ")){
      jwt = headerValue.substring(7);
    }

    if (jwt != null) {
      return jwt;
    } else {
      return null;
    }
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key()).build()
               .parseClaimsJws(token).getBody().getSubject();
  }

  public String getIdEntrepriseFromJwtToken(String token) {
         final Claims claims = extractAllClaims(token);
        return claims.get("idEntreprise", String.class);
  }

  private Claims extractAllClaims(String token){
      return Jwts.parserBuilder().setSigningKey(key()).build()
               .parseClaimsJws(token).getBody();
  }

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
      //Jwts.parser().setSigningKey(key()).parseClaimsJws(authToken).getBody();
      return true;

    } catch (SignatureException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    } catch (Exception e) {//J'ai ajouté ceci pour toutes les autres erreurs
      logger.error(e.getMessage());
    }

    return false;
  }

  public String generateTokenFromUsername(String username) {   
    return Jwts.builder()
               .setSubject(username)
               .setIssuedAt(new Date())
               .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
               .signWith(key(), SignatureAlgorithm.HS256)
               .compact();
  }

  public String generateToken(UserDetailsImpl userPrincipal) {
     
    return Jwts.builder()
               .setSubject(userPrincipal.getUsername())
               .claim("idEntreprise", userPrincipal.getIdEntreprise().toString())
               .claim("scope", userPrincipal.getAuthorities().stream().map(
                    auth -> auth.getAuthority()
                  ).collect(Collectors.toList())
               )
               .setIssuedAt(new Date())
               .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
               .signWith(key(), SignatureAlgorithm.HS256)
               .compact();
  }
}

