package com.example.clonespotify;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.text.format.DateUtils;
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

import com.spotify.protocol.client.CallResult;

public class MainActivity extends AppCompatActivity {

    ImageView imageCover;
    TextView textViewArtiste, textViewChanson,textViewTemps,titleApp2;
    SeekBar progression;
    Button buttonBeforeMusic, buttonPauseMusic, buttonPlayMusic, buttonAfterMusic, buttonCustom,buttonDetails;
    SpotifyDiffuseur spotifyDiffuseur;
    Ecouteur ec;
    ActivityResultLauncher<Intent> launcher;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spotifyDiffuseur = new SpotifyDiffuseur(new Playlist(getIntent().getExtras().getString("paysProvenance")),this);
        ec = new Ecouteur();

        imageCover = findViewById(R.id.imageViewCover);
        titleApp2 = findViewById(R.id.titleApp2);
        buttonBeforeMusic = findViewById(R.id.buttonBeforeMusic);
        buttonPauseMusic = findViewById(R.id.buttonPauseMusic);
        buttonPlayMusic = findViewById(R.id.buttonPlayMusic);
        buttonAfterMusic = findViewById(R.id.buttonAfterMusic);
        buttonDetails = findViewById(R.id.buttonAfficherDetails); // à enlever après
        buttonCustom = findViewById(R.id.buttonCustom);
        textViewArtiste = findViewById(R.id.textViewArtiste);
        textViewChanson = findViewById(R.id.textViewChanson);
        textViewTemps = findViewById(R.id.textViewTemps);
        progression = findViewById(R.id.seekBarProgression);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),new customUI());

        buttonBeforeMusic.setOnClickListener(ec);
        buttonPauseMusic.setOnClickListener(ec);
        buttonPlayMusic.setOnClickListener(ec);
        buttonAfterMusic.setOnClickListener(ec);
        buttonCustom.setOnClickListener(ec);
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

    public void afficher(){
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

    public class Ecouteur implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{
        @Override
        public void onClick(View view) {
            if (view.equals(buttonPauseMusic))
                spotifyDiffuseur.getPlayerApi().pause();
            else if (view.equals(buttonPlayMusic))
                spotifyDiffuseur.getPlayerApi().play(spotifyDiffuseur.getPlaylist().getLienSpotify());
            else if (view.equals(buttonBeforeMusic))
                spotifyDiffuseur.getPlayerApi().skipPrevious(); // spotify de base donc pas de skipBefore
            else if (view.equals(buttonAfterMusic)) {
                spotifyDiffuseur.getPlayerApi().skipNext();
                afficher(); // on change de music donc on change les infos de la chanson
            }
            else if (view.equals(buttonDetails))
                afficher();
            else
                launcher.launch(new Intent(MainActivity.this,CustomActivity.class));
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

    // retour du Intent / boomerang
    private class customUI implements ActivityResultCallback<ActivityResult>{

        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == 1){
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
        buttonDetails.setBackgroundColor(Color.parseColor(hexaValue));
        buttonBeforeMusic.setBackgroundColor(Color.parseColor(hexaValue));
        buttonPauseMusic.setBackgroundColor(Color.parseColor(hexaValue));
        buttonPlayMusic.setBackgroundColor(Color.parseColor(hexaValue));
        buttonAfterMusic.setBackgroundColor(Color.parseColor(hexaValue));
        buttonCustom.setBackgroundColor(Color.parseColor(hexaValue));
    }
}