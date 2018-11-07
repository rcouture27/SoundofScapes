package edu.nhti.ist218.soundscapeproject;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Spinner primarySpinner;
    private Spinner secondarySpinner;
    private Spinner tertiarySpinner;

    private SeekBar primaryVolume;
    private SeekBar secondaryVolume;
    private SeekBar tertiaryVolume;

    private MediaPlayer rain_sound;
    private MediaPlayer ocean_sound;
    private MediaPlayer fire_sound;
    private MediaPlayer thunder_sound;
    private MediaPlayer seagulls_sound;
    private MediaPlayer crickets_sound;
    private MediaPlayer birds_sound;
    private MediaPlayer harbor_sound;
    private MediaPlayer leaves_sound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        //initialize mp3 files
        rain_sound = MediaPlayer.create(this, R.raw.rain_sound);
        ocean_sound = MediaPlayer.create(this, R.raw.ocean_sound);
        fire_sound = MediaPlayer.create(this, R.raw.fire_sound);

        thunder_sound = MediaPlayer.create(this, R.raw.thunder_sound);
        seagulls_sound = MediaPlayer.create(this, R.raw.seagulls_sound);
        crickets_sound = MediaPlayer.create(this, R.raw.crickets_sound);

        birds_sound = MediaPlayer.create(this, R.raw.birds_chirping_sound);
        harbor_sound = MediaPlayer.create(this, R.raw.harbor_bells_sound);
        leaves_sound = MediaPlayer.create(this, R.raw.rustling_leaves_sound);



        //create audio arrays
        final MediaPlayer[] primaryAudio = createAudioArray(rain_sound, ocean_sound, fire_sound);
        final MediaPlayer[] secondaryAudio = createAudioArray(thunder_sound, seagulls_sound, crickets_sound);
        final MediaPlayer[] tertiaryAudio = createAudioArray(birds_sound, harbor_sound, leaves_sound);

        //initializes string array from strings xml
        String[] primarySounds = getResources().getStringArray(R.array.primary_sounds);
        String[] secondarySounds = getResources().getStringArray(R.array.secondary_sounds);
        String[] tertiarySounds = getResources().getStringArray(R.array.tertiary_sounds);

        //initializes spinners
        primarySpinner = findViewById(R.id.primarySpinner);
        secondarySpinner = findViewById(R.id.secondarySpinner);
        tertiarySpinner = findViewById(R.id.tertiarySpinner);

        //populates spinners with string array
        populateSpinner(primarySpinner, primarySounds, primaryAudio);
        populateSpinner(secondarySpinner, secondarySounds, secondaryAudio);
        populateSpinner(tertiarySpinner, tertiarySounds,tertiaryAudio);



        //initialize seek bars
        primaryVolume = findViewById(R.id.primarySeekBar);
        secondaryVolume = findViewById(R.id.secondarySeekBar);
        tertiaryVolume = findViewById(R.id.tertiarySeekBar);



        configureStartButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_1) {
            Toast.makeText(MainActivity.this, "Item 1 selected", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.item_2) {
            Toast.makeText(MainActivity.this, "Item 2 selected", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void configureStartButton() {

        Button startButton = findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PlayBackActivity.class));
            }
        });
    }

    private void populateSpinner(Spinner spinner, String[] soundsArray, final MediaPlayer[] audioArray) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item, soundsArray){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                switch (position) {
                    case 1:
                        if (audioArray[1].isPlaying()) {
                            audioArray[1].pause();
                        }
                        if (audioArray[2].isPlaying()) {
                            audioArray[2].pause();
                        }
                        audioArray[0].start();

                        Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        if (audioArray[0].isPlaying()) {
                            audioArray[0].pause();
                        }
                        if (audioArray[2].isPlaying()) {
                            audioArray[2].pause();
                        }
                        audioArray[1].start();

                        Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        if (audioArray[0].isPlaying()) {
                            audioArray[0].pause();
                        }
                        if (audioArray[1].isPlaying()) {
                            audioArray[1].pause();
                        }
                        audioArray[2].start();

                        Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private MediaPlayer[] createAudioArray(MediaPlayer sound1, MediaPlayer sound2, MediaPlayer sound3) {
        MediaPlayer[] audioArray = new MediaPlayer[3];
        audioArray[0] = sound1;
        audioArray[1] = sound2;
        audioArray[2] = sound3;

        return audioArray;
    }
}
