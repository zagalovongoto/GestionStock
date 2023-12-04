package org.mambey.gestiondestock.repository;

import java.util.Optional;

import org.mambey.gestiondestock.model.Roles;
import org.mambey.gestiondestock.security.model.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Integer>{
    
    Optional<Roles> findByRoleName(ERole roleName);
}
