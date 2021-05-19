package com.example.frontapp.entities;

import java.io.Serializable;

public class Producte implements Serializable {

    private String id;
    private String nom;
    private String descripcio;
    private String tipus;
    private String preu;
    private String iconaUrl;

    public Producte() {
    }

    public Producte(String id, String nom, String descripcio, String tipus, String preu, String iconaUrl) {
        this.id = id;
        this.nom = nom;
        this.descripcio = descripcio;
        this.tipus = tipus;
        this.preu = preu;
        this.iconaUrl = iconaUrl;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public String getPreu() {
        return preu;
    }

    public void setPreu(String preu) {
        this.preu = preu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIconaUrl() {
        return iconaUrl;
    }

    public void setIconaUrl(String iconaUrl) {
        this.iconaUrl = iconaUrl;
    }
}


