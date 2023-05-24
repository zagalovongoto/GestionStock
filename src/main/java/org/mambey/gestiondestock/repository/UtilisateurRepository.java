package org.mambey.gestiondestock.repository;

import org.mambey.gestiondestock.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Article, Integer>{
    
}
