package org.mambey.gestiondestock.repository;

import org.mambey.gestiondestock.model.MvtStk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MvtStkRepository extends JpaRepository<MvtStk, Integer>{
    
}
