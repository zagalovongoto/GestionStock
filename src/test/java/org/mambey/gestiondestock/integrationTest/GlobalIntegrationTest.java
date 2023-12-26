package org.mambey.gestiondestock.integrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mambey.gestiondestock.dto.RolesDto;
import org.mambey.gestiondestock.dto.UtilisateurDto;
import org.mambey.gestiondestock.security.model.ERole;
import org.mambey.gestiondestock.services.RolesService;
import org.mambey.gestiondestock.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayName("GLOBAL INTEGRATION TEST")
@Slf4j
public class GlobalIntegrationTest {

    @Autowired private TestRestTemplate restTemplate;
    @Autowired RolesService rolesService;
    @Autowired UtilisateurService utilisateurService;
    @LocalServerPort int randomServerPort;

    private final int idEntreprise = 1;
    private final String email = "gimuemoa@gim-uemoa.org";
    @Value("${defaultUserPassword}") private String password;
    private UtilisateurDto[] userArrays;

    private String getBaseUrl(){
        return "http://localhost:" + randomServerPort + "/gestiondestock/v1";
    }

    //private static final Logger logger = LoggerFactory.getLogger(GlobalIntegrationTest.class);

    @Test
    public void testApplication() throws URISyntaxException, JsonMappingException, JsonProcessingException, JSONException{
        
        //1- Ajout des roles*******************************************************
        for (RolesDto role : listRoles()) {
            rolesService.save(role);
        }
        
        //2- Ajout d'une entreprise et de son user*********************************
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<String>(entrepriseJson().toString(), headers);

        String url = getBaseUrl() + "/entreprises/create";
        URI uri = new URI(url);

        ResponseEntity<String> createdEntrepriseRequestEntity = restTemplate.postForEntity(uri, request, String.class);
        String jsonBody = createdEntrepriseRequestEntity.getBody();
        JsonNode createEntrepriseResponseJson = new ObjectMapper().readTree(jsonBody);

        // Assert
        assertEquals(HttpStatus.CREATED, createdEntrepriseRequestEntity.getStatusCode());
        //On vérifie si l'email envoyé est celle retournée
        assertEquals(entrepriseJson().get("email"),
        createEntrepriseResponseJson.path("email").asText(),
        "Returned enterprise's email seems to be incorrect");

        //3- On récupère l'utilisateur créé
        UtilisateurDto user = utilisateurService.findByEmail(email);
        assertThat(user).isNotNull();

        //4- Connexion en vue de génération de token ********************************
        url = getBaseUrl() + "/auth/authenticate";
        uri = new URI(url);
        request = new HttpEntity<String>(loginJson().toString(), headers);
        ResponseEntity<String> createdTokenRequestEntity = restTemplate.postForEntity(uri, request, String.class);
        jsonBody = createdTokenRequestEntity.getBody();
        JsonNode createTokenResponseJson = new ObjectMapper().readTree(jsonBody);
        String accessToken = createTokenResponseJson.path("accessToken").asText();
        assertThat(accessToken).isNotNull();
        log.info(accessToken);

        //5- On récupère la liste des utilisateurs ********************************
        url = getBaseUrl() + "/utilisateurs/all";
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<UtilisateurDto[]> getUsersRequestEntity = restTemplate.exchange(url, HttpMethod.GET, entity, UtilisateurDto[].class);
        userArrays = getUsersRequestEntity.getBody();
        assertThat(userArrays.length).isNotEqualTo(0);
        assertThat(userArrays[0].getNom()).isEqualTo("GIM-UEMOA");

        //TODO: Ajouter des catégories

        //TODO: Ajouter des articles

        //Ajouter un client

        //TODO: Ajouter une commande client

        //TODO: Ajouter une commande fournisseur

    }
    
    private List<RolesDto> listRoles(){
        RolesDto role1 = RolesDto.builder()
                                 .roleName(ERole.ROLE_ADMIN)
                                 .idEntreprise(idEntreprise)
                            .build();
        RolesDto role2 = RolesDto.builder()
                                 .roleName(ERole.ROLE_MODERATEUR)
                                 .idEntreprise(idEntreprise)
                            .build();
        RolesDto role3 = RolesDto.builder()
                                 .roleName(ERole.ROLE_USER)
                                 .idEntreprise(idEntreprise)
                            .build();

        return List.of(role1, role2, role3);
    }

    private JSONObject entrepriseJson() throws JSONException{

        JSONObject adresseJson = new JSONObject();
        adresseJson.put("adresse1","Ouest Foire");
        adresseJson.put("adresse2","Almadies");
        adresseJson.put("pays","SENEGAL");
        adresseJson.put("ville","DAKAR");
        adresseJson.put("codePostale","BP8859");

        JSONObject entrepriseJson = new JSONObject();
        entrepriseJson.put("nom","GIM-UEMOA");
        entrepriseJson.put("numTel","+221338959988");
        entrepriseJson.put("codeFiscal","12345678");
        entrepriseJson.put("email",email);
        entrepriseJson.put("description", "Entreprise d'intermédiation");
        entrepriseJson.put("adresse", adresseJson);

        return entrepriseJson;
    }

    private JSONObject loginJson() throws JSONException{
        JSONObject loginJson = new JSONObject();
        loginJson.put("username", email);
        loginJson.put("password", password);

        return loginJson;
    }
    
}
