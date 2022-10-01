package com.example.clonespotify;

public class Chanson {

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
}
