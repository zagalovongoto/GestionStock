package org.mambey.gestiondestock.repository;

import org.mambey.gestiondestock.model.CommandeClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CommandeClientRepository extends JpaRepository<CommandeClient, Integer>{
    
    Optional<CommandeClient> findCommandClientByCode(String code);

    List<CommandeClient> findAllByClientId(Integer id);

    Page<CommandeClient> findAll(Pageable page);
}
