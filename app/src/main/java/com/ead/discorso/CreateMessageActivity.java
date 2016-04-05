package com.ead.discorso;

import android.content.Intent;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;

import android.view.MotionEvent;
import android.widget.TextView;

import java.util.Locale;
import java.util.ArrayList;


import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import android.view.View;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class CreateMessageActivity extends ActionBarActivity implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener,RecognitionListener
{
    private GestureDetector mGestureDetector;
    private boolean mTwoFingersTapped;
    private TextView displayText;
    private TextToSpeech welcomeSpeech,hearInstructionsAgain,yourMessage;
    private MotionEvent e;

    private ProgressBar progressBar1,progressBar2,progressBar3,progressBar4,progressBar5,progressBar6,progressBar7,progressBar8,progressBar9,progressBar10,progressBar11,progressBar12,progressBar13,progressBar14,progressBar15;
    private SpeechRecognizer speech = null, assistant=null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";

    private boolean messageRecorded=false;
    private boolean contactRecorded=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);

        mGestureDetector = new GestureDetector(this);

        initWelcomeVoice();
        initHearInstructionsAgainVoice();
        initYourMessageVoice();

        displayText = (TextView) findViewById(R.id.textView1);
        //toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);

        //progress Bars
        progressBar1 = (ProgressBar) findViewById(R.id.voiceProgressBar1);
        progressBar2 = (ProgressBar) findViewById(R.id.voiceProgressBar2);
        progressBar3 = (ProgressBar) findViewById(R.id.voiceProgressBar3);
        progressBar4 = (ProgressBar) findViewById(R.id.voiceProgressBar4);
        progressBar5 = (ProgressBar) findViewById(R.id.voiceProgressBar5);
        progressBar6 = (ProgressBar) findViewById(R.id.voiceProgressBar6);
        progressBar7 = (ProgressBar) findViewById(R.id.voiceProgressBar7);
        progressBar8 = (ProgressBar) findViewById(R.id.voiceProgressBar8);
        progressBar9 = (ProgressBar) findViewById(R.id.voiceProgressBar9);
        progressBar10 = (ProgressBar) findViewById(R.id.voiceProgressBar10);
        progressBar11 = (ProgressBar) findViewById(R.id.voiceProgressBar11);
        progressBar12 = (ProgressBar) findViewById(R.id.voiceProgressBar12);
        progressBar13 = (ProgressBar) findViewById(R.id.voiceProgressBar13);
        progressBar14 = (ProgressBar) findViewById(R.id.voiceProgressBar14);
        progressBar15 = (ProgressBar) findViewById(R.id.voiceProgressBar15);

        progressBar1.setClickable(false);
        progressBar2.setClickable(false);
        progressBar3.setClickable(false);
        progressBar4.setClickable(false);
        progressBar5.setClickable(false);
        progressBar6.setClickable(false);
        progressBar7.setClickable(false);
        progressBar8.setClickable(false);
        progressBar9.setClickable(false);
        progressBar10.setClickable(false);
        progressBar11.setClickable(false);
        progressBar12.setClickable(false);
        progressBar13.setClickable(false);
        progressBar14.setClickable(false);
        progressBar15.setClickable(false);


        progressBar1.setVisibility(View.INVISIBLE);
        progressBar2.setVisibility(View.INVISIBLE);
        progressBar3.setVisibility(View.INVISIBLE);
        progressBar4.setVisibility(View.INVISIBLE);
        progressBar5.setVisibility(View.INVISIBLE);
        progressBar6.setVisibility(View.INVISIBLE);
        progressBar7.setVisibility(View.INVISIBLE);
        progressBar8.setVisibility(View.INVISIBLE);
        progressBar9.setVisibility(View.INVISIBLE);
        progressBar10.setVisibility(View.INVISIBLE);
        progressBar11.setVisibility(View.INVISIBLE);
        progressBar12.setVisibility(View.INVISIBLE);
        progressBar13.setVisibility(View.INVISIBLE);
        progressBar14.setVisibility(View.INVISIBLE);
        progressBar15.setVisibility(View.INVISIBLE);

        //speech recognizers
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);

        assistant = SpeechRecognizer.createSpeechRecognizer(this);
        assistant.setRecognitionListener(this);

        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());



    }

    //welcome voice
    private void initWelcomeVoice() {
        welcomeSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = welcomeSpeech.setLanguage(Locale.US);

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
        welcomeSpeech.setSpeechRate(0.75f);

    }

        //your mesaage voice
    private void initYourMessageVoice() {
        yourMessage = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = yourMessage.setLanguage(Locale.US);

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
        yourMessage.setSpeechRate(0.75f);
    }

    //hear instructions again voice
    private void initHearInstructionsAgainVoice() {
        hearInstructionsAgain = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = hearInstructionsAgain.setLanguage(Locale.US);

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
        hearInstructionsAgain.setSpeechRate(0.75f);
    }

    private void speakOptions() {
        welcomeSpeech.speak("hold with your finger to record your message or tap to go back to previous menu", TextToSpeech.QUEUE_FLUSH, null);

    }

    @Override
    public void onResume() {
        super.onResume();
        initWelcomeVoice();
    }

    @Override
    protected void onPause() {
        super.onPause();
       /* if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
*/
        if (welcomeSpeech != null) {
            welcomeSpeech.stop();
            welcomeSpeech.shutdown();
        }
        if (hearInstructionsAgain != null) {
            hearInstructionsAgain.stop();
            hearInstructionsAgain.shutdown();
        }
        if (yourMessage != null) {
            yourMessage.stop();
            yourMessage.shutdown();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (welcomeSpeech != null) {
            welcomeSpeech.stop();
            welcomeSpeech.shutdown();
        }
        if (hearInstructionsAgain != null) {
            hearInstructionsAgain.stop();
            hearInstructionsAgain.shutdown();
        }
        if (yourMessage != null) {
            yourMessage.stop();
            yourMessage.shutdown();
        }

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");

        progressBar1.setIndeterminate(false);
        progressBar1.setMax(10);
        progressBar2.setIndeterminate(false);
        progressBar2.setMax(10);
        progressBar3.setIndeterminate(false);
        progressBar3.setMax(10);
        progressBar4.setIndeterminate(false);
        progressBar4.setMax(10);
        progressBar5.setIndeterminate(false);
        progressBar5.setMax(10);

        progressBar6.setIndeterminate(false);
        progressBar6.setMax(10);
        progressBar7.setIndeterminate(false);
        progressBar7.setMax(10);
        progressBar8.setIndeterminate(false);
        progressBar8.setMax(10);
        progressBar9.setIndeterminate(false);
        progressBar9.setMax(10);
        progressBar10.setIndeterminate(false);
        progressBar10.setMax(10);

        progressBar11.setIndeterminate(false);
        progressBar11.setMax(10);
        progressBar12.setIndeterminate(false);
        progressBar12.setMax(10);
        progressBar13.setIndeterminate(false);
        progressBar13.setMax(10);
        progressBar14.setIndeterminate(false);
        progressBar14.setMax(10);
        progressBar15.setIndeterminate(false);
        progressBar15.setMax(10);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");

        progressBar1.setIndeterminate(true);
        progressBar2.setIndeterminate(true);
        progressBar3.setIndeterminate(true);
        progressBar4.setIndeterminate(true);
        progressBar5.setIndeterminate(true);

        progressBar6.setIndeterminate(true);
        progressBar7.setIndeterminate(true);
        progressBar8.setIndeterminate(true);
        progressBar9.setIndeterminate(true);
        progressBar10.setIndeterminate(true);

        progressBar11.setIndeterminate(true);
        progressBar12.setIndeterminate(true);
        progressBar13.setIndeterminate(true);
        progressBar14.setIndeterminate(true);
        progressBar15.setIndeterminate(true);

        //toggleButton.setChecked(false);
    }

    //------------------------------------- error -------------------------------------------------------//
    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);

        displayText.setText(errorMessage);
        yourMessage.speak("sorry " + errorMessage + " please try again", TextToSpeech.QUEUE_FLUSH, null);
        //welcomeSpeech.speak("hold to record message or tap once with two fingers to cancel", TextToSpeech.QUEUE_FLUSH, null);
        try {
            Thread.sleep(1000);
            hearInstructionsAgain.speak("do u wanna hear instructions again yes or no", TextToSpeech.QUEUE_FLUSH, null);
        }catch (Exception e){
            e.printStackTrace();
        }

        //assistant.startListening(recognizerIntent);
    }

    /*
    //-------------------------------- error handling STARTS -------------------------------------------------//
    //hear again method
    private void hearAgain(MotionEvent e) {

        int action = e.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:

                if (welcomeSpeech.isSpeaking()||yourMessage.isSpeaking()) {
                    // set the mTwoFingersTapped flag to TRUE when we tap with 2 fingers at once
                    mTwoFingersTapped = false;
                } else {

                    mTwoFingersTapped = true;
                    //Do u wanna listen to instructions again?
                    hearInstructionsAgain.speak("do u wanna hear instructions again single tap to confirm or tap with two fingers to go back to previous menu",TextToSpeech.QUEUE_FLUSH,null);

                    decide(e);
                }
                break;
        }//switch

    }

    // user is asked to decide to 'hear instructions' or 'go back to previous menu'
    private void decide(MotionEvent e) {


        // Single-Tap
        if(!welcomeSpeech.isSpeaking() || !yourMessage.isSpeaking() || !hearInstructionsAgain.isSpeaking()) {//when speech is not being played, then only gestures should work
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    initWelcomeVoice();
                    break;

            }//switch-ends
        }//if-ends

        // Two-fingers Single-Tap
        if(!welcomeSpeech.isSpeaking() || !yourMessage.isSpeaking() || !hearInstructionsAgain.isSpeaking()) {//when speech is not being played, then only TWO-finger gesture should work
            int action = e.getAction();
            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_POINTER_DOWN:

                    if (welcomeSpeech.isSpeaking() || yourMessage.isSpeaking() || hearInstructionsAgain.isSpeaking()) {
                        // set the mTwoFingersTapped flag to TRUE when we tap with 2 fingers at once
                        mTwoFingersTapped = false;
                    } else {

                        mTwoFingersTapped = true;
                        displayText.setText("back");
                        finish();
                        Intent i = new Intent(CreateMessageActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                    }
                    break;
            }//switch-ends
        }//if-ends
    }

    //------------------------------- error handling ENDS ------------------------------------------------------------//
*/

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");

        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches)
            text += result + "\n";

        String finalMessage = processing(text);

        displayText.setText(finalMessage);
        yourMessage.speak("your message is " + finalMessage, TextToSpeech.QUEUE_FLUSH, null);


    }

    private String processing(String text) {
        char[] array = text.toCharArray();
        char[] result= new char[array.length];

        int i=0;
        while(array[i] != '\n'){
            result[i]=array[i];
            i++;
        }

        //one more step to avoid loosing the last character before '\n'
        result[i]=array[i];
        i++;

        for(int j=i; j<result.length;j++)
            result[j]='@';

        int k = result.length - 1;

        while(result[k] == '@')
            k--;

        int length = k;

        char[] finalResult = new char[length];
        //0 to length -- final result
        for(int m=0;m<length;m++)
            finalResult[m]=result[m];

        String finalMessage = String.valueOf(finalResult);
        return finalMessage;
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);

        progressBar1.setProgress((int) rmsdB);
        progressBar2.setProgress((int) rmsdB - 1);
        progressBar3.setProgress((int) rmsdB - 2);
        progressBar4.setProgress((int) rmsdB - 3);
        progressBar5.setProgress((int) rmsdB - 3);

        progressBar6.setProgress((int) rmsdB - 2);
        progressBar7.setProgress((int) rmsdB - 1);
        progressBar8.setProgress((int) rmsdB - 4);
        progressBar9.setProgress((int) rmsdB - 4);
        progressBar10.setProgress((int) rmsdB - 1);

        progressBar11.setProgress((int) rmsdB - 3);
        progressBar12.setProgress((int) rmsdB - 2);
        progressBar13.setProgress((int) rmsdB - 1);
        progressBar14.setProgress((int) rmsdB - 3);
        progressBar15.setProgress((int) rmsdB - 2);
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    //------------------------------------------- Touch Events ---------------------------------------------------------------------------------------//
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //two-finger single tap
        if(!welcomeSpeech.isSpeaking() || !yourMessage.isSpeaking() || !hearInstructionsAgain.isSpeaking()) {//when speech is not being played, then only TWO-finger gesture should work
            int action = event.getAction();
            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_POINTER_DOWN:

                    if (welcomeSpeech.isSpeaking() || yourMessage.isSpeaking() || hearInstructionsAgain.isSpeaking()) {
                        // set the mTwoFingersTapped flag to TRUE when we tap with 2 fingers at once
                        mTwoFingersTapped = false;
                    } else {

                        mTwoFingersTapped = true;
                        displayText.setText("back");
                        finish();
                        Intent i = new Intent(CreateMessageActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);


                    }
                    break;
            }//switch
        }

        if(!welcomeSpeech.isSpeaking() || !yourMessage.isSpeaking() || !hearInstructionsAgain.isSpeaking()) {//when speech is not being played, then only hold-gesture should work
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startRecord();
                    break;

                case MotionEvent.ACTION_UP:
                    speech.stopListening();
                    stopRecord();
                    break;

            }//switch
        }

        //other gestures should not work, when voices are being played
        if(welcomeSpeech.isSpeaking() || yourMessage.isSpeaking() || hearInstructionsAgain.isSpeaking())
            return false;
        else
            return mGestureDetector.onTouchEvent(event);
    }

    private void startRecord() {
        progressBar1.setVisibility(View.VISIBLE);
        progressBar1.setIndeterminate(true);
        progressBar2.setVisibility(View.VISIBLE);
        progressBar2.setIndeterminate(true);
        progressBar3.setVisibility(View.VISIBLE);
        progressBar3.setIndeterminate(true);
        progressBar4.setVisibility(View.VISIBLE);
        progressBar4.setIndeterminate(true);
        progressBar5.setVisibility(View.VISIBLE);
        progressBar5.setIndeterminate(true);

        progressBar6.setVisibility(View.VISIBLE);
        progressBar6.setIndeterminate(true);
        progressBar7.setVisibility(View.VISIBLE);
        progressBar7.setIndeterminate(true);
        progressBar8.setVisibility(View.VISIBLE);
        progressBar8.setIndeterminate(true);
        progressBar9.setVisibility(View.VISIBLE);
        progressBar9.setIndeterminate(true);
        progressBar10.setVisibility(View.VISIBLE);
        progressBar10.setIndeterminate(true);

        progressBar11.setVisibility(View.VISIBLE);
        progressBar11.setIndeterminate(true);
        progressBar12.setVisibility(View.VISIBLE);
        progressBar12.setIndeterminate(true);
        progressBar13.setVisibility(View.VISIBLE);
        progressBar13.setIndeterminate(true);
        progressBar14.setVisibility(View.VISIBLE);
        progressBar14.setIndeterminate(true);
        progressBar15.setVisibility(View.VISIBLE);
        progressBar15.setIndeterminate(true);

        speech.startListening(recognizerIntent);
    }

    private void stopRecord() {
        stopAnimations();
        speech.stopListening();
    }

    private void stopAnimations() {
        progressBar1.setIndeterminate(false);
        progressBar1.setVisibility(View.INVISIBLE);
        progressBar2.setIndeterminate(false);
        progressBar2.setVisibility(View.INVISIBLE);
        progressBar3.setIndeterminate(false);
        progressBar3.setVisibility(View.INVISIBLE);
        progressBar4.setIndeterminate(false);
        progressBar4.setVisibility(View.INVISIBLE);
        progressBar5.setIndeterminate(false);
        progressBar5.setVisibility(View.INVISIBLE);

        progressBar6.setIndeterminate(false);
        progressBar6.setVisibility(View.INVISIBLE);
        progressBar7.setIndeterminate(false);
        progressBar7.setVisibility(View.INVISIBLE);
        progressBar8.setIndeterminate(false);
        progressBar8.setVisibility(View.INVISIBLE);
        progressBar9.setIndeterminate(false);
        progressBar9.setVisibility(View.INVISIBLE);
        progressBar10.setIndeterminate(false);
        progressBar10.setVisibility(View.INVISIBLE);

        progressBar11.setIndeterminate(false);
        progressBar11.setVisibility(View.INVISIBLE);
        progressBar12.setIndeterminate(false);
        progressBar12.setVisibility(View.INVISIBLE);
        progressBar13.setIndeterminate(false);
        progressBar13.setVisibility(View.INVISIBLE);
        progressBar14.setIndeterminate(false);
        progressBar14.setVisibility(View.INVISIBLE);
        progressBar15.setIndeterminate(false);
        progressBar15.setVisibility(View.INVISIBLE);
    }



    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        //Toast.makeText(getApplicationContext(),"single tap",Toast.LENGTH_SHORT).show();
        return true;

    }


    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}