package com.example.clonespotify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
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
        buttonBefore = findViewById(R.id.buttonBefore);
        buttonAfter = findViewById(R.id.buttonAfter);
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
        etatImageView = countryRap.get(0);
        imageView.setImageResource(etatImageView); // les elements dans drawables sont tous int -> pourquoi le transtiper en int ?
    }

    public class Ecouteur implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if (view.equals(buttonBefore)){

            }
            else if (view.equals(buttonAfter)){

            }
            else if (view.equals(buttonPlay)){

            }
            else{

            }
        }
    }
}