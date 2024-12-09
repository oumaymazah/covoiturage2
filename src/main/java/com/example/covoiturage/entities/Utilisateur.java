package com.example.covoiturage.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.management.relation.Role;
@EqualsAndHashCode
@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nomComplet;
    @Column(unique = true)
    private String email;
    private String motDePasse;
    private String telephone;

    @Enumerated(EnumType.STRING)
    private Role role;
    public enum Role {
        CONDUCTEUR,
        PASSAGER,
        ADMIN
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
// Ajout d'un getter explicite pour récupérer le rôle avec un préfixe ROLE_
   /* public String getRoleAsString() {
        return "ROLE_" + role.name();  // Retourne le rôle sous forme de chaîne avec le préfixe ROLE_
    }*/

    // Getters et Setters
}
