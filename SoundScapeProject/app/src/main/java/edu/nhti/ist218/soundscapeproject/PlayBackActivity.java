package edu.nhti.ist218.soundscapeproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class PlayBackActivity extends AppCompatActivity {

    private MainActivity mainAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_back);

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

                finish();
            }
        });

    }
}
