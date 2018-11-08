package edu.nhti.ist218.soundscapeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_back);

        TextView userTitle = findViewById(R.id.scapeTitle);

        Intent intent = getIntent();
        userTitle.setText(intent.getStringExtra("userTitle"));

        final Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playButton.equals(R.drawable.pause_button)){
                    playButton.setBackgroundResource(R.drawable.play_button);
                } else {
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
                finish();
            }
        });

    }
}
