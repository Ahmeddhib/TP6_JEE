package com.ahmed.tp6_jee.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "produits", schema = "db_mvc_cat", catalog = "")
public class ProduitsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_PRODUIT")
    private int idProduit;
    @Basic
    @Column(name = "NOM_PRODUIT")
    private String nomProduit;
    @Basic
    @Column(name = "PRIX")
    private double prix;

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProduitsEntity that = (ProduitsEntity) o;
        return idProduit == that.idProduit && Double.compare(that.prix, prix) == 0 && Objects.equals(nomProduit, that.nomProduit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduit, nomProduit, prix);
    }
}