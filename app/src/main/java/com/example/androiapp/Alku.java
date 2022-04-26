package com.example.androiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Alku extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String EXTRA_MESSAGE2 = "..............";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alku);

    }
    public void klikattu(View view) {

        Intent intent = new Intent(this, DisplayMessageActivity.class);

        TextView teksti = (TextView)findViewById(R.id.textView);
        TextView teksti2 = (TextView)findViewById(R.id.textView2);
        EditText nimi = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText ika = (EditText) findViewById(R.id.editTextTextPersonName2);
        String viesti = nimi.getText().toString();
        String age = ika.getText().toString();

        int alku = 0;
        int value = Integer.parseInt(age + alku);

        if(viesti.equals("") && age.equals("") && value <= 15){
            teksti2.setText("Kentät eivät voi olla tyhjiä!");
        }else if(viesti.equals("")) {
            teksti2.setText("Kentät eivät voi olla tyhjiä!");
        }else if (age.equals("")) {
            teksti2.setText("Kentät eivät voi olla tyhjiä!");
        }else if (value <= 15){
            teksti.setText("Ikä ei voi olla alle 15!");
        }else {

            intent.putExtra(EXTRA_MESSAGE, viesti);
            intent.putExtra(EXTRA_MESSAGE2, age);
            startActivity(intent);
        }


    }
}