package com.example.clonespotify;

import android.os.Bundle;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

public class MainActivity extends AppCompatActivity {

    SpotifyDiffuseur spotifyDiffuseur;

    private static final String CLIENT_ID = "d180deb12fbe440ea85717907df43bab";
    private static final String REDIRECT_URI = "com.example.clonespotify://callback";
    private SpotifyAppRemote mSpotifyAppRemote;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onStart() {
        super.onStart();
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");

                        // Now you can start interacting with App Remote
                        connected();

                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("MyActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    private void connected() {
        // Play a playlist
        // Rayane : pour chopper le lien d'une playlist -> partager playlist -> copier lien -> recuperer la ligne de caractères

        /*
        UK Drill : spotify:playlist:11jriovFQCrEszFtZ870LT?si=5f1b214923e64338
        US Drill : spotify:playlist:3gM1w0X80lUFnIS4q7b2zy?si=7a2daf1ac15f409e
        FR Drill : spotify:playlist:6MENu4VaafdmzKxK33Q2Ej?si=d11df1003d804964
        GR Drill : spotify:playlist:23peKtmUs6ep6jRse1QPkE?si=6ad4e00c2a4d4d7f
        IT Drill : spotify:playlist:4qyjCKvniSKkFunRDLAzsr?si=3c77dd2175e44de4
         */
        mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:23peKtmUs6ep6jRse1QPkE?si=6ad4e00c2a4d4d7f");

        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Log.d("MainActivity", track.name + " by " + track.artist.name);
                    }
                });
    }
}