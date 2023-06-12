package org.mambey.gestiondestock.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class SwaggerConfiguration {
    
  @Value("${gimuemoa.openapi.dev-url}")
  private String devUrl;

  @Value("${gimuemoa.openapi.prod-url}")
  private String prodUrl;

  @Bean
  public OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("URL pour l'environnement de développement");

    Server prodServer = new Server();
    prodServer.setUrl(prodUrl);
    prodServer.setDescription("URL pour l'environnement de production");

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

    return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
  }

}
