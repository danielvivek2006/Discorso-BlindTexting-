package com.ead.discorso;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;

public class RecordContactActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener,RecognitionListener {
    TextView message;
    GestureDetector mGestureDetector;
    TextToSpeech welcomeSpeech, yourMessage, hearInstructions, pleaseWait;
    boolean mTwoFingersTapped;
    SpeechRecognizer speech;
    Intent recognizerIntent;
    private ProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5, progressBar6, progressBar7, progressBar8, progressBar9, progressBar10, progressBar11, progressBar12, progressBar13, progressBar14, progressBar15, progressBar16, progressBar17, progressBar18, progressBar19, progressBar20, progressBar21, progressBar22, progressBar23, progressBar24, progressBar25, progressBar26, progressBar27, progressBar28, progressBar29, progressBar30, progressBar31;
    //final recorded message
    String contactName, phoneNumber, finalResult;

    //arraylists for saving all contact names with their respective phone numbers
    ArrayList<String> phoneNumbersList, contactNamesList;

    MediaPlayer mp;

    private Uri uriContact;
    private String contactID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_contact);

        mGestureDetector = new GestureDetector(this);

        message = (TextView) findViewById(R.id.contact);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/RobotoSlab-Light.ttf");
        message.setTypeface(tf);

        initWelcomeVoice();

        //speech recognizers
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        //intents
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());


        //initialize progress dialogs
        initProgressDialogs();

        //init music
        mp = MediaPlayer.create(getApplicationContext(), R.raw.please_wait_discorso);
        //play music
        mp.start();

        new AsyncTask<Void, Void, Void>() {
            int i = 0;


            @Override
            protected Void doInBackground(Void... params) {

                //get all contacts
                //fetchContacts();
                    readContacts();

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {


                //welcomeSpeech.speak("contacts are now ready...",TextToSpeech.QUEUE_FLUSH,null);
                Log.d("onPostExecute", "OK READY");

            }//End of onPostExecute method

            @Override
            protected void onPreExecute() {

                Log.d("onPreExecute", "PLEASE WAIT");
            }// End of onPreExecute method

            @Override
            protected void onProgressUpdate(Void... params) {
                //welcomeSpeech.speak("please wait...",TextToSpeech.QUEUE_FLUSH,null);

            }


        }.execute((Void[]) null);


    }

    private void initProgressDialogs() {
        //progress Bars
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar4 = (ProgressBar) findViewById(R.id.progressBar4);
        progressBar5 = (ProgressBar) findViewById(R.id.progressBar5);
        progressBar6 = (ProgressBar) findViewById(R.id.progressBar6);
        progressBar7 = (ProgressBar) findViewById(R.id.progressBar7);
        progressBar8 = (ProgressBar) findViewById(R.id.progressBar8);
        progressBar9 = (ProgressBar) findViewById(R.id.progressBar9);
        progressBar10 = (ProgressBar) findViewById(R.id.progressBar10);
        progressBar11 = (ProgressBar) findViewById(R.id.progressBar11);
        progressBar12 = (ProgressBar) findViewById(R.id.progressBar12);
        progressBar13 = (ProgressBar) findViewById(R.id.progressBar13);
        progressBar14 = (ProgressBar) findViewById(R.id.progressBar14);
        progressBar15 = (ProgressBar) findViewById(R.id.progressBar15);
        progressBar16 = (ProgressBar) findViewById(R.id.progressBar16);
        progressBar17 = (ProgressBar) findViewById(R.id.progressBar17);
        progressBar18 = (ProgressBar) findViewById(R.id.progressBar18);
        progressBar19 = (ProgressBar) findViewById(R.id.progressBar19);
        progressBar20 = (ProgressBar) findViewById(R.id.progressBar20);
        progressBar21 = (ProgressBar) findViewById(R.id.progressBar21);
        progressBar22 = (ProgressBar) findViewById(R.id.progressBar22);
        progressBar23 = (ProgressBar) findViewById(R.id.progressBar23);
        progressBar24 = (ProgressBar) findViewById(R.id.progressBar24);
        progressBar25 = (ProgressBar) findViewById(R.id.progressBar25);
        progressBar26 = (ProgressBar) findViewById(R.id.progressBar26);
        progressBar27 = (ProgressBar) findViewById(R.id.progressBar27);
        progressBar28 = (ProgressBar) findViewById(R.id.progressBar28);
        progressBar29 = (ProgressBar) findViewById(R.id.progressBar29);
        progressBar30 = (ProgressBar) findViewById(R.id.progressBar30);
        progressBar31 = (ProgressBar) findViewById(R.id.progressBar31);

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
        progressBar16.setClickable(false);
        progressBar17.setClickable(false);
        progressBar18.setClickable(false);
        progressBar19.setClickable(false);
        progressBar20.setClickable(false);
        progressBar21.setClickable(false);
        progressBar22.setClickable(false);
        progressBar23.setClickable(false);
        progressBar24.setClickable(false);
        progressBar25.setClickable(false);
        progressBar26.setClickable(false);
        progressBar27.setClickable(false);
        progressBar28.setClickable(false);
        progressBar29.setClickable(false);
        progressBar30.setClickable(false);
        progressBar31.setClickable(false);

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
        progressBar16.setVisibility(View.INVISIBLE);
        progressBar17.setVisibility(View.INVISIBLE);
        progressBar18.setVisibility(View.INVISIBLE);
        progressBar19.setVisibility(View.INVISIBLE);
        progressBar20.setVisibility(View.INVISIBLE);
        progressBar21.setVisibility(View.INVISIBLE);
        progressBar22.setVisibility(View.INVISIBLE);
        progressBar23.setVisibility(View.INVISIBLE);
        progressBar24.setVisibility(View.INVISIBLE);
        progressBar25.setVisibility(View.INVISIBLE);
        progressBar26.setVisibility(View.INVISIBLE);
        progressBar27.setVisibility(View.INVISIBLE);
        progressBar28.setVisibility(View.INVISIBLE);
        progressBar29.setVisibility(View.INVISIBLE);
        progressBar30.setVisibility(View.INVISIBLE);
        progressBar31.setVisibility(View.INVISIBLE);

    }

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
        welcomeSpeech.setSpeechRate(0.8f);

        yourMessage = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = yourMessage.setLanguage(Locale.US);

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
        yourMessage.setSpeechRate(0.8f);

        hearInstructions = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = hearInstructions.setLanguage(Locale.US);

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
        hearInstructions.setSpeechRate(0.8f);
    }

    private void speakOptions() {
        //welcomeSpeech.speak("hold with your finger to record your message ..or tap once with two fingers to go back to previous menu", TextToSpeech.QUEUE_FLUSH, null);
        welcomeSpeech.speak("whom do u wanna send message... Tap Once To record phone number or contact name.... or Tap once with two fingers to go back", TextToSpeech.QUEUE_FLUSH, null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_record_message, menu);
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

    //----------------------------------- Touch Events --------------------------------------------------//
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //two-fingers tap once gesture
        if (!welcomeSpeech.isSpeaking() || !yourMessage.isSpeaking() | !hearInstructions.isSpeaking() || !mp.isPlaying()) {
            int action = event.getAction();
            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_POINTER_DOWN:

                    if (welcomeSpeech.isSpeaking() || yourMessage.isSpeaking() || hearInstructions.isSpeaking()) {
                        // set the mTwoFingersTapped flag to TRUE when we tap with 2 fingers at once
                        mTwoFingersTapped = false;
                    } else {

                        mTwoFingersTapped = true;
                        message.setText("");
                        hearInstructions.speak("ok going back", TextToSpeech.QUEUE_FLUSH, null);


                        Thread t = new Thread() {
                            public void run() {
                                try {
                                    sleep(2000);
                                    finish();
                                    final Intent i = new Intent(RecordContactActivity.this, MainActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                } catch (Exception e) {

                                }
                            }
                        };
                        t.start();
                    }
                    break;
            }//switch
        }
        //other gestures should not work, when voices are being played
        if (welcomeSpeech.isSpeaking() || yourMessage.isSpeaking() || hearInstructions.isSpeaking() || mp.isPlaying())
            return false;
        else
            return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Don't forget to shutdown tts!
        if (welcomeSpeech != null) {
            welcomeSpeech.stop();
            welcomeSpeech.shutdown();
        }
        if (yourMessage != null) {
            yourMessage.stop();
            yourMessage.shutdown();
        }
        if (hearInstructions != null) {
            hearInstructions.stop();
            hearInstructions.shutdown();
        }
        speech.stopListening();
    }

    //stop playing voice when paused (onPause() state)
    @Override
    protected void onPause() {
        super.onPause();

        if (welcomeSpeech != null) {
            welcomeSpeech.stop();
            welcomeSpeech.shutdown();
        }
        if (yourMessage != null) {
            yourMessage.stop();
            yourMessage.shutdown();
        }
        if (hearInstructions != null) {
            hearInstructions.stop();
            hearInstructions.shutdown();
        }
        speech.stopListening();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initWelcomeVoice();
    }

    //-------------------------------- Gesture Listeners ---------------------------------------------------//
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        message.setText("");

        if (yourMessage.isSpeaking())
            return false;
        else
            return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        message.setText("");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        message.setText("");
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        message.setText("");

        if (yourMessage.isSpeaking()) {

            return false;
        } else {
            startRecord();
            return true;
        }

    }

    public void startRecord() {
        speech.startListening(recognizerIntent);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        message.setText("");

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("","inside onFling swipe");
        //it should not work when text to speech is going on
        if (!hearInstructions.isSpeaking() || !welcomeSpeech.isSpeaking() || !yourMessage.isSpeaking()) {
            if (finalResult != null && phoneNumber != null) {
                message.setText("");
                hearInstructions.speak("Ok phone number confirmed", TextToSpeech.QUEUE_FLUSH, null);
                //call next activity & set final message, contact number to intent
                final Intent i = new Intent(RecordContactActivity.this, RecordMessageActivity.class);
                Bundle b = new Bundle();
                b.putString("phone_number", phoneNumber);
                b.putString("contact_name", contactName);
                i.putExtras(b);

                Thread t = new Thread() {
                    public void run() {
                        try {

                            sleep(3000);
                            startActivity(i);

                        } catch (Exception e) {

                        }
                    }
                };
                t.start();

            }

            if (finalResult != null && phoneNumber == null) {

                hearInstructions.speak("sorry you cannot proceed as phone number is not recorded... tap once again to record phone number", TextToSpeech.QUEUE_FLUSH, null);
            }

            return true;
        } else
            return false;
    }

    //------------------------------------------- Recognition Listener ------------------------------------------------------//
    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {
        progressBar1.setIndeterminate(false);
        progressBar1.setVisibility(View.VISIBLE);
        progressBar1.setMax(10);
        progressBar2.setIndeterminate(false);
        progressBar2.setVisibility(View.VISIBLE);
        progressBar2.setMax(10);
        progressBar3.setIndeterminate(false);
        progressBar3.setVisibility(View.VISIBLE);
        progressBar3.setMax(10);
        progressBar4.setIndeterminate(false);
        progressBar4.setVisibility(View.VISIBLE);
        progressBar4.setMax(10);
        progressBar5.setIndeterminate(false);
        progressBar5.setVisibility(View.VISIBLE);
        progressBar5.setMax(10);

        progressBar6.setIndeterminate(false);
        progressBar6.setVisibility(View.VISIBLE);
        progressBar6.setMax(10);
        progressBar7.setIndeterminate(false);
        progressBar7.setVisibility(View.VISIBLE);
        progressBar7.setMax(10);
        progressBar8.setIndeterminate(false);
        progressBar8.setVisibility(View.VISIBLE);
        progressBar8.setMax(10);
        progressBar9.setIndeterminate(false);
        progressBar9.setVisibility(View.VISIBLE);
        progressBar9.setMax(10);
        progressBar10.setIndeterminate(false);
        progressBar10.setVisibility(View.VISIBLE);
        progressBar10.setMax(10);

        progressBar11.setIndeterminate(false);
        progressBar11.setVisibility(View.VISIBLE);
        progressBar11.setMax(10);
        progressBar12.setIndeterminate(false);
        progressBar12.setVisibility(View.VISIBLE);
        progressBar12.setMax(10);
        progressBar13.setIndeterminate(false);
        progressBar13.setVisibility(View.VISIBLE);
        progressBar13.setMax(10);
        progressBar14.setIndeterminate(false);
        progressBar14.setVisibility(View.VISIBLE);
        progressBar14.setMax(10);
        progressBar15.setIndeterminate(false);
        progressBar15.setVisibility(View.VISIBLE);
        progressBar15.setMax(10);

        progressBar16.setIndeterminate(false);
        progressBar16.setVisibility(View.VISIBLE);
        progressBar16.setMax(10);
        progressBar17.setIndeterminate(false);
        progressBar17.setVisibility(View.VISIBLE);
        progressBar17.setMax(10);
        progressBar18.setIndeterminate(false);
        progressBar18.setVisibility(View.VISIBLE);
        progressBar18.setMax(10);
        progressBar19.setIndeterminate(false);
        progressBar19.setVisibility(View.VISIBLE);
        progressBar19.setMax(10);
        progressBar20.setIndeterminate(false);
        progressBar20.setVisibility(View.VISIBLE);
        progressBar20.setMax(10);

        progressBar21.setIndeterminate(false);
        progressBar21.setVisibility(View.VISIBLE);
        progressBar21.setMax(10);
        progressBar22.setIndeterminate(false);
        progressBar22.setVisibility(View.VISIBLE);
        progressBar22.setMax(10);
        progressBar23.setIndeterminate(false);
        progressBar23.setVisibility(View.VISIBLE);
        progressBar23.setMax(10);
        progressBar24.setIndeterminate(false);
        progressBar24.setVisibility(View.VISIBLE);
        progressBar24.setMax(10);
        progressBar25.setIndeterminate(false);
        progressBar25.setVisibility(View.VISIBLE);
        progressBar25.setMax(10);

        progressBar26.setIndeterminate(false);
        progressBar26.setVisibility(View.VISIBLE);
        progressBar26.setMax(10);
        progressBar27.setIndeterminate(false);
        progressBar27.setVisibility(View.VISIBLE);
        progressBar27.setMax(10);
        progressBar28.setIndeterminate(false);
        progressBar28.setVisibility(View.VISIBLE);
        progressBar28.setMax(10);
        progressBar29.setIndeterminate(false);
        progressBar29.setVisibility(View.VISIBLE);
        progressBar29.setMax(10);
        progressBar30.setIndeterminate(false);
        progressBar30.setVisibility(View.VISIBLE);
        progressBar30.setMax(10);

        progressBar31.setIndeterminate(false);
        progressBar31.setVisibility(View.VISIBLE);
        progressBar31.setMax(10);
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        progressBar1.setProgress((int) rmsdB);
        progressBar2.setProgress((int) rmsdB - 0);
        progressBar3.setProgress((int) rmsdB - 0);
        progressBar4.setProgress((int) rmsdB - 0);
        progressBar5.setProgress((int) rmsdB - 0);

        progressBar6.setProgress((int) rmsdB - 0);
        progressBar7.setProgress((int) rmsdB - 0);
        progressBar8.setProgress((int) rmsdB - 0);
        progressBar9.setProgress((int) rmsdB - 0);
        progressBar10.setProgress((int) rmsdB - 0);

        progressBar11.setProgress((int) rmsdB - 0);
        progressBar12.setProgress((int) rmsdB - 0);
        progressBar13.setProgress((int) rmsdB - 0);
        progressBar14.setProgress((int) rmsdB - 0);
        progressBar15.setProgress((int) rmsdB - 0);

        progressBar16.setProgress((int) rmsdB);
        progressBar17.setProgress((int) rmsdB - 0);
        progressBar18.setProgress((int) rmsdB - 0);
        progressBar19.setProgress((int) rmsdB - 0);
        progressBar20.setProgress((int) rmsdB - 0);

        progressBar21.setProgress((int) rmsdB - 0);
        progressBar22.setProgress((int) rmsdB - 0);
        progressBar23.setProgress((int) rmsdB - 0);
        progressBar24.setProgress((int) rmsdB - 0);
        progressBar25.setProgress((int) rmsdB - 0);

        progressBar26.setProgress((int) rmsdB - 0);
        progressBar27.setProgress((int) rmsdB - 0);
        progressBar28.setProgress((int) rmsdB - 0);
        progressBar29.setProgress((int) rmsdB - 0);
        progressBar30.setProgress((int) rmsdB - 0);
        progressBar31.setProgress((int) rmsdB - 0);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {
        progressBar1.setIndeterminate(true);
        progressBar1.setVisibility(View.INVISIBLE);
        progressBar2.setIndeterminate(true);
        progressBar2.setVisibility(View.INVISIBLE);
        progressBar3.setIndeterminate(true);
        progressBar3.setVisibility(View.INVISIBLE);
        progressBar4.setIndeterminate(true);
        progressBar4.setVisibility(View.INVISIBLE);
        progressBar5.setIndeterminate(true);
        progressBar5.setVisibility(View.INVISIBLE);

        progressBar6.setIndeterminate(true);
        progressBar6.setVisibility(View.INVISIBLE);
        progressBar7.setIndeterminate(true);
        progressBar7.setVisibility(View.INVISIBLE);
        progressBar8.setIndeterminate(true);
        progressBar8.setVisibility(View.INVISIBLE);
        progressBar9.setIndeterminate(true);
        progressBar9.setVisibility(View.INVISIBLE);
        progressBar10.setIndeterminate(true);
        progressBar10.setVisibility(View.INVISIBLE);

        progressBar11.setIndeterminate(true);
        progressBar11.setVisibility(View.INVISIBLE);
        progressBar12.setIndeterminate(true);
        progressBar12.setVisibility(View.INVISIBLE);
        progressBar13.setIndeterminate(true);
        progressBar13.setVisibility(View.INVISIBLE);
        progressBar14.setIndeterminate(true);
        progressBar14.setVisibility(View.INVISIBLE);
        progressBar15.setIndeterminate(true);
        progressBar15.setVisibility(View.INVISIBLE);

        progressBar16.setIndeterminate(true);
        progressBar16.setVisibility(View.INVISIBLE);
        progressBar17.setIndeterminate(true);
        progressBar17.setVisibility(View.INVISIBLE);
        progressBar18.setIndeterminate(true);
        progressBar18.setVisibility(View.INVISIBLE);
        progressBar19.setIndeterminate(true);
        progressBar19.setVisibility(View.INVISIBLE);
        progressBar20.setIndeterminate(true);
        progressBar20.setVisibility(View.INVISIBLE);

        progressBar21.setIndeterminate(true);
        progressBar21.setVisibility(View.INVISIBLE);
        progressBar22.setIndeterminate(true);
        progressBar22.setVisibility(View.INVISIBLE);
        progressBar23.setIndeterminate(true);
        progressBar23.setVisibility(View.INVISIBLE);
        progressBar24.setIndeterminate(true);
        progressBar24.setVisibility(View.INVISIBLE);
        progressBar25.setIndeterminate(true);
        progressBar25.setVisibility(View.INVISIBLE);

        progressBar26.setIndeterminate(true);
        progressBar26.setVisibility(View.INVISIBLE);
        progressBar27.setIndeterminate(true);
        progressBar27.setVisibility(View.INVISIBLE);
        progressBar28.setIndeterminate(true);
        progressBar28.setVisibility(View.INVISIBLE);
        progressBar29.setIndeterminate(true);
        progressBar29.setVisibility(View.INVISIBLE);
        progressBar30.setIndeterminate(true);
        progressBar30.setVisibility(View.INVISIBLE);

        progressBar31.setIndeterminate(true);
        progressBar31.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        message.setText(errorMessage);
        yourMessage.speak("sorry.. " + errorMessage + " please try again", TextToSpeech.QUEUE_FLUSH, null);

    }

    @Override
    public void onResults(Bundle results) {
        Log.i("Speech:", "onResults");

        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches)
            text += result + "\n";

        finalResult = processing(text);


        //check whether finalResult contains only digits & no TEXT!!!
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);

        //PHONE NUMBER
        if (pattern.matcher(finalResult).matches()) {

            //phoneNumber = finalResult;
            //remove white spaces from phone number if any
            phoneNumber = removeWhiteSpaces(finalResult);

            //phone number before getContactName
            String tempPhoneNumber = phoneNumber;
            message.setText(tempPhoneNumber);

            contactName = getContactName(this, phoneNumber);
            //phone number after getContactName; phoneNumber value may get changed inside getContactName() method if substring number is given
            message.append("\n"+phoneNumber);

            if (contactName == null) {

                yourMessage.speak("phone number you've recorded is.. " + tempPhoneNumber + "... and phone number found is.. " + phoneNumber + "... sorry failed to recognize contact name", TextToSpeech.QUEUE_FLUSH, null);
                hearInstructions.speak("swipe once to confirm this phone number.. or tap once again to record phone number", TextToSpeech.QUEUE_FLUSH, null);

            } else {
                message.append(" " + contactName);
                yourMessage.speak("phone number you've recorded is.. " + tempPhoneNumber + "... and phone number found is.. " + phoneNumber + "... and contact name is.. " + contactName, TextToSpeech.QUEUE_FLUSH, null);
                hearInstructions.speak("swipe once to confirm this phone number.. or tap once again to record phone number", TextToSpeech.QUEUE_FLUSH, null);
            }
        }

        //CONTACT NAME
        else {
            contactName = finalResult;

            String num = getPhoneNumber(this, contactName);

            message.setText(finalResult);
            message.append("\n"+contactName);
            phoneNumber = num;
            //if number is not found, NULL is returned; so do not process further when NULL is returned
            if (phoneNumber == null) {

                message.append(" not found");
                yourMessage.speak("contact name you've recorded is.. " + finalResult + "... and contact name recognized is.. " + contactName + "... sorry phone number was not found", TextToSpeech.QUEUE_FLUSH, null);
            } else {
                phoneNumber = removeWhiteSpaces(phoneNumber);
                message.append(" " + phoneNumber);
                yourMessage.speak("contact name you've recorded is.. " + finalResult + "... and contact name recognized is.. " + contactName + "... and phone number is.. " + num, TextToSpeech.QUEUE_FLUSH, null);
            }

            hearInstructions.speak("swipe once to confirm this contact.. or tap once again to record phone number", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    //retrieving contact name using phone number
    public String getContactName(Context c, String phoneNumberValue) {

        //even if partial number is recorded, guess full number & then retreive contact name
        for (int i = 0; i < phoneNumbersList.size(); i++) {
            String phoneValue = phoneNumbersList.get(i);


            if (phoneValue.contains(phoneNumberValue)) {
                phoneNumberValue = phoneValue;
                //phoneNumber to be displayed
                phoneNumber = phoneNumberValue;
                Log.d("" + phoneValue, "" + phoneNumberValue);
            }
        }

        ContentResolver cr = c.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cur = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cur == null) {
            return null;
        }
        String contact = null;
        if (cur.moveToFirst()) {
            contact = cur.getString(cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }
        if (cur != null && !cur.isClosed()) {
            cur.close();
        }
        return contact;

    }

    //retrieving phone number using contact name
    public String getPhoneNumber(Context c, String name) {
        String phone=null;

        for (int i = 0; i < phoneNumbersList.size(); i++) {
            boolean flag;
            String nameValue = contactNamesList.get(i);

            //make contact name (from list), contact name (from voice) to lower case
            nameValue = nameValue.toLowerCase();
            name = name.toLowerCase();

            //first check directly with string
            flag = directCheck(name, nameValue);

            if (flag ) {//if direct check; then flag value is TRUE
                contactName = getParentName(contactNamesList.get(i));
                //Toast.makeText(getApplicationContext(),contactName + " " + phoneNumbersList.get(i),Toast.LENGTH_SHORT).show();
                Log.d("" + name, "" + nameValue);

                phone = phoneNumbersList.get(i);
                return phone;
            }

            //next if above check fails, check for substring
            else {
                if (nameValue.contains(name) ) {
                    contactName = getParentName(contactNamesList.get(i));
                    //Toast.makeText(getApplicationContext(),contactName + " " + phoneNumbersList.get(i),Toast.LENGTH_SHORT).show();
                    Log.d("" + name, "" + nameValue);

                    phone =  phoneNumbersList.get(i);
                }
            }



        }//for-loop

        if(phone==null)
        return null;

        else
            return phone;
    }

    private boolean directCheck(String name, String nameValue){

        if (nameValue.equalsIgnoreCase(name) ) {
            Log.d("","inside direct check: TRUE");
            return true;
        }
        else {
            Log.d("","inside direct check: FALSE");
            return false;
        }
    }

    private String getParentName(String s) {
        return s;
    }

    public void fetchContacts() {


        String fetchPhoneNumber = null;

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;


        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;



        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

        // Loop for every contact in the phone
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                //------------ process contact name - remove white spaces ------------------//
                if (name != null)
                    name = removeWhiteSpaces(name);
                else if (name == null) {
                    name = "unknown";
                }
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0) {

                    contactNamesList.add(name);
                    //output.append("First Name:" + name);

                    // Query and loop for every phone number of the contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);

                    phoneCursor.moveToNext();

                    //to fetch only one number from a contact id
                    int numbersCount = 0;
                    while (phoneCursor.moveToNext()) {
                        fetchPhoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        //------------ process phone number - remove white spaces ------------------//
                        fetchPhoneNumber = removeWhiteSpaces(fetchPhoneNumber);

                        //numbersCount++;

                        //if(numbersCount==1)
                        //{
                        phoneNumbersList.add(fetchPhoneNumber);

                        // break;
                        //}

                    }//while

                    phoneCursor.close();


                }//inner-if


            }//while

        }//if


        for (int i = 0; i < phoneNumbersList.size(); i++) {
            Log.d("[" + i + "]" + " " + contactNamesList.get(i), " " + phoneNumbersList.get(i));
        }

        Log.d("contactNamesList size", "" + contactNamesList.size());
        Log.d("phoneNumbersList size", "" + phoneNumbersList.size());

    }

    private String processing(String text) {
        char[] array = text.toCharArray();
        char[] result = new char[array.length];

        int i = 0;
        while (array[i] != '\n') {
            result[i] = array[i];
            i++;
        }

        //one more step to avoid loosing the last character before '\n'
        result[i] = array[i];
        i++;

        for (int j = i; j < result.length; j++)
            result[j] = '@';

        int k = result.length - 1;

        while (result[k] == '@')
            k--;

        int length = k;

        char[] resultArray = new char[length];
        //0 to length -- final result
        for (int m = 0; m < length; m++)
            resultArray[m] = result[m];

        String finalMessage = String.valueOf(resultArray);


        //if finalMessage contains any white spaces remove them -- VALID ONLY FOR RECORDING CONTACT/NUMBER
        finalMessage = removeWhiteSpaces(finalMessage);

        return finalMessage;
    }

    private String removeWhiteSpaces(String number) {
        //String query = "814 321 1572";

        char[] array = number.toCharArray();
        char[] result;

        //shifting spaces to the end
        //LOGIC: if any white spaces are present shift array values to left one-after-the-other && put 'space' to the last value of array
        //eg: '814 321 1572' ---------> '8143211572  '
        for (int i = 0; i < array.length; i++) {
            if (array[i] == ' ' || array[i] == '-') {
                int index = i;
                while (index < array.length - 1) {
                    array[index] = array[index + 1];
                    index++;
                }
                array[index] = ' ';
            }
        }

        //to get size of new array
        int count = 0;

        //increment count value only if any space is not encountered... (spaces are in the end of array, that means value of count gives us the exact size of array excluding white spaces)
        //eg: '8143211572  ' is the array, but we want size of '8143211572' excluding spaces
        for (int i = 0; i < array.length; i++) {
            if (array[i] != ' ') {
                count++;

            }
        }


        //result array
        result = new char[count];

        //eg: now result array is: '8143211572'
        for (int j = 0; j < result.length; j++)
            result[j] = array[j];

        String correctNumber = String.valueOf(result);

        return correctNumber;
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }

    //------------------------------------ get Error -------------------------------------------------//
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
                Log.d("",""+SpeechRecognizer.ERROR_RECOGNIZER_BUSY);
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

    public void readContacts(){
        //lists
        phoneNumbersList = new ArrayList<String>();
        contactNamesList = new ArrayList<String>();

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                //------------ process contact name - remove white spaces ------------------//
                if (name != null)
                    name = removeWhiteSpaces(name);

                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {


                    // get the phone number
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phone = pCur.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //Log.d("","name : " + name + ", ID : " + id+ ", num : "+phone);

                        //------------ process phone number - remove white spaces ------------------//
                        phone = removeWhiteSpaces(phone);

                        contactNamesList.add(name);
                        phoneNumbersList.add(phone);
                    }
                    pCur.close();



                }
            }
        }

        for(int i=0;i<contactNamesList.size();i++)
            Log.d("","contact: "+contactNamesList.get(i)+" phone: "+phoneNumbersList.get(i));

        Log.d("","contactNames size: "+contactNamesList.size());
        Log.d("","phoneNumbers size: "+phoneNumbersList.size());

        //stop music
        mp.stop();
    }

}
