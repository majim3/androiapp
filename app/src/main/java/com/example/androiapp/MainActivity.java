package com.example.androiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Pertun kommentti lelol
        TextView teksti = (TextView) findViewById(R.id.textView);
        teksti.setText("hEI KAikki");
    }

}