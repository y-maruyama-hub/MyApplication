package com.example.myapplication;

import android.content.Intent;
import android.app.IntentService;
import android.media.MediaPlayer;

public class PlaySoundService extends IntentService implements MediaPlayer.OnCompletionListener{

    public PlaySoundService() {
        super("PlaySound");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            System.out.println("test");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
    }

    private void stop(){
        System.out.println("stop");
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        System.out.println("aa");
    }
}