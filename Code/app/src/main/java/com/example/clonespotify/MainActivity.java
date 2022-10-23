package com.example.clonespotify;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.spotify.protocol.client.CallResult;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    ImageView imageCover;
    TextView textViewArtiste, textViewChanson,textViewTemps,titleApp2,textViewDureeEnCours;
    SeekBar progression;
    Button buttonBeforeMusic, buttonPauseMusic, buttonPlayMusic, buttonAfterMusic, buttonCustom;
    Chronometer chronometerChanson;
    SpotifyDiffuseur spotifyDiffuseur;
    Ecouteur ec;
    ActivityResultLauncher<Intent> launcher;
    int tempsProgression;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On recupère le choix du pays et on creer un spotifyDifusseur contenant une playlist
        spotifyDiffuseur = new SpotifyDiffuseur(new Playlist(getIntent().getExtras().getString("paysProvenance")),this);
        ec = new Ecouteur();

        // les findViewById
        imageCover = findViewById(R.id.imageViewCover);
        titleApp2 = findViewById(R.id.titleApp2);
        buttonBeforeMusic = findViewById(R.id.buttonBeforeMusic);
        buttonPauseMusic = findViewById(R.id.buttonPauseMusic);
        buttonPlayMusic = findViewById(R.id.buttonPlayMusic);
        buttonAfterMusic = findViewById(R.id.buttonAfterMusic);
        buttonCustom = findViewById(R.id.buttonCustom);
        textViewArtiste = findViewById(R.id.textViewArtiste);
        textViewChanson = findViewById(R.id.textViewChanson);
        textViewTemps = findViewById(R.id.textViewTemps);
        textViewDureeEnCours = findViewById(R.id.textViewDureeEnCours);
        chronometerChanson = findViewById(R.id.chronometerChanson);
        progression = findViewById(R.id.seekBarProgression);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),new customUI());
        tempsProgression = 0;

        // Les sets dans leurs ecouteurs respectifs
        buttonBeforeMusic.setOnClickListener(ec);
        buttonPauseMusic.setOnClickListener(ec);
        buttonPlayMusic.setOnClickListener(ec);
        buttonAfterMusic.setOnClickListener(ec);
        buttonCustom.setOnClickListener(ec);
        chronometerChanson.setOnChronometerTickListener(ec);

        // start le chronometre
        chronometerChanson.start();
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
        // si on quitte l'activite alors deconnecte toi de spotify
        spotifyDiffuseur.deconnecterApplication();
    }

    public void afficherDetailsChanson(){
        // On creer tous les instances necessaires pour l'affichage
        CallResult<Bitmap> imageSpotify = spotifyDiffuseur.getmSpotifyAppRemote().getImagesApi().getImage(spotifyDiffuseur.getPlayerState().track.imageUri);
        Artiste artiste = new Artiste(spotifyDiffuseur.getPlayerState().track.artist.name);
        Chanson chanson = new Chanson(spotifyDiffuseur.getPlayerState().track.name,artiste,spotifyDiffuseur.getPlayerState().track.duration);

        // L'images
        imageSpotify.setResultCallback(new CallResult.ResultCallback<Bitmap>() {
            @Override
            public void onResult(Bitmap data) {
                imageCover.setImageBitmap(data);
            }
        });

        // Ariste
        textViewArtiste.setText(artiste.getNomArtiste());

        // Chanson
        textViewChanson.setText(chanson.getNomChanson());

        // temps
        // si on veut la duree au total :
        textViewTemps.setText(DateUtils.formatElapsedTime(chanson.getDuree()/1000));
    }

    public class Ecouteur implements View.OnClickListener, Chronometer.OnChronometerTickListener {
        @Override
        public void onClick(View view) {
            // C'est grâce au playApi instancier lors de la connexion qu'on peut interagir avec spotify pour PAUSE, PLAY, SKIP, PREVIOUS
            if (view.equals(buttonPauseMusic))
                spotifyDiffuseur.getPlayerApi().pause();
            else if (view.equals(buttonPlayMusic))
                spotifyDiffuseur.getPlayerApi().play(spotifyDiffuseur.getPlaylist().getLienSpotify());
            else if (view.equals(buttonBeforeMusic))
                spotifyDiffuseur.getPlayerApi().skipPrevious(); // spotify de base donc pas de skipBefore
            else if (view.equals(buttonAfterMusic))
                spotifyDiffuseur.getPlayerApi().skipNext(); // limiter à 3 skip par heure
            else
                launcher.launch(new Intent(MainActivity.this,CustomActivity.class)); // si l'utilisateur veut changer le UI de son player
        }

        @Override
        public void onChronometerTick(Chronometer chronometer) {

            // Commencer à partir de la musique
            if (progression.getProgress() != 0){
                tempsProgression = progression.getProgress(); // le temps de progression est le temps actuelle de la musique
            }
            tempsProgression++; // incrementera soit à partir de 0 ou à partir de la progression actuelle de la bar
            progression.setProgress(tempsProgression);

            // Afficher en continue les secondes ecoulees
            // le premier progress.geProgress est pour les minutes et le seconds pour les secondes
            textViewDureeEnCours.setText(String.format("%02d:%02d",(progression.getProgress() % 3600) / 60,progression.getProgress() % 60));
        }
    }

    // retour du Intent / boomerang
    private class customUI implements ActivityResultCallback<ActivityResult>{

        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == 1){
                // on recupère l'hexa mit dans le put data de CustomActivity et on customise le player grâce à cette couleur
                String hexaValue = result.getData().getStringExtra("hexaValue");
                customizeUI(hexaValue);
            }
        }
    }

    // modifier le player
    private void customizeUI (String hexaValue){
        // le titre
        titleApp2.setBackgroundColor(Color.parseColor(hexaValue));

        // les buttons
        buttonBeforeMusic.setBackgroundColor(Color.parseColor(hexaValue));
        buttonPauseMusic.setBackgroundColor(Color.parseColor(hexaValue));
        buttonPlayMusic.setBackgroundColor(Color.parseColor(hexaValue));
        buttonAfterMusic.setBackgroundColor(Color.parseColor(hexaValue));
        buttonCustom.setBackgroundColor(Color.parseColor(hexaValue));
    }
}