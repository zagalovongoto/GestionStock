package org.mambey.gestiondestock.repository;

import org.mambey.gestiondestock.model.Ventes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenteRepository extends JpaRepository<Ventes, Integer>{
    
}
