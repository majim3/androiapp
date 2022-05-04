package com.example.androiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView fakta;
    private int rand;
    private String[] faktoja;
    private Intent lisaysSivu;
    private Intent tietoSivu;
    private Intent historiaSivu;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences3 = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String userName = sharedPreferences3.getString("User name: ", null);
        String userAge = sharedPreferences3.getString("User age: ", null);

        if (userName == null  ||  userAge == null) {
            Intent intent = new Intent(MainActivity.this, Alku.class);
            startActivity(intent);
        }



        TextView teksti = (TextView) findViewById(R.id.saldo);
        fakta = (TextView) findViewById(R.id.faktaText) ;
        faktoja = new String[] {"Caffeine may result in headaches.", "Dependency, so you need to take more of it to get the same results.",
                                "Did you know that caffeine causes faster heart rate","Consuming leo causes dehydration",
                                "Some say that caffeine causes restlessness and shakiness when consumed in large quantities","Caffeine may cause in some cases of anxiety",
                                "Caffeine also causes Dependency, so you need to take more of it to get the same effects",
                                "Caffeine also causes your intestines to work faster"};

    }

    public void faktaNappi(View v){
        rand = (int) (Math.random() * 8);
        fakta.setText(faktoja[rand]);
    }

   public void lisays(View v){
        lisaysSivu = new Intent(MainActivity.this, kofeiininLisays.class);
        startActivity(lisaysSivu);

   }
   public void historiaPainike(View view){
        historiaSivu = new Intent(MainActivity.this, Historia.class);
        startActivity(historiaSivu);

   }

   public void tietoNappi(View v){
        tietoSivu = new Intent(MainActivity.this, tiedot.class);
        startActivity(tietoSivu);
   }





}