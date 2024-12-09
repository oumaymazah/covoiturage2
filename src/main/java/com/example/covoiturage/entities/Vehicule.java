package com.example.covoiturage.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Utilisation du camelCase

    @NotNull
    private String type;
    private String image;

    @NotNull
    private String num_matricule;

    @Enumerated(EnumType.STRING)
    private EtatVehicule etat;
   // @Min(1)  // Valeur minimale pour le confort
   // @Max(5)  // Valeur maximale pour le confort
    private int degre_confort;

   /* @ManyToOne
    @JoinColumn(name = "conducteur_id", referencedColumnName = "id", nullable = false)
    private Conducteur conducteur;*/ // Association au conducteur



    public enum EtatVehicule {
        NEUF,
        BON,
        MOYEN,
        MAUVAIS
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull String getType() {
        return type;
    }

    public void setType(@NotNull String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public @NotNull String getNum_matricule() {
        return num_matricule;
    }

    public void setNum_matricule(@NotNull String num_matricule) {
        this.num_matricule = num_matricule;
    }

    public EtatVehicule getEtat() {
        return etat;
    }

    public void setEtat(EtatVehicule etat) {
        this.etat = etat;
    }

    @Min(1)
    @Max(5)
    public int getDegre_confort() {
        return degre_confort;
    }

    public void setDegre_confort(@Min(1) @Max(5) int degre_confort) {
        this.degre_confort = degre_confort;
    }
/*/
    public Conducteur getConducteur() {
        return conducteur;
    }

    public void setConducteur(Conducteur conducteur) {
        this.conducteur = conducteur;
    }**/
}
