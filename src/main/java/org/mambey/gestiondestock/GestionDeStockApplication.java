package org.mambey.gestiondestock;

/* import org.mambey.gestiondestock.dto.AdresseDto;
import org.mambey.gestiondestock.dto.EntrepriseDto;
import org.mambey.gestiondestock.dto.RolesDto;
import org.mambey.gestiondestock.security.model.ERole; */
/* import org.mambey.gestiondestock.dto.AdresseDto;
import org.mambey.gestiondestock.dto.EntrepriseDto;
import org.mambey.gestiondestock.dto.RolesDto;
import org.mambey.gestiondestock.security.model.ERole; */
//import org.mambey.gestiondestock.services.EntrepriseService;
//import org.mambey.gestiondestock.services.RolesService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestionDeStockApplication implements CommandLineRunner{

	/* RolesService rolesService;
	EntrepriseService entrepriseService; */
	
	/* public GestionDeStockApplication(RolesService rolesService, EntrepriseService entrepriseService){
		this.rolesService = rolesService;
		this.entrepriseService = entrepriseService;
	} */
	public static void main(String[] args) {
		SpringApplication.run(GestionDeStockApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		/* RolesDto role1 = RolesDto.builder()
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

		entrepriseService.save(entrepriseDto); */

	}
	
}
