package com.example.clonespotify;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.MotionEvent;
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
import com.spotify.protocol.types.Uri;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    ImageView imageCover;
    TextView textViewArtiste, textViewChanson,textViewTemps;
    SeekBar progression;
    Button buttonBeforeMusic, buttonPauseMusic, buttonPlayMusic, buttonAfterMusic,buttonStats,buttonDetails;
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
        buttonDetails = findViewById(R.id.buttonAfficherDetails); // à enlever après
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
        buttonDetails.setOnClickListener(ec);
        progression.setOnSeekBarChangeListener(ec);
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

    public class Ecouteur implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{
        @Override
        public void onClick(View view) {
            if (view.equals(buttonPauseMusic))
                spotifyDiffuseur.getPlayerApi().pause();
            else if (view.equals(buttonPlayMusic))
                spotifyDiffuseur.getPlayerApi().play(spotifyDiffuseur.getPlaylist().getLienSpotify());
            else if (view.equals(buttonBeforeMusic))
                spotifyDiffuseur.getPlayerApi().skipPrevious(); // spotify de base donc pas de skipBefore
            else if (view.equals(buttonAfterMusic))
                spotifyDiffuseur.getPlayerApi().skipNext();
            else if (view.equals(buttonDetails)){
                // Image
                //

                // Ariste
                textViewArtiste.setText(spotifyDiffuseur.getPlayerState().track.artist.name);

                // Chanson
                textViewChanson.setText(spotifyDiffuseur.getPlayerState().track.name);

                // temps
                textViewTemps.setText(DateUtils.formatElapsedTime(
                        spotifyDiffuseur.getPlayerState().track.duration/1000));
            }
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            // recupère le temps de la chanson et la setter dans la progression bar
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}