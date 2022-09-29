package com.example.clonespotify;

import java.util.Hashtable;
import java.util.Vector;

public class Playlist {
    private String paysProvenance;
    private String lienSpotify;
    // private Vector<Hashtable> listeChansons;

    public Playlist(String paysProvenance) {
        this.paysProvenance = paysProvenance;
        this.lienSpotify = setLienSpotify(paysProvenance);
    }

    public String getPaysProvenance() {
        return paysProvenance;
    }

    public void setPaysProvenance(String paysProvenance) {
        this.paysProvenance = paysProvenance;
    }

    public String getLienSpotify() {
        return lienSpotify;
    }

    public String setLienSpotify(String paysProvenance){
        String lienSpotify;
        switch (paysProvenance) {
            case "UK":
                lienSpotify = "spotify:playlist:11jriovFQCrEszFtZ870LT?si=5f1b214923e64338";
                break;
            case "US":
                lienSpotify = "spotify:playlist:3gM1w0X80lUFnIS4q7b2zy?si=7a2daf1ac15f409e";
                break;
            case "FR":
                lienSpotify = "spotify:playlist:6MENu4VaafdmzKxK33Q2Ej?si=d11df1003d804964";
                break;
            case "GR":
                lienSpotify = "spotify:playlist:23peKtmUs6ep6jRse1QPkE?si=6ad4e00c2a4d4d7f";
                break;
            default:
                lienSpotify = "spotify:playlist:4qyjCKvniSKkFunRDLAzsr?si=3c77dd2175e44de4";
                break;
        }
        return lienSpotify;
    }
}
