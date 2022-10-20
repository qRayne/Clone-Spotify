package com.example.clonespotify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Vector;

public class StartActivity extends AppCompatActivity {

    ImageView imageView;
    Button buttonBefore, buttonAfter, buttonPlay, buttonHistory;
    Ecouteur ec;
    Vector<Integer> countryRap;
    int etatImageView; // variables permettant de voir dans quel image on est

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        imageView = findViewById(R.id.imageViewCountry);
        buttonBefore = findViewById(R.id.buttonBeforeMusic);
        buttonAfter = findViewById(R.id.buttonAfterMusic);
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonHistory = findViewById(R.id.buttonHistory);

        ec = new Ecouteur();

        buttonBefore.setOnClickListener(ec);
        buttonAfter.setOnClickListener(ec);
        buttonPlay.setOnClickListener(ec);
        buttonHistory.setOnClickListener(ec);


        // On initialise le vector avec tous pays (logo) qui sont dans le dossier drawable
        countryRap = new Vector();

        // On ajoute tous les pays dans le vector
        countryRap.add(R.drawable.france);
        countryRap.add(R.drawable.germany);
        countryRap.add(R.drawable.greatbritain);
        countryRap.add(R.drawable.italy);
        countryRap.add(R.drawable.america);

        // Par defaut on met celui de la france
        etatImageView = 0;
        imageView.setImageResource(countryRap.get(etatImageView)); // les elements dans drawables sont tous int -> pourquoi le transtiper en int ?
    }

    public class Ecouteur implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            // Ici on verifie que les buttons before et after ne sorte pas de leurs tailles
            // permet d'eviter les indices en dehors des pays qu'on a
            if (view.equals(buttonBefore)){
                if (etatImageView > 0 && etatImageView <= countryRap.size()){
                    etatImageView--;
                    imageView.setImageResource(countryRap.get(etatImageView));
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Nous avons pas d'autres pays pour le moment !", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else if (view.equals(buttonAfter)){
                if (etatImageView >= 0 && etatImageView < countryRap.size()-1){
                    etatImageView++;
                    imageView.setImageResource(countryRap.get(etatImageView));
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Nous avons pas d'autres pays pour le moment !", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else if (view.equals(buttonPlay)){
                Intent retour = new Intent(getApplicationContext(),MainActivity.class);
                // On recupère le choix du pays et on envoie ce data à notre mainActivity qui elle va lancer la playlist correspondant au pays
                switch (etatImageView){
                    case 0:
                        retour.putExtra("paysProvenance","FR");
                        break;
                    case 1:
                        retour.putExtra("paysProvenance","GR");
                        break;
                    case 2:
                        retour.putExtra("paysProvenance","UK");
                        break;
                    case 3:
                        retour.putExtra("paysProvenance","IT");
                        break;
                    case 4:
                        retour.putExtra("paysProvenance","US");
                        break;
                }
                startActivity(retour);
            }
            else{
                // Ici si l'utilisateur souhaite comprendre l'histoire du rap dans un certain pays alors il clique sur history
                // Il sera rediriger vers une page de l'histoire du rap du pays qu'il a choisi
                switch (etatImageView){
                    case 0:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://fr.wikipedia.org/wiki/Hip-hop_fran%C3%A7ais")));
                        break;
                    case 1:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://fr.wikipedia.org/wiki/Hip-hop_allemand")));
                        break;
                    case 2:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://fr.wikipedia.org/wiki/Hip-hop_britannique")));
                        break;
                    case 3:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://fr.wikipedia.org/wiki/Hip-hop_italien")));
                        break;
                    case 4:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://fr.wikipedia.org/wiki/Hip-hop_aux_%C3%89tats-Unis")));
                        break;
                }
            }
        }
    }
}