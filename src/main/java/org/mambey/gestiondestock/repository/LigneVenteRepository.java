package org.mambey.gestiondestock.repository;

import org.mambey.gestiondestock.model.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneVenteRepository extends JpaRepository<LigneVente, Integer>{
    
}
