package com.example.clonespotify;

public class Artiste {
    private String nomArtiste;
    private String paysProvenance;

    public Artiste(String nomArtiste, String paysProvenance) {
        this.nomArtiste = nomArtiste;
        this.paysProvenance = paysProvenance;
    }

    public String getNomArtiste() {
        return nomArtiste;
    }

    public void setNomArtiste(String nomArtiste) {
        this.nomArtiste = nomArtiste;
    }

    public String getPaysProvenance() {
        return paysProvenance;
    }

    public void setPaysProvenance(String paysProvenance) {
        this.paysProvenance = paysProvenance;
    }
}
