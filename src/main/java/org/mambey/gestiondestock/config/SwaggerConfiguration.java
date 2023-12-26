package org.mambey.gestiondestock.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
@Profile("dev")
public class SwaggerConfiguration {
    
  @Value("${gimuemoa.openapi.dev-url}")
  private String url;

  @Bean
  public OpenAPI myOpenAPI() {
    Server server = new Server();
    server.setUrl(url);
    server.setDescription("URL pour l'environnement de développement");

    Contact contact = new Contact();
    contact.setEmail("mambosag@gmail.com");
    contact.setName("AGBETI Mambey Edem");
    contact.setUrl("https://www.bezkoder.com");

    License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

    Info info = new Info()
        .title("API REST Gestion de stock")
        .version("1.0")
        .contact(contact)
        .description("Cette API expose des points de terminaison pour gérer les stocks").termsOfService("https://www.bezkoder.com/terms")
        .license(mitLicense);

    final String securitySchemeName = "bearerAuth";
    
    return new OpenAPI()
        .info(info)
        .servers(List.of(server))
        .addSecurityItem(new SecurityRequirement()
          .addList(securitySchemeName))
        .components(new Components()
          .addSecuritySchemes(securitySchemeName, new SecurityScheme()
            .name(securitySchemeName)
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")));
  }

}
