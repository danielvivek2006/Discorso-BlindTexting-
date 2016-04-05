package com.ead.discorso;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;

import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends ActionBarActivity implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener
{
    private TextToSpeech tts,tts2,tts3;
    private GestureDetector mGestureDetector;
    public TextView text;
    private boolean mTwoFingersTapped;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGestureDetector = new GestureDetector(this);

        text = (TextView) findViewById(R.id.textView);

        //installVoiceData();
        initVoice();



        //for two-finger double tap listener

    }//onCreate()

    private void installVoiceData() {
        Intent intent = new Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.google.android.tts"/*replace with the package name of the target TTS engine*/);
        try {
            Log.v("voice", "Installing voice data: " + intent.toUri(0));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Log.e("voice", "Failed to install TTS data, no acitivty found for " + intent + ")");
        }
    }

    private void initVoice() {
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = tts.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    } else {



                    }

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }


            }
        }
        );
        tts.setSpeechRate(0.8f);


        tts2 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = tts2.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    } else {

                        speakOptions();

                    }

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }


            }
        }
        );
        tts2.setSpeechRate(0.8f);

        tts3 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = tts3.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    } else {

                        speakOptions();

                    }

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }


            }
        }
        );
        tts3.setSpeechRate(0.8f);

        //call voice
        speakOptions();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        int action = event.getAction();

        switch(action & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_POINTER_DOWN:

                if(tts2.isSpeaking()||tts.isSpeaking()||tts3.isSpeaking()) {
                    // set the mTwoFingersTapped flag to TRUE when we tap with 2 fingers at once
                    mTwoFingersTapped = false;
                }
                else {
                    text.setText("Bye see you soon...");
                    mTwoFingersTapped = true;


                    tts.speak("bye see you soon", TextToSpeech.QUEUE_FLUSH, null);
                    Thread t = new Thread() {
                        public void run() {
                            try {
                                sleep(2000);
                                finish(); //close this activity

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    t.start();

                }
                break;
        }

        if(tts.isSpeaking()||tts2.isSpeaking()||tts3.isSpeaking()) {
            return false;
        }
        else{
            return mGestureDetector.onTouchEvent(event);
        }
    }


    private void speakOut(String voice1,String voice2){

        tts.speak(voice1, TextToSpeech.QUEUE_FLUSH, null);
        tts2.speak(voice2,TextToSpeech.QUEUE_FLUSH,null);
    }
    private void speakOptions(){
        speakOut(".Tap once to read inbox... tap twice to create message... ","tap once with two fingers to exit ");
        //tts.speak(".Tap once to read inbox...", TextToSpeech.QUEUE_FLUSH, null);
        //tts2.speak(".tap twice to create message...", TextToSpeech.QUEUE_FLUSH, null);
        //tts3.speak(".tap once with two fingers to exit...", TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        if (tts2 != null) {
            tts2.stop();
            tts2.shutdown();
        }
        if (tts3 != null) {
            tts3.stop();
            tts3.shutdown();
        }

    }

    //stop playing voice when paused (onPause() state)
    @Override
    protected void onPause() {
        super.onPause();

        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        if (tts2 != null) {
            tts2.stop();
            tts2.shutdown();
        }
        if (tts3 != null) {
            tts3.stop();
            tts3.shutdown();
        }
    }

    //start playing voices again, when resumed from pause (when returned to onResume() state from onPause() -> onRestart() -> onStart() -> onResume() )
    @Override
    protected void onResume() {
        super.onResume();

        initVoice();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        text.setText("");

        tts.speak("ok proceeding to Inbox", TextToSpeech.QUEUE_FLUSH, null);

        final Intent i = new Intent(MainActivity.this,InboxActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Thread t = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                    startActivity(i);
                }catch(Exception e){

                }
            }
        };
        t.start();

        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        text.setText("");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        text.setText("");

        tts.speak("ok proceeding to Create message", TextToSpeech.QUEUE_FLUSH, null);

        //go to create message activity
       final Intent i = new Intent(MainActivity.this,RecordContactActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Thread t = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                    startActivity(i);
                }catch(Exception e){

                }
            }
        };
        t.start();

        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        text.setText("");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        text.setText("");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        text.setText("");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        text.setText("");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        text.setText("");

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        text.setText("");
        return true;
    }
}



