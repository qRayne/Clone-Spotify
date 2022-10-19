package com.example.clonespotify;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

public class Playlist {
    // Ici on ne veut pas avoir une classe playlist habituel auquel on aurait une liste de chanson et d'artiste
    // Cette classe sert surtout à cherche une playlist correspondant au pays que l'utilisateur veut ecouter
    // final pour le lien spotify, car impossible à l'utilisateur de changer pendant l'ecoute de sa playlist
    private String paysProvenance;
    private final String lienSpotify;

    public Playlist(String paysProvenance) {
        this.paysProvenance = paysProvenance;
        this.lienSpotify = setLienSpotify(paysProvenance);
        // j'utilise la class playlist pour recuperer le pays de provenance -> pour voir quel playlist jouer
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

    public String setLienSpotify(String paysProvenance) {
        // Au lieu de faire des if else -> on creer un hashmap contenant le pays de provenance et son lien spotify
        HashMap<String,String> dataLienSpotify = new HashMap<>();

        // on set les pays
        dataLienSpotify.put("UK","spotify:playlist:37i9dQZF1DZ06evO07PW9i?si=33672e33a8d84a16");
        dataLienSpotify.put("US","spotify:playlist:03Phtu3e3yY63lHJRro2WH?si=37bdc355345e4add");
        dataLienSpotify.put("FR","spotify:playlist:6MENu4VaafdmzKxK33Q2Ej?si=d11df1003d804964");
        dataLienSpotify.put("GR","spotify:playlist:23peKtmUs6ep6jRse1QPkE?si=6ad4e00c2a4d4d7f");
        dataLienSpotify.put("IT","spotify:playlist:4qyjCKvniSKkFunRDLAzsr?si=3c77dd2175e44de4");

        // on retourne le lien spotify du pays choisi
        return dataLienSpotify.get(paysProvenance);
    }
}
