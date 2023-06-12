package org.mambey.gestiondestock.repository;

import org.mambey.gestiondestock.model.CommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface CommandeClientRepository extends JpaRepository<CommandeClient, Integer>{
    
    Optional<CommandeClient> findCommandClientByCode(String code);
}
