package com.example.clonespotify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
            if (view.equals(buttonBefore)){
                if (etatImageView > 0 && etatImageView <= countryRap.size()){
                    etatImageView--;
                    imageView.setImageResource(countryRap.get(etatImageView));
                }
            }
            else if (view.equals(buttonAfter)){
                if (etatImageView >= 0 && etatImageView < countryRap.size()-1){
                    etatImageView++;
                    imageView.setImageResource(countryRap.get(etatImageView));
                }
            }
            else if (view.equals(buttonPlay)){
                Intent retour = new Intent(getApplicationContext(),MainActivity.class);
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