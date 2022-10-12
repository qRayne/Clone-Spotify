package com.example.clonespotify;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.Artist;
import com.spotify.protocol.types.ImageUri;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.Vector;

public class SpotifyDiffuseur {
    // Classe très importante : permet la diffusion de spotify
    private static final String CLIENT_ID = "d180deb12fbe440ea85717907df43bab";
    private static final String REDIRECT_URI = "com.example.clonespotify://callback";
    private SpotifyAppRemote mSpotifyAppRemote;
    private Playlist playlist;
    private PlayerApi playerApi;
    private Activity context;
    private PlayerState playerState;

    public SpotifyDiffuseur(Playlist playlist, Activity context) {
        // ici seule la playlist et le context seront passer en paramètre
        // Le reste des variables de classes seront instanciee pendant la connexion : connecterApplication
        this.playlist = playlist;
        this.context = context;
    }

    public void autoriserApplication() {
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(context, connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");

                        // Now you can start interacting with App Remote
                        connecterApplication();
                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("MyActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }

                });
    }

    public void deconnecterApplication(){
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    public void connecterApplication(){
        playerApi = mSpotifyAppRemote.getPlayerApi(); // on initalise le playapi
        playerApi.play(playlist.getLienSpotify()); // on joue le lien spotify du pays dans la playlist
        playerApi.subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Log.d("MainActivity", track.name + " by " + track.artist.name);
                        this.playerState = playerState; // on initialise le playerState pour recuperer les donnes
                    }
                });
    }

    public PlayerApi getPlayerApi() {
        return playerApi;
    }

    public SpotifyAppRemote getmSpotifyAppRemote() {
        return mSpotifyAppRemote;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public PlayerState getPlayerState(){ return playerState;}

    public void setPlayerApi(PlayerApi playerApi) {
        this.playerApi = playerApi;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }
}




