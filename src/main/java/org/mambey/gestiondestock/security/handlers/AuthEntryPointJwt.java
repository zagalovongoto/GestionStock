package org.mambey.gestiondestock.security.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

//   @Override
//   public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
//       throws IOException, ServletException {
//     logger.error("Unauthorized error: {}", authException.getMessage());
//     response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
//   }


//Cette classe gère les erreurs d'authentification
//Elle permet de rediriger ou de renoyer une réponse en cas de requête sans authentification
@Override
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
    throws IOException, ServletException {
    logger.error("Unauthorized error: {}", authException.getMessage());

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    /* final ErrorDto errorDto = ErrorDto.builder()
      .code(exception.getErrorCode())
      .httpCode(badRequest.value())
      .message(exception.getMessage())
      .build(); */

    final Map<String, Object> body = new HashMap<>();
    body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
    body.put("error", "Unauthorized");
    body.put("message", authException.getMessage());
    body.put("path", request.getServletPath());

    final ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(response.getOutputStream(), body);
  }

}
