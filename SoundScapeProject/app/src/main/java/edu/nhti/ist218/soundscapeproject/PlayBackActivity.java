package edu.nhti.ist218.soundscapeproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayBackActivity extends AppCompatActivity {

    private MainActivity mainAct;

    Button playButton;
    TextView userTitle;

    MediaPlayer konami = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_back);

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

    @SuppressLint("RestrictedApi")
    private void configureReturnButton() {

        Button returnButton = findViewById(R.id.buttonReturn);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayBackActivity.this, MainActivity.class);
                int requestCode = MainActivity.PAUSE_CODE;
                Bundle bundle = new Bundle();
                bundle.putString("key", "pause");
                startActivityForResult(intent, requestCode, bundle);

                if (konami != null) {
                    konami.stop();
                }
                finish();
            }
        });

    }
}
