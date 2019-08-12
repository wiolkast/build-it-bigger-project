package com.example.jokedisplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    private static final String JOKE_EXTRA = "jokeExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        TextView jokeTv = findViewById(R.id.jokeTV);

        Intent intent = getIntent();
        if(intent.hasExtra(JOKE_EXTRA)){
            jokeTv.setText(intent.getStringExtra(JOKE_EXTRA));
        }
    }
}
