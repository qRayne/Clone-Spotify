package com.example.clonespotify;

import com.spotify.protocol.types.ImageUri;

import java.util.concurrent.TimeUnit;

public class Chanson {
    // Une instance de cette classe sera creer quand -> une musique sera lancee et qu'on recupère le nom de la chanson, l'artiste(son nom) et sa duree
    private String nomChanson;
    private Artiste artiste;
    private long duree;

    public Chanson(String nomChanson, Artiste artiste, long duree) {
        this.nomChanson = nomChanson;
        this.artiste = artiste;
        this.duree = duree;
    }

    public String getNomChanson() {
        return nomChanson;
    }

    public void setNomChanson(String nomChanson) {
        this.nomChanson = nomChanson;
    }

    public Artiste getArtiste() {
        return artiste;
    }

    public void setArtiste(Artiste artiste) {
        this.artiste = artiste;
    }

    public long getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setDuree(long duree) {
        this.duree = duree;
    }
}
