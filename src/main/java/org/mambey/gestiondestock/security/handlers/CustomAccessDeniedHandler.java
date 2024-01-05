package org.mambey.gestiondestock.security.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.handlers.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@ResponseStatus(HttpStatus.FORBIDDEN)
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
        logger.error("Forbidden error: {}", accessDeniedException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    
        /* final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_FORBIDDEN);
        body.put("error", "Forbidden");
        body.put("message", accessDeniedException.getMessage());
        body.put("path", request.getServletPath()); */

        final ErrorDto body = ErrorDto.builder()
          .code(ErrorCodes.FORBIDDEN)
          .httpCode(HttpServletResponse.SC_FORBIDDEN)
          .message(accessDeniedException.getMessage())
          .errors(null)
          .build();
    
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
  }

}
