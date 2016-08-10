package com.example.videonews;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends AppCompatActivity {
    View adjustView = findViewById(R.id.view1);
    float percentage;
    private AudioManager audioManager;
    private Window window;
    private int maxVolume;
    private int currentVolume;
    private float currentBrightness;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        touch();
        init();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void init() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        window=getWindow();
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,maxVolume/2,AudioManager.FLAG_PLAY_SOUND);
        WindowManager.LayoutParams layoutparams=window.getAttributes();

    }

    private void touch() {
        final GestureDetector gd = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                float startX = e1.getX();
                float startY = e1.getY();
                float endX = e2.getX();
                float endY = e2.getY();
                //判断左右
                float width = adjustView.getWidth();
                float height = adjustView.getHeight();
                percentage = (startY - endY) / height;
                if (startX > width / 2) {
                    //调亮度
                    adjustBrightness();
                    return true;
                } else if (startX > width / 2) {
                    //调音量
                    adjustVolume();
                    return true;
                }
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });
        adjustView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            //按下事件时
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if ((motionEvent.getAction()& MotionEvent.ACTION_MASK)== MotionEvent.ACTION_DOWN){
                    currentVolume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    currentBrightness=window.getAttributes().screenBrightness;
                }
                gd.onTouchEvent(motionEvent);
                return true;
            }
        });
    }

    private void adjustVolume() {

        int targetVolume =(int) (percentage * maxVolume)+currentVolume;
        targetVolume =targetVolume>maxVolume?maxVolume:targetVolume;
//        targetVolume=targetVolume
        //        if (percentage<0){
        //
        //        }
        //        else if (percentage>0){
        //
        //        }
    }

    private void adjustBrightness() {
        if (percentage < 0) {

        } else if (percentage > 0) {

        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
