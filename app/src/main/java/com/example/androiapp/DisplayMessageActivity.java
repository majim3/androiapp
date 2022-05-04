package com.example.androiapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String message = "Hello " + intent.getStringExtra(Alku.EXTRA_MESSAGE) + "Your age is " + intent.getStringExtra(Alku.EXTRA_MESSAGE2);

    }

}
