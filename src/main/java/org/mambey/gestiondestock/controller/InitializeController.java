package org.mambey.gestiondestock.controller;

import java.util.List;

import org.mambey.gestiondestock.dto.AdresseDto;
import org.mambey.gestiondestock.dto.EntrepriseDto;
import org.mambey.gestiondestock.dto.RolesDto;
import org.mambey.gestiondestock.security.model.ERole;
import org.mambey.gestiondestock.services.EntrepriseService;
import org.mambey.gestiondestock.services.RolesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitializeController {

    RolesService rolesService;
	EntrepriseService entrepriseService;
	
	public InitializeController(RolesService rolesService, EntrepriseService entrepriseService){
		this.rolesService = rolesService;
		this.entrepriseService = entrepriseService;
	}
    
    @GetMapping("/initialize")
	public String initialize(){

        List<RolesDto> roleListe = rolesService.findAll();
        if (roleListe != null && !roleListe.isEmpty()) {
             return "OK";
        }


		RolesDto role1 = RolesDto.builder()
                                 .roleName(ERole.ROLE_ADMIN)
                                 .idEntreprise(1)
                            .build();
        RolesDto role2 = RolesDto.builder()
                                 .roleName(ERole.ROLE_MODERATEUR)
                                 .idEntreprise(1)
                            .build();
        RolesDto role3 = RolesDto.builder()
                                 .roleName(ERole.ROLE_USER)
                                 .idEntreprise(1)
                            .build();

        rolesService.save(role1);
        rolesService.save(role2);
        rolesService.save(role3);

		AdresseDto adresseDto = AdresseDto.builder()
										  .adresse1("Ouest Foire")
										  .adresse2("Almadies")
										  .pays("SENEGAL")
										  .ville("DAKAR")
										  .codePostale("BP12345")
									.build();
		EntrepriseDto entrepriseDto = EntrepriseDto.builder()
												   .nom("GIM-UEMOA")
												   .numTel("+221338959988")
												   .codeFiscal("12345678")
												   .email("gimuemoa3@gim-uemoa.org")
												   .description("Entreprise d'intermédiation")
												   .adresse(adresseDto)
												.build();

		entrepriseService.save(entrepriseDto);

        return "OK";
	}
}
