package com.example.kylerkiminki.jukebox;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.sql.Connection;

/**
 * Created by kylerkiminki on 10/16/16.
 */

public class PlayerActivity extends AppCompatActivity implements SpotifyPlayer.NotificationCallback, ConnectionStateCallback{

    // TODO: Replace with your client ID
    private static final String CLIENT_ID = "yourclientid";
    // TODO: Replace with your redirect URI
    private static final String REDIRECT_URI = "testschema://callback";
    private static final String TAG = "PlayerActivity";

    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String AccessToken = savedInstanceState.getString("AccessToken");
        Config PlayerConfig = new Config(this, AccessToken, CLIENT_ID);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d(TAG, "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d(TAG, "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    public void onTemporaryError(){
        Log.d(TAG, "Temporary error");
    }

    @Override
    public void onLoginFailed(int e){
        Log.d(TAG, "Login failed");
        Log.d(TAG, "Error code = "+e);
    }

    @Override
    public void onConnectionMessage (String message){
        Log.d(TAG, "Connection message = "+message);
    }

    @Override
    public void onLoggedOut (){
        Log.d(TAG, "Logged out");
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    @Override
    public void onLoggedIn(){
        Log.d(TAG, "Logged in");
    }

}
