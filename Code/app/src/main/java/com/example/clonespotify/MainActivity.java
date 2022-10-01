package com.example.clonespotify;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

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

public class MainActivity extends AppCompatActivity {

    SpotifyDiffuseur spotifyDiffuseur;
    ActivityResultLauncher<Intent> launcher;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spotifyDiffuseur = new SpotifyDiffuseur(new Playlist(getIntent().getExtras().getString("paysProvenance")),this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        spotifyDiffuseur.autoriserApplication();
    }

    @Override
    protected void onStop() {
        super.onStop();
        spotifyDiffuseur.deconnecterApplication();
    }
}