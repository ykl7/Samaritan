package com.darkarmy.ykl.samaritan;

import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class CommandEntryActivity extends AppCompatActivity {

    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private AudioManager mAudioManager;
    private boolean mPhoneIsSilent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_command_entry);

        txtSpeechInput = (TextView) findViewById(R.id.textSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.speechButton);
        mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        checkIfPhoneIsSilent();
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
    }

    private void promptSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
            switchToMovies();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                    // put in 2 second time here to display recognized text - use handler (wait then change activity)
                    Log.d("msg", "MSG" + result.get(0));
                    if (result.get(0).equalsIgnoreCase("movies"))  {
                        switchToMovies();
                    }
                    else if (result.get(0).equalsIgnoreCase("card"))  {
                        openCards();
                    }
                    else if (result.get(0).equalsIgnoreCase("aadhar"))  {
                        aadharReader();
                    }
                    else if (result.get(0).equalsIgnoreCase("silence"))  {
                        if (mPhoneIsSilent) {
                            //change back to normal mode
                            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                            mPhoneIsSilent = false;
                        }
                        else {
                            //change to silent mode
                            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                            mPhoneIsSilent = true;
                        }
                    }
                }
                break;
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkIfPhoneIsSilent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void switchToMovies ()  {
        Intent intent = new Intent(this, MovieActivity.class);
        startActivity(intent);
    }

    public void openCards ()  {
        Intent intent = new Intent(this, NFCReader.class);
        startActivity(intent);
    }

    public void aadharReader ()  {
        Intent intent = new Intent(this, AadharInfo.class);
        startActivity(intent);
    }

    private void checkIfPhoneIsSilent() {
        int ringermode = mAudioManager.getRingerMode();
        if (ringermode == AudioManager.RINGER_MODE_SILENT) {
            mPhoneIsSilent = true;
        }
        else {
            mPhoneIsSilent = false;
        }
    }
}
