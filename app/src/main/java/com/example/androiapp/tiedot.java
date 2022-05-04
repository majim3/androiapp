package com.example.androiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class tiedot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiedot);


        SharedPreferences sharedPreferences3 = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String userName = sharedPreferences3.getString("User name: ", null);
        String userAge = sharedPreferences3.getString("User age: ", null);


        TextView name = (TextView) findViewById(R.id.textView5);
        TextView age = (TextView) findViewById(R.id.textView7);
        name.setText(userName);
        age.setText(userAge);

        Button btn = (Button) findViewById(R.id.button5);





    }
    public void clicked(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(tiedot.this, MainActivity.class);
        startActivity(intent);

    }




}