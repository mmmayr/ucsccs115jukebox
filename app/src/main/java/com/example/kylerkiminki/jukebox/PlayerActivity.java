package com.example.kylerkiminki.jukebox;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private static final String test_uri = "spotify:track:17xbKoCF5iDcSb9usFt2yO";

    private final Player.OperationCallback mOperationCallback = new Player.OperationCallback() {
        @Override
        public void onSuccess() {
            Log.d(TAG,"OK!");
        }

        @Override
        public void onError(Error error) {
            Log.d(TAG, "ERROR:" + error);
        }
    };

    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Button playbtn = (Button)findViewById(R.id.play_button);
        Button prevbtn = (Button)findViewById(R.id.prev_button);
        Button nextbtn = (Button)findViewById(R.id.next_button);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String AccessToken = savedInstanceState.getString("AccessToken");
        Config PlayerConfig = new Config(this, AccessToken, CLIENT_ID);
        Spotify.getPlayer(PlayerConfig, this, new SpotifyPlayer.InitializationObserver(){
            @Override
            public void onInitialized(SpotifyPlayer spotifyPlayer){
                player = spotifyPlayer;
                player.addConnectionStateCallback(PlayerActivity.this);
                player.addNotificationCallback(PlayerActivity.this);
            }

            @Override
            public void onError(Throwable throwable){
                Log.d(TAG, "Player initialization error");
                Log.d(TAG, throwable.getMessage());
            }
        });
        player.setRepeat(mOperationCallback, true);
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playbtn.getText()=="play") {
                    player.playUri(mOperationCallback, test_uri, 0, 0);
                }else {
                    player.pause(mOperationCallback);
                }
            } });

        prevbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               player.skipToPrevious(mOperationCallback);
            } });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.skipToNext(mOperationCallback);
            } });
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
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
