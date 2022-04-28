package com.example.androiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView fakta;
    private int rand;
    private String[] faktoja;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView teksti = (TextView) findViewById(R.id.saldo);
        fakta = (TextView) findViewById(R.id.faktaText) ;
        faktoja = new String[] {"A", "BEN"};



    }

    public void faktaNappi(View v){
        rand = (int) (Math.random() * 4);
        fakta.setText(faktoja[rand]);


    }


}