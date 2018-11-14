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
    public static boolean galaxy = false;
    public static boolean storm = false;
    public static boolean street = false;
    public static boolean rain_ocean = false;
    public static boolean rain = false;
    public static boolean lightning = false;
    public static boolean mindcontrol = false;

    Button playButton;
    TextView userTitle;
    PreviewMode previousMode = PreviewMode.None;

    MediaPlayer konami = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_back);

        ImageView imageView;

        //Loads gifs
        if (tornado) {
            imageView = findViewById(R.id.imageView);
            Glide.with(this)
                    .load(R.raw.tornado)
                    .apply(new RequestOptions().override(3400, 2800))
                    .into(imageView);
            tornado = false;
        }

        if (tornado2) {
            imageView = findViewById(R.id.imageView);
            Glide.with(this)
                    .load(R.raw.tornado2test1)
                    .apply(new RequestOptions().override(1500, 2000))
                    .into(imageView);
            tornado2 = false;
        }

        if (galaxy) {
            imageView = findViewById(R.id.imageView);
            Glide.with(this)
                    .load(R.raw.galaxy)
                    .into(imageView);
            galaxy = false;
        }

        if (storm) {
            imageView = findViewById(R.id.imageView);
            Glide.with(this)
                    .load(R.raw.storm2)
                    .into(imageView);
            storm = false;
        }

        if (street) {
            imageView = findViewById(R.id.imageView);
            Glide.with(this)
                    .load(R.raw.street)
                    .into(imageView);
            street = false;
        }

        if (rain_ocean) {
            imageView = findViewById(R.id.imageView);
            Glide.with(this)
                    .load(R.raw.rain_ocean)
                    .into(imageView);
            rain_ocean = false;
        }

        if (rain) {
            imageView = findViewById(R.id.imageView);
            Glide.with(this)
                    .load(R.raw.rain)
                    .into(imageView);
            rain = false;
        }

        if (lightning) {
            imageView = findViewById(R.id.imageView);
            Glide.with(this)
                    .load(R.raw.lightning)
                    .into(imageView);
            lightning = false;
        }

        if (mindcontrol) {
            imageView = findViewById(R.id.imageView);
            Glide.with(this)
                    .load(R.raw.mindcontrol)
                    .into(imageView);
            mindcontrol = false;
        }


        userTitle = findViewById(R.id.scapeTitle);
        playButton = findViewById(R.id.playButton);
        previousMode = MainActivity.mainActivity.getPreviewMode();
        MainActivity.mainActivity.setPreviewMode(PreviewMode.All);
        playButton.setBackgroundResource(R.drawable.pause_button);

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
                if(MainActivity.mainActivity.getPreviewMode() == PreviewMode.All){
                    playButton.setBackgroundResource(R.drawable.play_button);
                    MainActivity.mainActivity.setPreviewMode(PreviewMode.None);
                } else if (MainActivity.mainActivity.getPreviewMode() == PreviewMode.None){
                    playButton.setBackgroundResource(R.drawable.pause_button);
                    MainActivity.mainActivity.setPreviewMode(PreviewMode.All);
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
                MainActivity.mainActivity.setPreviewMode(previousMode);
                finish();
            }
        });

    }
}