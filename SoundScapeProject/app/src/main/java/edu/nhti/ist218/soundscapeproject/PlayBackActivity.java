package edu.nhti.ist218.soundscapeproject;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//imports gif image loader Glide
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class PlayBackActivity extends AppCompatActivity {

    //gif bools
    public static boolean tornado = false;
    public static boolean tornado2 = false;
    public static boolean storm = false;
    public static boolean storm2 = false;
    public static boolean street = false;
    public static boolean rain_ocean = false;
    public static boolean rain = false;
    public static boolean lightning = false;

    Button playButton;
    TextView userTitle;

    MediaPlayer konami = null;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_play_back);

            //Loads gifs
            if (tornado) {
                ImageView imageView = findViewById(R.id.imageView);
                Glide.with(this)
                        .load(R.raw.tornado)
                        .apply(new RequestOptions().override(2400, 1800))
                        .into(imageView);
                tornado = false;
            }

            if (tornado2) {
                ImageView imageView = findViewById(R.id.imageView);
                Glide.with(this)
                        .load(R.raw.tornado2)
                        .into(imageView);
                tornado2 = false;
            }

            if (storm) {
                ImageView imageView = findViewById(R.id.imageView);
                Glide.with(this)
                        .load(R.raw.thunderstorm)
                        .into(imageView);
                storm = false;
            }

            if (storm2) {
                ImageView imageView = findViewById(R.id.imageView);
                Glide.with(this)
                        .load(R.raw.storm2)
                        .into(imageView);
                storm2 = false;
            }

            if (street) {
                ImageView imageView = findViewById(R.id.imageView);
                Glide.with(this)
                        .load(R.raw.street)
                        .into(imageView);
                street = false;
            }

            if (rain_ocean) {
                ImageView imageView = findViewById(R.id.imageView);
                Glide.with(this)
                        .load(R.raw.rain_ocean)
                        .into(imageView);
                rain_ocean = false;
            }

            if (rain) {
                ImageView imageView = findViewById(R.id.imageView);
                Glide.with(this)
                        .load(R.raw.rain)
                        .into(imageView);
                rain = false;
            }

            if (lightning) {
                ImageView imageView = findViewById(R.id.imageView);
                Glide.with(this)
                        .load(R.raw.lightning)
                        .into(imageView);
                lightning = false;
            }


        userTitle = findViewById(R.id.scapeTitle);
        final Button playButton = findViewById(R.id.playButton);

        Intent intent = getIntent();
        userTitle.setText(intent.getStringExtra("userTitle"));

        if ((userTitle.getText().equals("22884646 B A B A Start")) || (userTitle.getText().equals("22884646babastart"))
                || (userTitle.getText().equals("Up Up Down Down Left Right Left Right B A B A Start"))) {

            userTitle.setText(R.string.konami);
            playButton.setBackgroundResource(R.drawable.konami_dog);

            konami = MediaPlayer.create(PlayBackActivity.this, R.raw.konami);
            konami.start();
            konami.setLooping(true);
        }


        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playButton.equals(R.drawable.konami_dog)) {
                    playButton.setBackgroundResource(R.drawable.konami_dog);
                }
                if(playButton.equals(R.drawable.pause_button)){
                    playButton.setBackgroundResource(R.drawable.play_button);
                }
                if (playButton.equals(R.drawable.play_button)){
                    playButton.setBackgroundResource(R.drawable.pause_button);
                }
            }
        });

        configureReturnButton();
    }

    private void configureReturnButton() {

        Button returnButton = findViewById(R.id.buttonReturn);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (konami != null) {
                    konami.stop();
                }
                finish();
            }
        });

    }



}
