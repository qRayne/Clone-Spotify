package com.example.clonespotify;

public class Artiste {
    // Une instance de cette classe sera creer quand -> une musique sera lancee et qu'on recupère le nom d'artiste
    private String nomArtiste;

    public Artiste(String nomArtiste) {
        this.nomArtiste = nomArtiste;
    }

    public String getNomArtiste() {
        return nomArtiste;
    }

    public void setNomArtiste(String nomArtiste) {
        this.nomArtiste = nomArtiste;
    }
}
