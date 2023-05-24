package org.mambey.gestiondestock.repository;

import org.mambey.gestiondestock.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer>{
    
}
