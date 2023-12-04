package org.mambey.gestiondestock.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.mambey.gestiondestock.security.model.ERole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
public class Roles extends AbstractEntity{
    
    @Column(name = "rolename")
    private ERole roleName;

    @Column(name = "identreprise")
    private Integer idEntreprise;
}
