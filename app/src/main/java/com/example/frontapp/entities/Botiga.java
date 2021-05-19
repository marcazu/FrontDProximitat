package com.example.frontapp.entities;

import java.io.Serializable;

public class Botiga implements Serializable {
    private String id;
    private String nom;
    private String descripcio;
    private String email;
    private String telefon;
    private String longitud;
    private String latitud;
    private String iconUrl;
    private String mainImageUrl;

    public Botiga() {
    }

    public Botiga(String id, String nom, String descripcio, String email, String telefon, String longitud, String latitud, String mainImageUrl,String iconUrl) {
        this.id = id;
        this.nom = nom;
        this.descripcio = descripcio;
        this.email = email;
        this.telefon = telefon;
        this.longitud = longitud;
        this.latitud = latitud;
        this.mainImageUrl = mainImageUrl;
        this.iconUrl = iconUrl;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }
}
