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
        // Rayane : pour chopper le lien d'une playlit -> partager playlist -> copier lien -> recuperer la ligne de caractères

        /*
        Liste d'Artistes pour le projet Drill International :

        2 artistes par pays :
        US :
        Pop smoke : spotify:artist/0eDvMgVFoNV3TpwtrVCoTj?si=lxSunDkgQTCx75to-QLTVw
        Fivio Foreign : spotify:artist/14CHVeJGrR5xgUGQFV5BVM?si=sZyTKuIRRp22NZDPX4IIGw

        France :
        Gazo : spotify:artist/5gqmbbfjcikQBzPB5Hv13I?si=F67wd0M4TnKH4POycfPYRQ
        Ziak : spotify:artist/2ubn2zwyYaLdHOCKnTouU2?si=ZVvxr9HUR-iQ4_EzS78RZA

        Angleterre :
        Headie one : spotify:artist/6UCQYrcJ6wab6gnQ89OJFh?si=Eqr2NeprRnyJbLcNnCNU-Q
        Tion Wayne : spotify:artist/7b79bQFziJFedJb75k6hFt?si=rcmvC7NSQBeJ3gAPZGD1uw

        Italie :
        Rondasosa : spotify:artist/61bQ4nwIioR8w6PGxzpyY3?si=2hjPda_NRjGU9Rc3T4kiag
        SEVEN 7oo : spotify:artist/1Hg2H3Z46P8lXECM8DYSpU?si=GOBrwdgPTHqvPRyygo1sbA

        Allemagne :
        Luciano  : spotify:artist/3CJKkU0XuElRT1z8rEtIYg?si=0mve4RVuQvG42xxXqam-gA
        reezy : spotify:artist/1ul8iLt2WnFe2UIyovjg7q?si=KiUHNsrBTjKObG3duJpA0w

         */
        mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DWY6tYEFs22tT?si=4d2d23c7e0ff43db");

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