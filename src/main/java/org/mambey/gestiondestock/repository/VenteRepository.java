package org.mambey.gestiondestock.repository;

import java.util.Optional;

import org.mambey.gestiondestock.model.Ventes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenteRepository extends JpaRepository<Ventes, Integer>{
    
    Optional<Ventes> findVentesByCode(String code);
}
