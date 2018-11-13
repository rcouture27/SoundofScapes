package edu.nhti.ist218.soundscapeproject;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayBackActivity extends AppCompatActivity {

    Button playButton;
    TextView userTitle;
    PreviewMode previousMode = PreviewMode.None;

    MediaPlayer konami = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_back);

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
