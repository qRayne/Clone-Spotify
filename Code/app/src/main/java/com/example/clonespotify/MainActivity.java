package com.example.clonespotify;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    ImageView imageCover;
    TextView textViewArtiste, textViewChanson,textViewTemps;
    SeekBar progression;
    Button buttonBeforeMusic, buttonPauseMusic, buttonPlayMusic, buttonAfterMusic,buttonStats;
    SpotifyDiffuseur spotifyDiffuseur;
    Ecouteur ec;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spotifyDiffuseur = new SpotifyDiffuseur(new Playlist(getIntent().getExtras().getString("paysProvenance")),this);
        ec = new Ecouteur();

        imageCover = findViewById(R.id.imageViewCover);
        buttonBeforeMusic = findViewById(R.id.buttonBeforeMusic);
        buttonPauseMusic = findViewById(R.id.buttonPauseMusic);
        buttonPlayMusic = findViewById(R.id.buttonPlayMusic);
        buttonAfterMusic = findViewById(R.id.buttonAfterMusic);
        buttonStats = findViewById(R.id.buttonStats);
        textViewArtiste = findViewById(R.id.textViewArtiste);
        textViewChanson = findViewById(R.id.textViewChanson);
        textViewTemps = findViewById(R.id.textViewTemps);
        progression = findViewById(R.id.seekBarProgression);

        buttonBeforeMusic.setOnClickListener(ec);
        buttonPauseMusic.setOnClickListener(ec);
        buttonPlayMusic.setOnClickListener(ec);
        buttonAfterMusic.setOnClickListener(ec);
        buttonStats.setOnClickListener(ec);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // On set l'artiste et la chanson suite à la connexion
        spotifyDiffuseur.autoriserApplication();
    }

    @Override
    protected void onStop() {
        super.onStop();
        spotifyDiffuseur.deconnecterApplication();
    }

    public class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            System.out.println(view);
        }
    }
}