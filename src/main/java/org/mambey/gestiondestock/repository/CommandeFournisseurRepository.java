package org.mambey.gestiondestock.repository;

import java.util.List;
import java.util.Optional;

import org.mambey.gestiondestock.model.CommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur, Integer>{
    
    Optional<CommandeFournisseur> findCommandFournisseurByCode(String code);

    List<CommandeFournisseur> findAllByFournisseurId(Integer id);
}
