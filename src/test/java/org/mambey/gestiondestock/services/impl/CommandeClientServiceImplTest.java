package org.mambey.gestiondestock.services.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mambey.gestiondestock.dto.ArticleDto;
import org.mambey.gestiondestock.dto.ClientDto;
import org.mambey.gestiondestock.dto.CommandeClientDto;
import org.mambey.gestiondestock.dto.LigneCommandeClientDto;
import org.mambey.gestiondestock.exception.InvalidEntityException;
import org.mambey.gestiondestock.model.Article;
import org.mambey.gestiondestock.model.Client;
import org.mambey.gestiondestock.model.CommandeClient;
import org.mambey.gestiondestock.model.EtatCommande;
import org.mambey.gestiondestock.repository.ArticleRepository;
import org.mambey.gestiondestock.repository.ClientRepository;
import org.mambey.gestiondestock.repository.CommandeClientRepository;
import org.mambey.gestiondestock.repository.LigneCommandeClientRepository;
import org.mambey.gestiondestock.services.CommandeClientService;
import org.mambey.gestiondestock.services.MvtStkService;
import org.mambey.gestiondestock.services.ObjectsValidator;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandeClientServiceImplTest {

    @Mock CommandeClientRepository commandeClientRepository;
    @Mock ClientRepository clientRepository;
    @Mock ArticleRepository articleRepository;
    @Mock LigneCommandeClientRepository ligneCommandeClientRepository;
    @Mock MvtStkService mvtStkService;

    CommandeClientService commandeClientService;
    CommandeClientDto commandeClientDto;
    Article article1;
    Article article2;
    Client client;

    @BeforeEach
    void setup(){
        ObjectsValidator<CommandeClientDto> commandeClientValidator = new ObjectsValidator<CommandeClientDto>();
        CommandeClientService commandeClientService = new CommandeClientServiceImpl(commandeClientRepository, 
        clientRepository, articleRepository, ligneCommandeClientRepository, mvtStkService, commandeClientValidator);
        this.commandeClientService = commandeClientService;

        ClientDto clientDto = ClientDto.builder()
        .id(1)
        .build();
        client = ClientDto.toEntity(clientDto);

        ArticleDto articleDto1 = ArticleDto.builder()
        .id(1)
        .build();
        ArticleDto articleDto2 = ArticleDto.builder()
        .id(1)
        .build();
        article1 = ArticleDto.toEntity(articleDto1); article2 = ArticleDto.toEntity(articleDto2);

        LigneCommandeClientDto ligneCommandeClientDto1 = LigneCommandeClientDto.builder()
        .article(articleDto1)
        .quantite(BigDecimal.valueOf(3.5))
        .prixUnitaire(BigDecimal.valueOf(1200))
        .build();

        LigneCommandeClientDto ligneCommandeClientDto2 = LigneCommandeClientDto.builder()
        .article(articleDto2)
        .quantite(BigDecimal.valueOf(10))
        .prixUnitaire(BigDecimal.valueOf(500))
        .build();

        List<LigneCommandeClientDto> liste = List.of(ligneCommandeClientDto1, ligneCommandeClientDto2);

        CommandeClientDto commandeClientDto = CommandeClientDto.builder()
        .code("CMD01")
        .etatCommade(EtatCommande.VALIDEE)
        .client(clientDto)
        .date(Instant.now())
        .ligneCommandeClients(liste)
        .idEntreprise(1)
        .build();

        this.commandeClientDto = commandeClientDto;

        MDC.put("idEntreprise", "1");
    }

    @Test
    @DisplayName("SAVE ARTICLE")
    void testSave() {

        //Given
        when(articleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(article1));
        when(articleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(article2));
        when(clientRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(client));
        CommandeClient cmdClt = CommandeClientDto.toEntity(commandeClientDto);
        cmdClt.setId(Integer.valueOf(1));

        // Configurez le comportement du mock en utilisant ArgumentMatchers
        when(commandeClientRepository.save(ArgumentMatchers.any(CommandeClient.class))
        ).thenReturn(cmdClt);

        //When
        CommandeClientDto savedCmdClt = this.commandeClientService.save(commandeClientDto);

        //On vérifie que la méthode articleRepository.findById() a été appelée 2 fois 
        verify(articleRepository, times(2)).findById(ArgumentMatchers.anyInt());
        
        //Then
        assertThat(savedCmdClt).isNotNull();

    }

    @Test
    @DisplayName("SAVE COMMANDE WITH NULL ETAT COMMANDE")
    void testSaveWithNullEtatCommande() {

        //Given
        CommandeClientDto cmdCltDto = this.commandeClientDto;
        cmdCltDto.setEtatCommade(null);

        //Then
        assertThatThrownBy(() -> commandeClientService.save(cmdCltDto))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("Données invalides");

    }

    @Test
    @Disabled
    void testDelete() {

    }

    @Test
    @Disabled
    void testDeleteArticle() {

    }

    @Test
    @Disabled
    void testFindAll() {

    }

    @Test
    @Disabled
    void testFindAllByPage() {

    }

    @Test
    @Disabled
    void testFindAllLignesCommandesClientByCommandeClientId() {

    }

    @Test
    @Disabled
    void testFindByCode() {

    }

    @Test
    @Disabled
    void testFindById() {

    }

    @Test
    @Disabled
    void testUpdateArticle() {

    }

    @Test
    @Disabled
    void testUpdateClient() {

    }

    @Test
    @Disabled
    void testUpdateEtatCommande() {

    }

    @Test
    @Disabled
    void testUpdateQuantiteCommandee() {

    }
}
