package com.example.clonespotify;

public class Chanson {

    private String nomChanson;
    private Artiste artiste;
    private int duree;
    private String genre;
    private String paysProvenance;

    public Chanson(String nomChanson, Artiste artiste, int duree, String genre, String paysProvenance) {
        this.nomChanson = nomChanson;
        this.artiste = artiste;
        this.duree = duree;
        this.genre = genre;
        this.paysProvenance = paysProvenance;
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

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPaysProvenance() {
        return paysProvenance;
    }

    public void setPaysProvenance(String paysProvenance) {
        this.paysProvenance = paysProvenance;
    }
}
