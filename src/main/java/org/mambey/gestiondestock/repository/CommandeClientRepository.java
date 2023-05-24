package org.mambey.gestiondestock.repository;

import org.mambey.gestiondestock.model.CommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeClientRepository extends JpaRepository<CommandeClient, Integer>{
    
}
