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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private SeekBar[] volumeSliders = { null, null, null };

    private static MediaPlayer[][] audio;

    private EditText userTitle;

    private static MediaPlayer[] mediaPlayers = { null, null, null };


    private final static int maxVolume = 100;

    private PreviewMode previewMode = PreviewMode.None;

    public static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//sdklfhsdklf
        //initialize mp3 files
        MediaPlayer rain_sound = MediaPlayer.create(this, R.raw.rain_sound);
        MediaPlayer ocean_sound = MediaPlayer.create(this, R.raw.ocean_sound);
        MediaPlayer fire_sound = MediaPlayer.create(this, R.raw.fire_sound);

        MediaPlayer thunder_sound = MediaPlayer.create(this, R.raw.thunder_sound);
        MediaPlayer seagulls_sound = MediaPlayer.create(this, R.raw.seagulls_sound);
        MediaPlayer crickets_sound = MediaPlayer.create(this, R.raw.crickets_sound);

        MediaPlayer birds_sound = MediaPlayer.create(this, R.raw.birds_chirping_sound);
        MediaPlayer harbor_sound = MediaPlayer.create(this, R.raw.harbor_bells_sound);
        MediaPlayer leaves_sound = MediaPlayer.create(this, R.raw.rustling_leaves_sound);

        //add mp3 to audio array
        audio = new MediaPlayer[][] { {rain_sound, ocean_sound, fire_sound},
                { thunder_sound, seagulls_sound, crickets_sound },
                { birds_sound, harbor_sound, leaves_sound } };



        //initializes string array from strings xml
        String[] primarySounds = getResources().getStringArray(R.array.primary_sounds);
        String[] secondarySounds = getResources().getStringArray(R.array.secondary_sounds);
        String[] tertiarySounds = getResources().getStringArray(R.array.tertiary_sounds);
        String[] gifSpinnerArray = getResources().getStringArray(R.array.gif_spinner);

        //initializes spinners and adds them to an array
        Spinner primarySpinner = findViewById(R.id.primarySpinner);
        Spinner secondarySpinner = findViewById(R.id.secondarySpinner);
        Spinner tertiarySpinner = findViewById(R.id.tertiarySpinner);
        Spinner gifSpinner = findViewById(R.id.gifSpinner);
        Spinner[] spinners = new Spinner[] {primarySpinner, secondarySpinner, tertiarySpinner, gifSpinner};

        //populates spinners with string array
        populateSpinner(spinners[0], primarySounds, 0);
        populateSpinner(spinners[1], secondarySounds, 1);
        populateSpinner(spinners[2], tertiarySounds, 2);
        populateGifSpinner(spinners[3], gifSpinnerArray);

        configureStartButton();

        RadioButton[] previewOptions = { findViewById(R.id.previewNoneButton), findViewById(R.id.previewVolumeButton), findViewById(R.id.previewAllButton) };

        previewOptions[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreviewMode(PreviewMode.None);
            }
        });
        previewOptions[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreviewMode(PreviewMode.VolumeChange);
            }
        });
        previewOptions[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreviewMode(PreviewMode.All);
            }
        });
    }

    public void setPreviewMode (PreviewMode mode) {
        previewMode = mode;
        if (mode == PreviewMode.All) {
            playAll();
        } else {
            pauseAll();
        }
    }

    public PreviewMode getPreviewMode() {
        return previewMode;
    }

    public void playAll () {
        for (int i = 0; i < mediaPlayers.length; i++) {
            if (mediaPlayers[i] != null) {
                mediaPlayers[i].start();
            }
        }
    }

    public void pauseAll() {
        for (int i = 0; i < mediaPlayers.length; i++) {
            if (mediaPlayers[i] != null && mediaPlayers[i].isPlaying()) {
                mediaPlayers[i].pause();
            }
        }
    }


    private void configureStartButton() {

        Button startButton = findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userTitle = findViewById(R.id.editScapeName);
                Intent intent = new Intent(MainActivity.this, PlayBackActivity.class);
                intent.putExtra("userTitle", userTitle.getText().toString());

                startActivity(intent);
            }
        });
    }

    private void populateSpinner(final Spinner spinner, String[] soundsArray, final int playerNum) {

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
                if (position == 0)
                    return;
                setVolumeSlider(position - 1, playerNum);
                Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void populateGifSpinner(final Spinner spinner, String[] gifArray) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item, gifArray){
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
                        Toast.makeText(parent.getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        PlayBackActivity.tornado = true;
                        break;
                    case 2:
                        Toast.makeText(parent.getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        PlayBackActivity.tornado2 = true;
                        break;
                    case 3:
                        Toast.makeText(parent.getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        PlayBackActivity.galaxy = true;
                        break;
                    case 4:
                        Toast.makeText(parent.getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        PlayBackActivity.storm = true;
                        break;
                    case 5:
                        Toast.makeText(parent.getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        PlayBackActivity.street = true;
                        break;
                    case 6:
                        Toast.makeText(parent.getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        PlayBackActivity.rain_ocean = true;
                        break;
                    case 7:
                        Toast.makeText(parent.getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        PlayBackActivity.rain = true;
                        break;
                    case 8:
                        Toast.makeText(parent.getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        PlayBackActivity.lightning = true;
                        break;

                    case 9:
                        Toast.makeText(parent.getContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                        PlayBackActivity.mindcontrol = true;
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

    private void setVolumeSlider(int clipNum, final int playerNum){


        volumeSliders[0] = findViewById(R.id.primarySeekBar);
        volumeSliders[1] = findViewById(R.id.secondarySeekBar);
        volumeSliders[2] = findViewById(R.id.tertiarySeekBar);

        if (mediaPlayers[playerNum] != null) {
            mediaPlayers[playerNum].pause();
        }
        mediaPlayers[playerNum] = audio[playerNum][clipNum];
        volumeSliders[playerNum].setMax(maxVolume);
        volumeSliders[playerNum].setProgress(50);
        mediaPlayers[playerNum].setLooping(true);

        if (mediaPlayers[playerNum].isPlaying() == false && previewMode == PreviewMode.All) {
            mediaPlayers[playerNum].start();
        }

        volumeSliders[playerNum].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                float volume = (float) (1 - (Math.log(maxVolume - progress) / Math.log(maxVolume)));
                mediaPlayers[playerNum].setVolume(volume, volume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (previewMode == PreviewMode.VolumeChange) {
                    mediaPlayers[playerNum].start();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (previewMode == PreviewMode.VolumeChange) {
                    mediaPlayers[playerNum].pause();
                }
            }
        });
    }
}
