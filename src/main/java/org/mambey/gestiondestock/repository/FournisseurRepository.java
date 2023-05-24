package org.mambey.gestiondestock.repository;

import org.mambey.gestiondestock.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer>{
    
}
